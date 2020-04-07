package com.armi.sample.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface JwtParser {
    Jws<Claims> parse(final String jwt) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException;
}
