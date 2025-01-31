package com.lovecloud.usermanagement.domain;

import com.lovecloud.blockchain.domain.Wallet;
import com.lovecloud.blockchain.exception.WalletAlreadyAssignedException;
import com.lovecloud.global.domain.CommonRootEntity;
import com.lovecloud.invitationmanagement.domain.Invitation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "couple")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Couple extends CommonRootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groom_id", nullable = false)
    private WeddingUser groom;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bride_id", nullable = false)
    private WeddingUser bride;

    @Column(name = "refund_account", length = 100)
    private String refundAccount;

    @Column(name = "refund_bank_name", length = 100)
    private String refundBankName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Builder
    public Couple(WeddingUser groom, WeddingUser bride, String refundAccount, String refundBankName,
            Invitation invitation) {
        this.groom = groom;
        this.bride = bride;
        this.refundAccount = refundAccount;
        this.refundBankName = refundBankName;
        this.invitation = invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

    public void assignWallet(Wallet wallet) {
        if(this.wallet != null){
            throw new WalletAlreadyAssignedException();
        }
        this.wallet = wallet;
    }

    public void deleteInvitation() {
        this.invitation = null;
    }
}
