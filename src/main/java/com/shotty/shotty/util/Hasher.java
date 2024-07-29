package com.shotty.shotty.util;

import org.mindrot.jbcrypt.BCrypt;

public final class Hasher {

    private Hasher() {
    }

    public static String hashPassword(String plainPw) {
        return BCrypt.hashpw(plainPw, BCrypt.gensalt());
    }
}
