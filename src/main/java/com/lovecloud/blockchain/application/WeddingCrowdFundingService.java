package com.lovecloud.blockchain.application;

import com.lovecloud.blockchain.domain.WeddingCrowdFunding;
import com.lovecloud.blockchain.exception.SmartContractFundingNotCompletedException;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.blockchain.domain.WeddingCrowdFunding.CrowdfundingCreatedEventResponse;
import com.lovecloud.blockchain.exception.BlockchainException;
import com.lovecloud.infra.s3.KeyfileService;
import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WeddingCrowdFundingService {

    private final Web3j web3j;
    private final LCTokenService lcTokenService;
    private final KeyfileService keyfileService;

    @Value("${web3j.chain-id}")
    private BigInteger chainId;

    @Value("${web3j.funding-contract-address}")
    private String fundingContractAddress;

    @Value("${web3j.keyfile-password}")
    private String keyfilePassword;

    @Value("${web3j.company-wallet-file-name}")
    private String companyWalletFileName;
    /**
     * 블록체인에 펀딩을 생성하는 메서드
     *
     * @param goal        펀딩 목표 금액
     * @param duration    펀딩 기간
     * @param keyfileName S3에 저장된 사용자의 지갑 파일 이름
     * @return 생성된 blockchainFundingId
     * @throws BlockchainException 블록체인 연동 중 오류 발생 시 예외 처리
     */
    public BigInteger createCrowdfundingOnBlockchain(BigInteger goal, BigInteger duration, String keyfileName) {
        try {
            log.info("블록체인 펀딩 생성 시작. 목표 금액: {}, 기간: {}, 키파일: {}", goal, duration, keyfileName);

            // S3에서 지갑 파일을 가져옴
            String keyfileContent = keyfileService.downloadKeyfile(keyfileName);
            log.info("S3에서 지갑 파일 가져옴: {}", keyfileContent);

            // 펀딩 스마트 컨트랙트 로드
            WeddingCrowdFunding fundingContract = loadContract(keyfileContent);
            log.info("스마트 컨트랙트 로드 완료.");

            // 펀딩 트랜잭션 전송
            TransactionReceipt receipt = fundingContract.createCrowdfunding(goal, duration).send();

            List<CrowdfundingCreatedEventResponse> eventResponseList = fundingContract.getCrowdfundingCreatedEvents(receipt);
            if (eventResponseList.isEmpty()) {
                throw new BlockchainException("펀딩 생성 이벤트를 찾을 수 없습니다.");
            }

            return eventResponseList.get(0).crowdfundingId;
        } catch (Exception e) {
            log.error("블록체인에서 펀딩 생성 중 오류 발생", e);
            throw new BlockchainException("블록체인에서 펀딩 생성 중 오류 발생", e);
        }
    }

    /**
     * 토큰 사용 승인 후 블록체인에 펀딩 기여하는 메서드
     *
     * @param fundingId   기여할 블록체인 펀딩 ID
     * @param amount      기여 금액
     * @param keyfileName S3에 저장된 사용자의 지갑 파일 이름
     * @return 트랜잭션 해시
     * @throws BlockchainException 블록체인 연동 중 오류 발생 시 예외 처리
     */
    public String approveAndContribute(BigInteger fundingId, BigInteger amount, String keyfileName) {
        try {
            // S3에서 지갑 파일을 가져옴
            String keyfileContent = keyfileService.downloadKeyfile(keyfileName);

            // 토큰 사용 승인
            String approvalTxHash = lcTokenService.approveTokens(keyfileContent, amount);

            // 승인 결과 검증
            if (approvalTxHash == null || approvalTxHash.isEmpty()) {
                throw new BlockchainException("토큰 사용 승인에 실패하였습니다.");
            }

            // 펀딩 기여
            return contributeToFunding(fundingId, amount, keyfileContent);
        } catch (Exception e) {
            throw new BlockchainException("블록체인 펀딩 기여 중 에러 발생", e);
        }
    }

    public String cancelContribution(BigInteger fundingId, String keyfileName) {
        try {
            // S3에서 지갑 파일을 가져옴
            String keyfileContent = keyfileService.downloadKeyfile(keyfileName);
            log.info("S3에서 지갑 파일 가져옴: {}", keyfileContent);

            // 펀딩 스마트 컨트랙트 로드
            WeddingCrowdFunding fundingContract = loadContract(keyfileContent);
            log.info("스마트 컨트랙트 로드 완료.");

            // 펀딩 트랜잭션 전송
            TransactionReceipt receipt = fundingContract.cancelContribution(fundingId).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            log.error("블록체인 펀딩 참여 취소 중 에러 발생", e);
            throw new BlockchainException("블록체인 펀딩 참여 취소 중 에러 발생", e);
        }
    }

    public String cancelCrowdFunding(BigInteger fundingId, String keyfileName) {
        try {
            // S3에서 지갑 파일을 가져옴
            String keyfileContent = keyfileService.downloadKeyfile(keyfileName);
            log.info("S3에서 지갑 파일 가져옴: {}", keyfileContent);

            // 펀딩 스마트 컨트랙트 로드
            WeddingCrowdFunding fundingContract = loadContract(keyfileContent);
            log.info("스마트 컨트랙트 로드 완료.");

            // 펀딩 트랜잭션 전송
            TransactionReceipt receipt = fundingContract.cancelCrowdfunding(fundingId).send();
            log.info("펀딩 취소 트랜잭션 해시: {}", receipt.getTransactionHash());

            return receipt.getTransactionHash();
        } catch (Exception e) {
            log.error("블록체인 펀딩 참여 취소 중 에러 발생", e);
            throw new BlockchainException("블록체인 펀딩 참여 취소 중 에러 발생", e);
        }
    }

    /**
     * 주문 완료 메서드
     * @param keyfileName 사용자의 지갑 파일 경로
     * @param fundingId 주문 완료할 펀딩 ID
     * @return 트랜잭션 해시
     * @throws Exception 블록체인 연동 중 오류 발생 시 예외 처리
     * */
    public String completeOrder(String keyfileName, BigInteger fundingId) throws Exception {
        try {
            // S3에서 지갑 파일을 가져옴
            String keyfileContent = keyfileService.downloadKeyfile(keyfileName);
            log.info("S3에서 지갑 파일 가져옴: {}", keyfileContent);

            // 펀딩 스마트 계약 로드
            WeddingCrowdFunding fundingContract = loadContract(keyfileContent);
            log.info("스마트 컨트랙트 로드 완료.");

            // 펀딩 완료 및 주문 트랜잭션 전송
            TransactionReceipt receipt = fundingContract.completeOrder(fundingId).send();

            return receipt.getTransactionHash();
        } catch (Exception e) {
            // 펀딩이 종료되지 않은 경우 FundingNotCompletedException 발생
            if(e.getMessage().contains("ended yet")){
                throw new SmartContractFundingNotCompletedException();
            }else{
                log.error("블록체인 펀딩 참여 취소 중 에러 발생", e);
                throw new BlockchainException("블록체인 펀딩 참여 취소 중 에러 발생", e);
            }
        }
    }


    /**
     * 펀딩 기여 메서드
     * 블록체인에 펀딩 기여를 처리하는 메서드
     *
     * @param fundingId      기여할 블록체인 펀딩 ID
     * @param amount         기여 금액
     * @param keyfileContent 사용자의 지갑 파일 내용
     * @return 트랜잭션 해시
     * @throws BlockchainException 블록체인 연동 중 오류 발생 시 예외 처리
     */
    private String contributeToFunding(BigInteger fundingId, BigInteger amount, String keyfileContent) {
        try {
            // 펀딩 스마트 계약 로드
            WeddingCrowdFunding fundingContract = loadContract(keyfileContent);

            // 펀딩 트랜잭션 전송
            TransactionReceipt receipt = fundingContract.contribute(fundingId, amount).send();

            // 트랜잭션 해시 반환
            return receipt.getTransactionHash();
        } catch (Exception e) {
            throw new BlockchainException("블록체인 펀딩 기여 트랜잭션 전송 중 오류 발생", e);
        }
    }

    /**
     * 토큰 사용 승인 후 주문을 취소하는 메서드
     * @param fundingId      취소할 펀딩 ID
     * @param keyfileName    사용자의 지갑 파일 이름
     * @param amount         환불받을 토큰 금액
     * */
    public String approveAndCancelOrder(BigInteger fundingId, String keyfileName, BigInteger amount) throws Exception {
        // 토큰 사용 승인
        String approvalTxHash = lcTokenService.approveTokens(companyWalletFileName, amount);

        // 승인 결과 검증
        if (approvalTxHash == null || approvalTxHash.isEmpty()) {
            throw new IllegalStateException("토큰 사용 승인에 실패하였습니다.");
        }

        // 펀딩 취소
        return cancelOrder(keyfileName, fundingId);
    }

    /**
     * 주문 취소 메서드
     *
     * @param keyfileName 사용자의 지갑 파일 경로
     * @param fundingId      취소할 펀딩 ID
     *
     * */
    private String cancelOrder(String keyfileName, BigInteger fundingId) throws Exception {
        WeddingCrowdFunding fundingContract = loadContract(keyfileName);
        TransactionReceipt receipt = fundingContract.cancelOrder(fundingId).send();
        return receipt.getTransactionHash();
    }

    /**
     * 지갑 정보를 로드하고 트랜잭션 매니저를 생성한 후, 스마트 컨트랙트를 로드하는 메서드
     *
     * @param keyfileContent 사용자의 지갑 파일 내용
     * @return WeddingCrowdFunding 스마트 컨트랙트 인스턴스
     * @throws Exception 블록체인 연동 중 오류 발생 시 예외 처리
     */
    private WeddingCrowdFunding loadContract(String keyfileContent) {
        try {
            log.info("스마트 컨트랙트 로드 시작.");

            // 사용자 지갑 정보 로드
            Credentials credentials = WalletUtils.loadJsonCredentials(keyfilePassword, keyfileContent);
            log.info("지갑 정보 로드 완료.");

            // 트랜잭션 매니저 생성 (chainId 포함)
            TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, chainId.intValue());
            log.info("트랜잭션 매니저 생성 완료. chainId: {}", chainId);

            // 스마트 컨트랙트 로드
            return WeddingCrowdFunding.load(fundingContractAddress, web3j, transactionManager,
                    new DefaultGasProvider());
        } catch (Exception e) {
            log.error("스마트 컨트랙트를 로드하는 중 오류 발생", e);
            throw new BlockchainException("스마트 컨트랙트를 로드하는 중 오류 발생", e);
        }
    }
}
