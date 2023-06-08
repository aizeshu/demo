package com.cl.controller;

import com.cl.domain.ResponseResult;
import com.cl.ipfs.IpfsService;
import com.cl.utils.JsonUtils;
import com.cl.pojo.Goods;
import com.cl.service.GoodsService;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 商品相关
 */

@RestController
@RequestMapping("goods")
@CrossOrigin
public class GoodsController extends BaseController {

    @Value("${ipfs.show-url}")
    private String ipfsShowUrl;
    @Autowired
    private IpfsService ipfsService;

    private BcosSDK bcosSDK;
    private Client client;

    private com.cl.solidity.Goods goods_solidity;


    @PostConstruct
    public void init() throws ContractException {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:fisco-bscos-config.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
        //部署合约
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
        goods_solidity = com.cl.solidity.Goods.deploy(client, cryptoKeyPair);
    }

    @Autowired
    private GoodsService goodsService;

    //获取某商品详情 链下
    @RequestMapping("/details")
    public ResponseResult details(String gid) throws IOException {
        Goods goods = goodsService.details(gid);
        return ResponseResult.success(goods);
    }

    /***
     * 商品关键词查询
     * @throws IOException
     */
    @RequestMapping("/keywords")
    public ResponseResult keywords(String keywords) throws IOException {
        List<Goods> list = goodsService.keywords(keywords);
        return ResponseResult.success(list);
    }

    //藏品创作 需要调用智能合约
    @RequestMapping("create")
    public ResponseResult create(Goods goods, MultipartFile file, String token) throws Exception {
        String originalFilename = file.getOriginalFilename();
        int idx = originalFilename.lastIndexOf(".");
        String fileName = originalFilename.substring(0, idx);
        String suffix = originalFilename.substring(idx + 1, originalFilename.length());
        String pic_hash = ipfsService.uploadToIpfs(file.getBytes());
        //藏品元数据 - 扩展字段
        String gid = UUID.randomUUID().toString();
        goods.setGid(gid);
        goods.setPic_hash(pic_hash);
        goods.setFile_name(fileName);
        goods.setSuffix(suffix);
        String uid = getUid(token);
        String url = ipfsShowUrl + pic_hash;
        goods.setUrl(url);
        goods.setCreate_id(uid);
        //将元数据装成json 保存到ipfs
        String good_data = JsonUtils.GenGoodJson(goods);
        String data_hash = ipfsService.uploadToIpfs(good_data.getBytes());
        goods.setData_hash(data_hash);
        //执行合约 写入藏品hash
        TransactionReceipt receipt = goods_solidity.createGoods(gid, uid, pic_hash, data_hash);
        Tuple1<BigInteger> resultTuple = goods_solidity.getCreateGoodsOutput(receipt);
        BigInteger result = resultTuple.getValue1();
        goods.setOwn_id(uid);
        goodsService.create(goods);
        return ResponseResult.success(result);
    }

    //获取当前用户拥有的藏品 走链下
    @RequestMapping("select")
    public ResponseResult select(String token) throws Exception {
        String uid = getUid(token);
        List<Goods> dataHashList = goodsService.getGoodsDetailsByUid(uid);
        //返回图片hash 和 元数据 hash 获取到元数据hash后在获取元数据json 解析数据
        List<Goods> result = new ArrayList<>();
        for (Goods g : dataHashList) {
            //从ipfs下载元数据json转换成bean对象
            String data_hash = g.getData_hash();
            byte[] bytes = ipfsService.downFromIpfs(data_hash);
            //解析元数据
            Goods goods = JsonUtils.parseJson(bytes, Goods.class);
            result.add(goods);
        }
        return ResponseResult.success(result);
    }


    /**
     * 藏品交易
     */
    @RequestMapping("buy")
    public ResponseResult buy(String gid, String token) throws Exception {
        //根据gid获取藏品元数据
        TransactionReceipt goodReceipt = goods_solidity.selectGood(gid);
        Tuple2<String, String> selectGoodOutput = goods_solidity.getSelectGoodOutput(goodReceipt);
        String buy_id = getAccountByToken(token).getUid();
        String uid = selectGoodOutput.getValue1();
        if ("".equals(uid)) {
            throw new Exception("错误的商品id");
        }

        if (buy_id.equals(uid)) {
            throw new Exception("不能购买自己的商品");
        }
        String data_hash = selectGoodOutput.getValue2();

        byte[] bytes = ipfsService.downFromIpfs(data_hash);

        Goods g = JsonUtils.parseJson(bytes, Goods.class);

        int price = g.getPrice();


        TransactionReceipt sellReceipt = goods_solidity.sellGoods(gid, buy_id, new BigInteger(String.valueOf(price)));
        Tuple1<BigInteger> sellGoodsTuple = goods_solidity.getSellGoodsOutput(sellReceipt);


        if (Integer.valueOf(sellGoodsTuple.getValue1().toString()) == 0) {//交易成功
            //修改数据库记录
            goodsService.updateOwn(data_hash, buy_id);
        } else if (Integer.valueOf(sellGoodsTuple.getValue1().toString()) == -2) {
            throw new Exception("您的余额不足");
        }

        return ResponseResult.success(null);
    }


    //查询系统内所有藏品 - 链下
    @RequestMapping("selectAll")
    public ResponseResult selectAll() throws Exception {
        List<Goods> AllGoods = new ArrayList<>();
        List<Goods> result = new ArrayList<>();
        //获取区块链上所有藏品hash 及元数据
        AllGoods = goodsService.selectAllGoods();

        //解析元数据
        for (Goods g : AllGoods) {
            Goods data = new Goods();
            byte[] bytes = ipfsService.downFromIpfs(g.getData_hash());
            data = JsonUtils.parseJson(bytes, Goods.class);
            data.setSys_gid(g.getSys_gid());
            result.add(data);
        }
        return ResponseResult.success(result);
    }

    //    @RequestMapping("selectCate")
//    public ResponseResult selectCate(String cate_id) throws Exception {
//        List<Goods> result = new ArrayList<>();
//        result = goodsService.selectCate(cate_id);
//        return ResponseResult.success(result);
//    }
    @RequestMapping("updateClickNum")
    public ResponseResult updateClickNum(String gid) throws Exception {
        Goods good = goodsService.details(gid);
        goodsService.updateClickNum(good.getData_hash());
        return ResponseResult.success(null);
    }

    @RequestMapping("recommend")
    public ResponseResult recommend() throws Exception {
        List<Goods> recommend = goodsService.getRecommendGoods();
        List<Goods> result = new ArrayList<>();
        for (Goods g : recommend) {
            byte[] bytes = ipfsService.downFromIpfs(g.getData_hash());
            Goods recommendGood = JsonUtils.parseJson(bytes, Goods.class);
            recommendGood.setClickNum(g.getClickNum());
            result.add(recommendGood);
        }

        return ResponseResult.success(result);
    }
}
