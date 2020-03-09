package com.armi.sample.security;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

@Component
public class DefaultPasswordEncryptor implements PasswordEncryptor{

    @Override
    public String[] encrypt(String plainText) {
        RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
        //String salt = saltGenerator.nextBytes().toHex();
        String salt = "3dc0f1a07b16eea5321b2a0b17902f22";

        HashRequest.Builder builder = new HashRequest.Builder();
        builder.setSource(ByteSource.Util.bytes(plainText));
        builder.setSalt(salt);

        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(ByteSource.Util.bytes("privateSalt"));

        Hash hash = hashService.computeHash(builder.build());
        return new String[] {hash.toHex(), salt};
    }

    public static void main(String[] args) {
        DefaultPasswordEncryptor defaultPasswordEncryptor = new DefaultPasswordEncryptor();
        String[] s1 = new String[2];
        for(int i=0; i < 8; i++) {
            s1 = defaultPasswordEncryptor.encrypt("zaqwsx");
            out.println(s1[0] + "--" + s1[1]);
        }



//        for(int i=0; i < 8; i++) {
//            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
//            String  encode = bCryptPasswordEncoder.encode("zaqwsx");
//            out.println(encode);
//        }
    }
}
