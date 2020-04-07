package com.armi.sample.security;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface JwtBuilder {
    String build() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException;
}
