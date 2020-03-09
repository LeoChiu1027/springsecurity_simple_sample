package com.armi.sample.security;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Override
    public String encode(CharSequence charSequence) {
        return passwordEncryptor.encrypt(charSequence.toString())[0];
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return passwordEncryptor.encrypt(charSequence.toString())[0].equals(s);
    }
}
