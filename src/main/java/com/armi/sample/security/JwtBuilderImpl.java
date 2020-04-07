package com.armi.sample.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtBuilderImpl implements JwtBuilder {


    public String build() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 10);

        String jws = Jwts.builder().signWith(SignatureAlgorithm.RS256, generatePrivateKey())
                .setIssuer("test")
                .setSubject("api")
                .setAudience("leo")
                .setExpiration(cal.getTime())
                .setNotBefore(new Date())
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString())
                .claim("username", "leo")
                .compact();
        return jws;
    }


    private PrivateKey generatePrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        File file = new File(System.getProperty("user.dir")+"/"+"private_key.der");
        byte[] keyBytes = Files.readAllBytes(file.toPath());
        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);
        return privateKey;
    }

    public static void main(String args[]) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        JwtBuilder jwtBuilder = new JwtBuilderImpl();
        System.out.println(jwtBuilder.build());
    }
}
