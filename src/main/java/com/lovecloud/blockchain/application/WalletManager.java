package com.lovecloud.blockchain.application;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletUtils;

import java.io.File;


@Service
public class WalletManager {

    private final String password = "password";

    public String creatWallet() {

        ECKeyPair ecKeyPair = null;

        try{
            ecKeyPair = Keys.createEcKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create wallet");
        }

        String walletDirectory = "./";

        try{
            return WalletUtils.generateWalletFile(password, ecKeyPair, new File(walletDirectory), true);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create wallet", e);
        }

    }


    public Credentials verifyWallet(String walletFileName) {
        String walletDirectory = "./";
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(password, walletDirectory + walletFileName);
            return credentials;
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify wallet", e);
        }
    }
}
