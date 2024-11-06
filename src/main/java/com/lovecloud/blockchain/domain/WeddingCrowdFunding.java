package com.lovecloud.blockchain.domain;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class WeddingCrowdFunding extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b50604051620023f6380380620023f6833981810160405281019062000037919062000109565b60016000806101000a81548160ff02191690831515021790555081600060016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050620001c6565b600081519050620000ec8162000192565b92915050565b6000815190506200010381620001ac565b92915050565b600080604083850312156200011d57600080fd5b60006200012d85828601620000f2565b92505060206200014085828601620000db565b9150509250929050565b6000620001578262000172565b9050919050565b60006200016b826200014a565b9050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6200019d816200014a565b8114620001a957600080fd5b50565b620001b7816200015e565b8114620001c357600080fd5b50565b61222080620001d66000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c8063b57326d311610066578063b57326d314610133578063b6adaaff14610163578063b8035d711461017f578063dc6fa568146101b5578063f257df36146101d15761009e565b80631ec32d15146100a3578063368e571b146100c1578063514fcac7146100dd57806355a373d6146100f95780638c59091714610117575b600080fd5b6100ab6101ef565b6040516100b89190611cbd565b60405180910390f35b6100db60048036038101906100d69190611677565b610215565b005b6100f760048036038101906100f29190611677565b6104ed565b005b6101016108f8565b60405161010e9190611e07565b60405180910390f35b610131600480360381019061012c91906116c9565b61091e565b005b61014d600480360381019061014891906116c9565b610e35565b60405161015a9190612022565b60405180910390f35b61017d60048036038101906101789190611677565b610f6d565b005b61019960048036038101906101949190611677565b611270565b6040516101ac9796959493929190611d98565b60405180910390f35b6101cf60048036038101906101ca9190611677565b6112f9565b005b6101d9611609565b6040516101e69190612022565b60405180910390f35b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008054906101000a900460ff16610262576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161025990611fe2565b60405180910390fd5b60008060006101000a81548160ff02191690831515021790555060006002600083815260200190815260200160002090508060040160019054906101000a900460ff16156102e5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102dc90611e62565b60405180910390fd5b60008160050160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490506000811161036e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161036590611ec2565b60405180910390fd5b60008260050160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550808260030160008282546103c991906120a4565b92505081905550600060019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb33836040518363ffffffff1660e01b815260040161042d929190611d38565b602060405180830381600087803b15801561044757600080fd5b505af115801561045b573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061047f919061164e565b503373ffffffffffffffffffffffffffffffffffffffff167fbb28353e4598c3b9199101a66e0989549b659a59a54d2c27fbb183f1932c8e6d826040516104c69190612022565b60405180910390a2505060016000806101000a81548160ff02191690831515021790555050565b60008054906101000a900460ff1661053a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161053190611fe2565b60405180910390fd5b60008060006101000a81548160ff02191690831515021790555060006002600083815260200190815260200160002090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146105fd576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105f490611fc2565b60405180910390fd5b8060040160009054906101000a900460ff161561064f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161064690611f42565b60405180910390fd5b8060040160029054906101000a900460ff16156106a1576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161069890611f02565b60405180910390fd5b60008160030154905080600060019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663dd62ed3e600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16306040518363ffffffff1660e01b815260040161072a929190611cd8565b60206040518083038186803b15801561074257600080fd5b505afa158015610756573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061077a91906116a0565b10156107bb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107b290611e82565b60405180910390fd5b600060019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1630846040518463ffffffff1660e01b815260040161083c93929190611d01565b602060405180830381600087803b15801561085657600080fd5b505af115801561086a573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061088e919061164e565b5060018260040160026101000a81548160ff021916908315150217905550827f97aef5197151230249a009de66e5bac6a087b4e727083bdc150726277cbd988e60405160405180910390a2505060016000806101000a81548160ff02191690831515021790555050565b600060019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008054906101000a900460ff1661096b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161096290611fe2565b60405180910390fd5b60008060006101000a81548160ff02191690831515021790555060006002600084815260200190815260200160002090508060040160009054906101000a900460ff166109ed576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109e490611f82565b60405180910390fd5b8060040160019054906101000a900460ff1615610a3f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a3690611e62565b60405180910390fd5b80600201544210610a85576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a7c90611ee2565b60405180910390fd5b60008160050160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205414610b09576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b0090611e42565b60405180910390fd5b81600060019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663dd62ed3e33306040518363ffffffff1660e01b8152600401610b67929190611cd8565b60206040518083038186803b158015610b7f57600080fd5b505afa158015610b93573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610bb791906116a0565b1015610bf8576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610bef90611f62565b60405180910390fd5b600060019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166340c10f1933846040518363ffffffff1660e01b8152600401610c55929190611d38565b600060405180830381600087803b158015610c6f57600080fd5b505af1158015610c83573d6000803e3d6000fd5b50505050600060019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3330856040518463ffffffff1660e01b8152600401610ce693929190611d01565b602060405180830381600087803b158015610d0057600080fd5b505af1158015610d14573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610d38919061164e565b50818160050160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555081816003016000828254610d93919061204e565b925050819055508060010154816003015410610dc75760008160040160006101000a81548160ff0219169083151502179055505b3373ffffffffffffffffffffffffffffffffffffffff16837f0a4a91237423e0a1766a761c7cb029311d8b95d6b1b81db1b949a70c98b4e08e84604051610e0e9190612022565b60405180910390a35060016000806101000a81548160ff0219169083151502179055505050565b600060036000815480929190610e4a90612144565b919050555060006002600060035481526020019081526020016000209050338160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508381600101819055508242610ec0919061204e565b816002018190555060018160040160006101000a81548160ff02191690831515021790555060008160040160016101000a81548160ff02191690831515021790555060008160040160026101000a81548160ff0219169083151502179055506003547f78b089ed0ff2f97dbc6d46e8ff7c2e1d24268225a64f551606c0cec14294a2d933868460020154604051610f5993929190611d61565b60405180910390a260035491505092915050565b60008054906101000a900460ff16610fba576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610fb190611fe2565b60405180910390fd5b60008060006101000a81548160ff02191690831515021790555060006002600083815260200190815260200160002090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461107d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161107490611fa2565b60405180910390fd5b8060040160019054906101000a900460ff16156110cf576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016110c690611e62565b60405180910390fd5b8060040160009054906101000a900460ff1615611121576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161111890611ea2565b60405180910390fd5b600081600301549050600060019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16836040518363ffffffff1660e01b81526004016111a9929190611d38565b602060405180830381600087803b1580156111c357600080fd5b505af11580156111d7573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906111fb919061164e565b5060008260040160026101000a81548160ff021916908315150217905550827fd467c1bc28c93578a2e2e99c775c5c20d8341e9a0e7d8e1b676aea9dfef85692826040516112499190612022565b60405180910390a2505060016000806101000a81548160ff02191690831515021790555050565b60026020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020154908060030154908060040160009054906101000a900460ff16908060040160019054906101000a900460ff16908060040160029054906101000a900460ff16905087565b60008054906101000a900460ff16611346576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161133d90611fe2565b60405180910390fd5b60008060006101000a81548160ff02191690831515021790555060006002600083815260200190815260200160002090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611409576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161140090612002565b60405180910390fd5b8060040160019054906101000a900460ff161561145b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161145290611e22565b60405180910390fd5b8060040160009054906101000a900460ff166114ac576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016114a390611f22565b60405180910390fd5b60008160040160006101000a81548160ff02191690831515021790555060018160040160016101000a81548160ff021916908315150217905550600060019054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663a9059cbb8260000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1683600301546040518363ffffffff1660e01b815260040161156b929190611d38565b602060405180830381600087803b15801561158557600080fd5b505af1158015611599573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906115bd919061164e565b50817f97aef5197151230249a009de66e5bac6a087b4e727083bdc150726277cbd988e60405160405180910390a25060016000806101000a81548160ff02191690831515021790555050565b60035481565b60008151905061161e816121bc565b92915050565b600081359050611633816121d3565b92915050565b600081519050611648816121d3565b92915050565b60006020828403121561166057600080fd5b600061166e8482850161160f565b91505092915050565b60006020828403121561168957600080fd5b600061169784828501611624565b91505092915050565b6000602082840312156116b257600080fd5b60006116c084828501611639565b91505092915050565b600080604083850312156116dc57600080fd5b60006116ea85828601611624565b92505060206116fb85828601611624565b9150509250929050565b61170e816120d8565b82525050565b61171d816120ea565b82525050565b61172c81612120565b82525050565b600061173f602b8361203d565b91507f5468652063726f776466756e64696e672068617320616c72656164792062656560008301527f6e2063616e63656c65642e0000000000000000000000000000000000000000006020830152604082019050919050565b60006117a5601d8361203d565b91507f596f75206861766520616c726561647920636f6e74726962757465642e0000006000830152602082019050919050565b60006117e560238361203d565b91507f5468652063726f776466756e64696e6720686173206265656e2063616e63656c60008301527f65642e00000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b600061184b60248361203d565b91507f496e73756666696369656e7420616c6c6f77616e63652066726f6d20636f6d7060008301527f616e792e000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b60006118b160238361203d565b91507f5468652063726f776466756e64696e6720686173206e6f7420656e646564207960008301527f65742e00000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b600061191760158361203d565b91507f4e6f20726566756e6461626c6520616d6f756e742e00000000000000000000006000830152602082019050919050565b6000611957601b8361203d565b91507f5468652063726f776466756e64696e672068617320656e6465642e00000000006000830152602082019050919050565b600061199760248361203d565b91507f546865206f726465722068617320616c7265616479206265656e2063616e636560008301527f6c65642e000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b60006119fd60348361203d565b91507f5468652063726f776466756e64696e672068617320616c726561647920656e6460008301527f6564206f72206265656e2063616e63656c65642e0000000000000000000000006020830152604082019050919050565b6000611a6360218361203d565b91507f5468652063726f776466756e64696e67206973207374696c6c2061637469766560008301527f2e000000000000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000611ac960178361203d565b91507f496e73756666696369656e7420616c6c6f77616e63652e0000000000000000006000830152602082019050919050565b6000611b09601f8361203d565b91507f5468652063726f776466756e64696e67206973206e6f74206163746976652e006000830152602082019050919050565b6000611b4960278361203d565b91507f4f6e6c792074686520636f75706c652063616e20636f6d706c6574652074686560008301527f206f726465722e000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000611baf60258361203d565b91507f4f6e6c792074686520636f75706c652063616e2063616e63656c20746865206f60008301527f726465722e0000000000000000000000000000000000000000000000000000006020830152604082019050919050565b6000611c15601f8361203d565b91507f5265656e7472616e637947756172643a207265656e7472616e742063616c6c006000830152602082019050919050565b6000611c55602c8361203d565b91507f4f6e6c792074686520636f75706c652063616e2063616e63656c20746865206360008301527f726f776466756e64696e672e00000000000000000000000000000000000000006020830152604082019050919050565b611cb781612116565b82525050565b6000602082019050611cd26000830184611705565b92915050565b6000604082019050611ced6000830185611705565b611cfa6020830184611705565b9392505050565b6000606082019050611d166000830186611705565b611d236020830185611705565b611d306040830184611cae565b949350505050565b6000604082019050611d4d6000830185611705565b611d5a6020830184611cae565b9392505050565b6000606082019050611d766000830186611705565b611d836020830185611cae565b611d906040830184611cae565b949350505050565b600060e082019050611dad600083018a611705565b611dba6020830189611cae565b611dc76040830188611cae565b611dd46060830187611cae565b611de16080830186611714565b611dee60a0830185611714565b611dfb60c0830184611714565b98975050505050505050565b6000602082019050611e1c6000830184611723565b92915050565b60006020820190508181036000830152611e3b81611732565b9050919050565b60006020820190508181036000830152611e5b81611798565b9050919050565b60006020820190508181036000830152611e7b816117d8565b9050919050565b60006020820190508181036000830152611e9b8161183e565b9050919050565b60006020820190508181036000830152611ebb816118a4565b9050919050565b60006020820190508181036000830152611edb8161190a565b9050919050565b60006020820190508181036000830152611efb8161194a565b9050919050565b60006020820190508181036000830152611f1b8161198a565b9050919050565b60006020820190508181036000830152611f3b816119f0565b9050919050565b60006020820190508181036000830152611f5b81611a56565b9050919050565b60006020820190508181036000830152611f7b81611abc565b9050919050565b60006020820190508181036000830152611f9b81611afc565b9050919050565b60006020820190508181036000830152611fbb81611b3c565b9050919050565b60006020820190508181036000830152611fdb81611ba2565b9050919050565b60006020820190508181036000830152611ffb81611c08565b9050919050565b6000602082019050818103600083015261201b81611c48565b9050919050565b60006020820190506120376000830184611cae565b92915050565b600082825260208201905092915050565b600061205982612116565b915061206483612116565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff038211156120995761209861218d565b5b828201905092915050565b60006120af82612116565b91506120ba83612116565b9250828210156120cd576120cc61218d565b5b828203905092915050565b60006120e3826120f6565b9050919050565b60008115159050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600061212b82612132565b9050919050565b600061213d826120f6565b9050919050565b600061214f82612116565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8214156121825761218161218d565b5b600182019050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6121c5816120ea565b81146121d057600080fd5b50565b6121dc81612116565b81146121e757600080fd5b5056fea2646970667358221220657f1eba735b0456a08654145dbd41f11b655a82ac06a5baac3b5395a10f791064736f6c63430008000033\n";

    public static final String FUNC_CANCELCONTRIBUTION = "cancelContribution";

    public static final String FUNC_CANCELCROWDFUNDING = "cancelCrowdfunding";

    public static final String FUNC_CANCELORDER = "cancelOrder";

    public static final String FUNC_COMPANYWALLET = "companyWallet";

    public static final String FUNC_COMPLETEORDER = "completeOrder";

    public static final String FUNC_CONTRIBUTE = "contribute";

    public static final String FUNC_CREATECROWDFUNDING = "createCrowdfunding";

    public static final String FUNC_CROWDFUNDINGCOUNT = "crowdfundingCount";

    public static final String FUNC_CROWDFUNDINGS = "crowdfundings";

    public static final String FUNC_TOKENCONTRACT = "tokenContract";

    public static final Event CONTRIBUTIONMADE_EVENT = new Event("ContributionMade",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CROWDFUNDINGCANCELED_EVENT = new Event("CrowdfundingCanceled",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event CROWDFUNDINGCOMPLETED_EVENT = new Event("CrowdfundingCompleted",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CROWDFUNDINGCREATED_EVENT = new Event("CrowdfundingCreated",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REFUND_EVENT = new Event("Refund",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected WeddingCrowdFunding(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected WeddingCrowdFunding(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected WeddingCrowdFunding(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected WeddingCrowdFunding(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ContributionMadeEventResponse> getContributionMadeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CONTRIBUTIONMADE_EVENT, transactionReceipt);
        ArrayList<ContributionMadeEventResponse> responses = new ArrayList<ContributionMadeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContributionMadeEventResponse typedResponse = new ContributionMadeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.guest = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContributionMadeEventResponse> contributionMadeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ContributionMadeEventResponse>() {
            @Override
            public ContributionMadeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CONTRIBUTIONMADE_EVENT, log);
                ContributionMadeEventResponse typedResponse = new ContributionMadeEventResponse();
                typedResponse.log = log;
                typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.guest = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ContributionMadeEventResponse> contributionMadeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTRIBUTIONMADE_EVENT));
        return contributionMadeEventFlowable(filter);
    }

    public List<CrowdfundingCanceledEventResponse> getCrowdfundingCanceledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CROWDFUNDINGCANCELED_EVENT, transactionReceipt);
        ArrayList<CrowdfundingCanceledEventResponse> responses = new ArrayList<CrowdfundingCanceledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CrowdfundingCanceledEventResponse typedResponse = new CrowdfundingCanceledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CrowdfundingCanceledEventResponse> crowdfundingCanceledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CrowdfundingCanceledEventResponse>() {
            @Override
            public CrowdfundingCanceledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CROWDFUNDINGCANCELED_EVENT, log);
                CrowdfundingCanceledEventResponse typedResponse = new CrowdfundingCanceledEventResponse();
                typedResponse.log = log;
                typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CrowdfundingCanceledEventResponse> crowdfundingCanceledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CROWDFUNDINGCANCELED_EVENT));
        return crowdfundingCanceledEventFlowable(filter);
    }

    public List<CrowdfundingCompletedEventResponse> getCrowdfundingCompletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CROWDFUNDINGCOMPLETED_EVENT, transactionReceipt);
        ArrayList<CrowdfundingCompletedEventResponse> responses = new ArrayList<CrowdfundingCompletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CrowdfundingCompletedEventResponse typedResponse = new CrowdfundingCompletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.totalCollected = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CrowdfundingCompletedEventResponse> crowdfundingCompletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CrowdfundingCompletedEventResponse>() {
            @Override
            public CrowdfundingCompletedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CROWDFUNDINGCOMPLETED_EVENT, log);
                CrowdfundingCompletedEventResponse typedResponse = new CrowdfundingCompletedEventResponse();
                typedResponse.log = log;
                typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.totalCollected = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CrowdfundingCompletedEventResponse> crowdfundingCompletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CROWDFUNDINGCOMPLETED_EVENT));
        return crowdfundingCompletedEventFlowable(filter);
    }

    public List<CrowdfundingCreatedEventResponse> getCrowdfundingCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CROWDFUNDINGCREATED_EVENT, transactionReceipt);
        ArrayList<CrowdfundingCreatedEventResponse> responses = new ArrayList<CrowdfundingCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CrowdfundingCreatedEventResponse typedResponse = new CrowdfundingCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.couple = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.goal = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CrowdfundingCreatedEventResponse> crowdfundingCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CrowdfundingCreatedEventResponse>() {
            @Override
            public CrowdfundingCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CROWDFUNDINGCREATED_EVENT, log);
                CrowdfundingCreatedEventResponse typedResponse = new CrowdfundingCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.crowdfundingId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.couple = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.goal = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.endTime = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CrowdfundingCreatedEventResponse> crowdfundingCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CROWDFUNDINGCREATED_EVENT));
        return crowdfundingCreatedEventFlowable(filter);
    }

    public List<RefundEventResponse> getRefundEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REFUND_EVENT, transactionReceipt);
        ArrayList<RefundEventResponse> responses = new ArrayList<RefundEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RefundEventResponse typedResponse = new RefundEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.guest = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RefundEventResponse> refundEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RefundEventResponse>() {
            @Override
            public RefundEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REFUND_EVENT, log);
                RefundEventResponse typedResponse = new RefundEventResponse();
                typedResponse.log = log;
                typedResponse.guest = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RefundEventResponse> refundEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REFUND_EVENT));
        return refundEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelContribution(BigInteger _crowdfundingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCELCONTRIBUTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crowdfundingId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelCrowdfunding(BigInteger _crowdfundingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCELCROWDFUNDING,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crowdfundingId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelOrder(BigInteger _crowdfundingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCELORDER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crowdfundingId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> companyWallet() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COMPANYWALLET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> completeOrder(BigInteger _crowdfundingId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_COMPLETEORDER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crowdfundingId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> contribute(BigInteger _crowdfundingId, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CONTRIBUTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_crowdfundingId),
                        new org.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createCrowdfunding(BigInteger _goal, BigInteger _duration) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATECROWDFUNDING,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_goal),
                        new org.web3j.abi.datatypes.generated.Uint256(_duration)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> crowdfundingCount() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CROWDFUNDINGCOUNT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean, Boolean>> crowdfundings(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CROWDFUNDINGS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple7<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean, Boolean>>(function,
                new Callable<Tuple7<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean, Boolean>>() {
                    @Override
                    public Tuple7<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, BigInteger, BigInteger, BigInteger, Boolean, Boolean, Boolean>(
                                (String) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(),
                                (BigInteger) results.get(2).getValue(),
                                (BigInteger) results.get(3).getValue(),
                                (Boolean) results.get(4).getValue(),
                                (Boolean) results.get(5).getValue(),
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> tokenContract() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOKENCONTRACT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static WeddingCrowdFunding load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new WeddingCrowdFunding(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static WeddingCrowdFunding load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new WeddingCrowdFunding(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static WeddingCrowdFunding load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new WeddingCrowdFunding(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static WeddingCrowdFunding load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new WeddingCrowdFunding(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<WeddingCrowdFunding> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _tokenContract, String _companyWallet) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _tokenContract),
                new org.web3j.abi.datatypes.Address(160, _companyWallet)));
        return deployRemoteCall(WeddingCrowdFunding.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<WeddingCrowdFunding> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _tokenContract, String _companyWallet) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _tokenContract),
                new org.web3j.abi.datatypes.Address(160, _companyWallet)));
        return deployRemoteCall(WeddingCrowdFunding.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<WeddingCrowdFunding> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _tokenContract, String _companyWallet) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _tokenContract),
                new org.web3j.abi.datatypes.Address(160, _companyWallet)));
        return deployRemoteCall(WeddingCrowdFunding.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<WeddingCrowdFunding> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _tokenContract, String _companyWallet) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _tokenContract),
                new org.web3j.abi.datatypes.Address(160, _companyWallet)));
        return deployRemoteCall(WeddingCrowdFunding.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ContributionMadeEventResponse extends BaseEventResponse {
        public BigInteger crowdfundingId;

        public String guest;

        public BigInteger amount;
    }

    public static class CrowdfundingCanceledEventResponse extends BaseEventResponse {
        public BigInteger crowdfundingId;
    }

    public static class CrowdfundingCompletedEventResponse extends BaseEventResponse {
        public BigInteger crowdfundingId;

        public BigInteger totalCollected;
    }

    public static class CrowdfundingCreatedEventResponse extends BaseEventResponse {
        public BigInteger crowdfundingId;

        public String couple;

        public BigInteger goal;

        public BigInteger endTime;
    }

    public static class RefundEventResponse extends BaseEventResponse {
        public String guest;

        public BigInteger amount;
    }
}
