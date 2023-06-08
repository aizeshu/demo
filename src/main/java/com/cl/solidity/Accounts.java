package com.cl.solidity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Int256;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Accounts extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040523480156200001157600080fd5b506110016000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166356004b6a6040805190810160405280600781526020017f6163636f756e74000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016200010191906200024a565b602060405180830381600087803b1580156200011c57600080fd5b505af115801562000131573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525062000157919081019062000174565b50620002f4565b60006200016c8251620002a3565b905092915050565b6000602082840312156200018757600080fd5b600062000197848285016200015e565b91505092915050565b6000620001ad8262000298565b808452620001c3816020860160208601620002ad565b620001ce81620002e3565b602085010191505092915050565b6000600382527f75696400000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000600782527f62616c616e6365000000000000000000000000000000000000000000000000006020830152604082019050919050565b60006060820190508181036000830152620002668184620001a0565b905081810360208301526200027b81620001dc565b90508181036040830152620002908162000213565b905092915050565b600081519050919050565b6000819050919050565b60005b83811015620002cd578082015181840152602081019050620002b0565b83811115620002dd576000848401525b50505050565b6000601f19601f8301169050919050565b61134580620003046000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063870187eb1461005c578063cc98220614610099578063d6253174146100d6575b600080fd5b34801561006857600080fd5b50610083600480360361007e9190810190610e4d565b610113565b6040516100909190610fef565b60405180910390f35b3480156100a557600080fd5b506100c060048036036100bb9190810190610e4d565b610518565b6040516100cd9190610fef565b60405180910390f35b3480156100e257600080fd5b506100fd60048036036100f89190810190610e0c565b6108b3565b60405161010a9190611168565b60405180910390f35b6000806000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040805190810160405280600781526020017f6163636f756e74000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016101c49190611025565b602060405180830381600087803b1580156101de57600080fd5b505af11580156101f2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506102169190810190610dba565b92508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561027c57600080fd5b505af1158015610290573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506102b49190810190610d91565b91508173ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161030b91906110e5565b600060405180830381600087803b15801561032557600080fd5b505af1158015610339573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16638a42ebe9866040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610392919061113a565b600060405180830381600087803b1580156103ac57600080fd5b505af11580156103c0573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff1663bf2b70a187848673ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561044657600080fd5b505af115801561045a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061047e9190810190610d3f565b6040518463ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016104b8939291906110a7565b602060405180830381600087803b1580156104d257600080fd5b505af11580156104e6573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061050a9190810190610de3565b905080935050505092915050565b6000806000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040805190810160405280600781526020017f6163636f756e74000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016105c99190611025565b602060405180830381600087803b1580156105e357600080fd5b505af11580156105f7573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061061b9190810190610dba565b92508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561068157600080fd5b505af1158015610695573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506106b99190810190610d91565b91508173ffffffffffffffffffffffffffffffffffffffff1663e942b516876040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161071091906110e5565b600060405180830381600087803b15801561072a57600080fd5b505af115801561073e573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16638a42ebe9866040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610797919061113a565b600060405180830381600087803b1580156107b157600080fd5b505af11580156107c5573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac3687846040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610820929190611077565b602060405180830381600087803b15801561083a57600080fd5b505af115801561084e573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506108729190810190610de3565b9050600181141561088657600093506108aa565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff93505b50505092915050565b60008060008060008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040805190810160405280600781526020017f6163636f756e74000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016109669190611025565b602060405180830381600087803b15801561098057600080fd5b505af1158015610994573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506109b89190810190610dba565b93508373ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610a1e57600080fd5b505af1158015610a32573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610a569190810190610d3f565b92508373ffffffffffffffffffffffffffffffffffffffff1663e8434e3987856040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610aaf929190611047565b602060405180830381600087803b158015610ac957600080fd5b505af1158015610add573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610b019190810190610d68565b91508173ffffffffffffffffffffffffffffffffffffffff1663846719e060006040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610b59919061100a565b602060405180830381600087803b158015610b7357600080fd5b505af1158015610b87573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610bab9190810190610d91565b73ffffffffffffffffffffffffffffffffffffffff16633536046a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610bfd9061111a565b602060405180830381600087803b158015610c1757600080fd5b505af1158015610c2b573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610c4f9190810190610ea1565b905080945050505050919050565b6000610c698251611226565b905092915050565b6000610c7d8251611238565b905092915050565b6000610c91825161124a565b905092915050565b6000610ca5825161125c565b905092915050565b6000610cb9825161126e565b905092915050565b600082601f8301121515610cd457600080fd5b8135610ce7610ce2826111b0565b611183565b9150808252602083016020830185838301111561","0d0357600080fd5b610d0e8382846112b8565b50505092915050565b6000610d238235611278565b905092915050565b6000610d378251611278565b905092915050565b600060208284031215610d5157600080fd5b6000610d5f84828501610c5d565b91505092915050565b600060208284031215610d7a57600080fd5b6000610d8884828501610c71565b91505092915050565b600060208284031215610da357600080fd5b6000610db184828501610c85565b91505092915050565b600060208284031215610dcc57600080fd5b6000610dda84828501610c99565b91505092915050565b600060208284031215610df557600080fd5b6000610e0384828501610cad565b91505092915050565b600060208284031215610e1e57600080fd5b600082013567ffffffffffffffff811115610e3857600080fd5b610e4484828501610cc1565b91505092915050565b60008060408385031215610e6057600080fd5b600083013567ffffffffffffffff811115610e7a57600080fd5b610e8685828601610cc1565b9250506020610e9785828601610d17565b9150509250929050565b600060208284031215610eb357600080fd5b6000610ec184828501610d2b565b91505092915050565b610ed381611282565b82525050565b610ee281611294565b82525050565b610ef181611212565b82525050565b610f00816112a6565b82525050565b6000610f11826111e7565b808452610f258160208601602086016112c7565b610f2e816112fa565b602085010191505092915050565b6000610f47826111dc565b808452610f5b8160208601602086016112c7565b610f64816112fa565b602085010191505092915050565b6000600382527f75696400000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000600782527f62616c616e6365000000000000000000000000000000000000000000000000006020830152604082019050919050565b610fe98161121c565b82525050565b60006020820190506110046000830184610ee8565b92915050565b600060208201905061101f6000830184610ef7565b92915050565b6000602082019050818103600083015261103f8184610f3c565b905092915050565b600060408201905081810360008301526110618185610f06565b90506110706020830184610eca565b9392505050565b600060408201905081810360008301526110918185610f06565b90506110a06020830184610ed9565b9392505050565b600060608201905081810360008301526110c18186610f06565b90506110d06020830185610ed9565b6110dd6040830184610eca565b949350505050565b600060408201905081810360008301526110fe81610f72565b905081810360208301526111128184610f06565b905092915050565b6000602082019050818103600083015261113381610fa9565b9050919050565b6000604082019050818103600083015261115381610fa9565b90506111626020830184610fe0565b92915050565b600060208201905061117d6000830184610fe0565b92915050565b6000604051905081810181811067ffffffffffffffff821117156111a657600080fd5b8060405250919050565b600067ffffffffffffffff8211156111c757600080fd5b601f19601f8301169050602081019050919050565b600081519050919050565b600081519050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b6000819050919050565b6000611231826111f2565b9050919050565b6000611243826111f2565b9050919050565b6000611255826111f2565b9050919050565b6000611267826111f2565b9050919050565b6000819050919050565b6000819050919050565b600061128d826111f2565b9050919050565b600061129f826111f2565b9050919050565b60006112b182611212565b9050919050565b82818337600083830152505050565b60005b838110156112e55780820151818401526020810190506112ca565b838111156112f4576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a723058207b1bfd14f262b29c41a20a67201ab7d89a55c7d3fcdb50a97d929065d45413216c6578706572696d656e74616cf50037"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040523480156200001157600080fd5b506110016000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663c92a78016040805190810160405280600781526020017f6163636f756e74000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016200010191906200024a565b602060405180830381600087803b1580156200011c57600080fd5b505af115801562000131573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525062000157919081019062000174565b50620002f4565b60006200016c8251620002a3565b905092915050565b6000602082840312156200018757600080fd5b600062000197848285016200015e565b91505092915050565b6000620001ad8262000298565b808452620001c3816020860160208601620002ad565b620001ce81620002e3565b602085010191505092915050565b6000600382527f75696400000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000600782527f62616c616e6365000000000000000000000000000000000000000000000000006020830152604082019050919050565b60006060820190508181036000830152620002668184620001a0565b905081810360208301526200027b81620001dc565b90508181036040830152620002908162000213565b905092915050565b600081519050919050565b6000819050919050565b60005b83811015620002cd578082015181840152602081019050620002b0565b83811115620002dd576000848401525b50505050565b6000601f19601f8301169050919050565b61134580620003046000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063264d804d1461005c578063464f482714610099578063f977b272146100d6575b600080fd5b34801561006857600080fd5b50610083600480360361007e9190810190610e4d565b610113565b6040516100909190610fef565b60405180910390f35b3480156100a557600080fd5b506100c060048036036100bb9190810190610e0c565b6104ae565b6040516100cd9190611168565b60405180910390f35b3480156100e257600080fd5b506100fd60048036036100f89190810190610e4d565b610858565b60405161010a9190610fef565b60405180910390f35b6000806000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166359a48b656040805190810160405280600781526020017f6163636f756e74000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016101c49190611025565b602060405180830381600087803b1580156101de57600080fd5b505af11580156101f2573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506102169190810190610dba565b92508273ffffffffffffffffffffffffffffffffffffffff16635887ab246040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561027c57600080fd5b505af1158015610290573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506102b49190810190610d91565b91508173ffffffffffffffffffffffffffffffffffffffff16631a391cb4876040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161030b91906110e5565b600060405180830381600087803b15801561032557600080fd5b505af1158015610339573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663f2f4ee6d866040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610392919061113a565b600060405180830381600087803b1580156103ac57600080fd5b505af11580156103c0573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff16634c6f30c087846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040161041b929190611077565b602060405180830381600087803b15801561043557600080fd5b505af1158015610449573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061046d9190810190610de3565b9050600181141561048157600093506104a5565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff93505b50505092915050565b60008060008060008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166359a48b656040805190810160405280600781526020017f6163636f756e74000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016105619190611025565b602060405180830381600087803b15801561057b57600080fd5b505af115801561058f573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506105b39190810190610dba565b93508373ffffffffffffffffffffffffffffffffffffffff1663c74f8caf6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561061957600080fd5b505af115801561062d573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506106519190810190610d3f565b92508373ffffffffffffffffffffffffffffffffffffffff1663d8ac595787856040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016106aa929190611047565b602060405180830381600087803b1580156106c457600080fd5b505af11580156106d8573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506106fc9190810190610d68565b91508173ffffffffffffffffffffffffffffffffffffffff16633dd2b61460006040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610754919061100a565b602060405180830381600087803b15801561076e57600080fd5b505af1158015610782573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506107a69190810190610d91565b73ffffffffffffffffffffffffffffffffffffffff1663df7427af6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016107f89061111a565b602060405180830381600087803b15801561081257600080fd5b505af1158015610826573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061084a9190810190610ea1565b905080945050505050919050565b6000806000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166359a48b656040805190810160405280600781526020017f6163636f756e74000000000000000000000000000000000000000000000000008152506040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004016109099190611025565b602060405180830381600087803b15801561092357600080fd5b505af1158015610937573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525061095b9190810190610dba565b92508273ffffffffffffffffffffffffffffffffffffffff16635887ab246040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156109c157600080fd5b505af11580156109d5573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506109f99190810190610d91565b91508173ffffffffffffffffffffffffffffffffffffffff16631a391cb4876040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610a5091906110e5565b600060405180830381600087803b158015610a6a57600080fd5b505af1158015610a7e573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663f2f4ee6d866040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610ad7919061113a565b600060405180830381600087803b158015610af157600080fd5b505af1158015610b05573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff1663664b37d687848673ffffffffffffffffffffffffffffffffffffffff1663c74f8caf6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610b8b57600080fd5b505af1158015610b9f573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610bc39190810190610d3f565b6040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401610bfd939291906110a7565b602060405180830381600087803b158015610c1757600080fd5b505af1158015610c2b573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250610c4f9190810190610de3565b905080935050505092915050565b6000610c698251611226565b905092915050565b6000610c7d8251611238565b905092915050565b6000610c91825161124a565b905092915050565b6000610ca5825161125c565b905092915050565b6000610cb9825161126e565b905092915050565b600082601f8301121515610cd457600080fd5b8135610ce7610ce2826111b0565b611183565b9150808252602083016020830185838301111561","0d0357600080fd5b610d0e8382846112b8565b50505092915050565b6000610d238235611278565b905092915050565b6000610d378251611278565b905092915050565b600060208284031215610d5157600080fd5b6000610d5f84828501610c5d565b91505092915050565b600060208284031215610d7a57600080fd5b6000610d8884828501610c71565b91505092915050565b600060208284031215610da357600080fd5b6000610db184828501610c85565b91505092915050565b600060208284031215610dcc57600080fd5b6000610dda84828501610c99565b91505092915050565b600060208284031215610df557600080fd5b6000610e0384828501610cad565b91505092915050565b600060208284031215610e1e57600080fd5b600082013567ffffffffffffffff811115610e3857600080fd5b610e4484828501610cc1565b91505092915050565b60008060408385031215610e6057600080fd5b600083013567ffffffffffffffff811115610e7a57600080fd5b610e8685828601610cc1565b9250506020610e9785828601610d17565b9150509250929050565b600060208284031215610eb357600080fd5b6000610ec184828501610d2b565b91505092915050565b610ed381611282565b82525050565b610ee281611294565b82525050565b610ef181611212565b82525050565b610f00816112a6565b82525050565b6000610f11826111e7565b808452610f258160208601602086016112c7565b610f2e816112fa565b602085010191505092915050565b6000610f47826111dc565b808452610f5b8160208601602086016112c7565b610f64816112fa565b602085010191505092915050565b6000600382527f75696400000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000600782527f62616c616e6365000000000000000000000000000000000000000000000000006020830152604082019050919050565b610fe98161121c565b82525050565b60006020820190506110046000830184610ee8565b92915050565b600060208201905061101f6000830184610ef7565b92915050565b6000602082019050818103600083015261103f8184610f3c565b905092915050565b600060408201905081810360008301526110618185610f06565b90506110706020830184610eca565b9392505050565b600060408201905081810360008301526110918185610f06565b90506110a06020830184610ed9565b9392505050565b600060608201905081810360008301526110c18186610f06565b90506110d06020830185610ed9565b6110dd6040830184610eca565b949350505050565b600060408201905081810360008301526110fe81610f72565b905081810360208301526111128184610f06565b905092915050565b6000602082019050818103600083015261113381610fa9565b9050919050565b6000604082019050818103600083015261115381610fa9565b90506111626020830184610fe0565b92915050565b600060208201905061117d6000830184610fe0565b92915050565b6000604051905081810181811067ffffffffffffffff821117156111a657600080fd5b8060405250919050565b600067ffffffffffffffff8211156111c757600080fd5b601f19601f8301169050602081019050919050565b600081519050919050565b600081519050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b6000819050919050565b6000611231826111f2565b9050919050565b6000611243826111f2565b9050919050565b6000611255826111f2565b9050919050565b6000611267826111f2565b9050919050565b6000819050919050565b6000819050919050565b600061128d826111f2565b9050919050565b600061129f826111f2565b9050919050565b60006112b182611212565b9050919050565b82818337600083830152505050565b60005b838110156112e55780820151818401526020810190506112ca565b838111156112f4576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a7230582045f70324b972a42f50f358ddae63b0fd2b1e14070e4f69bebfc2fda52a2fe0de6c6578706572696d656e74616cf50037"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"uid\",\"type\":\"string\"},{\"name\":\"balance\",\"type\":\"uint256\"}],\"name\":\"updateBalance\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"uid\",\"type\":\"string\"},{\"name\":\"balance\",\"type\":\"uint256\"}],\"name\":\"addAccount\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"uid\",\"type\":\"string\"}],\"name\":\"selectBalance\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"count\",\"type\":\"int256\"}],\"name\":\"InsertResult\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_UPDATEBALANCE = "updateBalance";

    public static final String FUNC_ADDACCOUNT = "addAccount";

    public static final String FUNC_SELECTBALANCE = "selectBalance";

    public static final Event INSERTRESULT_EVENT = new Event("InsertResult", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
    ;

    protected Accounts(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt updateBalance(String uid, BigInteger balance) {
        final Function function = new Function(
                FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(new Utf8String(uid),
                new Uint256(balance)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] updateBalance(String uid, BigInteger balance, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(new Utf8String(uid),
                new Uint256(balance)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUpdateBalance(String uid, BigInteger balance) {
        final Function function = new Function(
                FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(new Utf8String(uid),
                new Uint256(balance)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getUpdateBalanceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public Tuple1<BigInteger> getUpdateBalanceOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt addAccount(String uid, BigInteger balance) {
        final Function function = new Function(
                FUNC_ADDACCOUNT, 
                Arrays.<Type>asList(new Utf8String(uid),
                new Uint256(balance)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] addAccount(String uid, BigInteger balance, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ADDACCOUNT, 
                Arrays.<Type>asList(new Utf8String(uid),
                new Uint256(balance)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForAddAccount(String uid, BigInteger balance) {
        final Function function = new Function(
                FUNC_ADDACCOUNT, 
                Arrays.<Type>asList(new Utf8String(uid),
                new Uint256(balance)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getAddAccountInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADDACCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public Tuple1<BigInteger> getAddAccountOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ADDACCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt selectBalance(String uid) {
        final Function function = new Function(
                FUNC_SELECTBALANCE, 
                Arrays.<Type>asList(new Utf8String(uid)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] selectBalance(String uid, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SELECTBALANCE, 
                Arrays.<Type>asList(new Utf8String(uid)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSelectBalance(String uid) {
        final Function function = new Function(
                FUNC_SELECTBALANCE, 
                Arrays.<Type>asList(new Utf8String(uid)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSelectBalanceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SELECTBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getSelectBalanceOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SELECTBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public List<InsertResultEventResponse> getInsertResultEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(INSERTRESULT_EVENT, transactionReceipt);
        ArrayList<InsertResultEventResponse> responses = new ArrayList<InsertResultEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            InsertResultEventResponse typedResponse = new InsertResultEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.count = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeInsertResultEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(INSERTRESULT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeInsertResultEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(INSERTRESULT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static Accounts load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Accounts(contractAddress, client, credential);
    }

    public static Accounts deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Accounts.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class InsertResultEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger count;
    }
}