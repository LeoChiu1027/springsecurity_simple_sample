package com.armi.sample.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;


@Component
public class JwtParserImpl implements JwtParser {

    @Autowired
    private static ResourceLoader resourceLoader;

    public Jws<Claims> parse(final String jwt) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException {

        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(generatePublicKey())
                .parseClaimsJws(jwt);
        //OK, we can trust this JWT
        return jws;
    }


    private PublicKey generatePublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        File file = new File(System.getProperty("user.dir")+"/"+"public_key.der");
        byte[] keyBytes = Files.readAllBytes(file.toPath());
        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(spec);
        return publicKey;
    }

}