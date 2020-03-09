package com.armi.sample.security;

public interface PasswordEncryptor {

    public String[] encrypt(String plainText);
}
