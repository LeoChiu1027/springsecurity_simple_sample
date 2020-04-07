package com.armi.sample.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class AsKeyGenerator {


    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;


    public AsKeyGenerator(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);
    }

    public void createKeys() {
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public static void main(String[] args) {
        AsKeyGenerator keyGenerator;
        try {
            keyGenerator = new AsKeyGenerator(1024);
            keyGenerator.createKeys();
            keyGenerator.writeToFile("KeyPair/publicKey", keyGenerator.getPublicKey().getEncoded());
            keyGenerator.writeToFile("KeyPair/privateKey", keyGenerator.getPrivateKey().getEncoded());
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
