package com.bandhan.usersService.util;

import static org.mindrot.jbcrypt.BCrypt.*;

public class Bcrypt {

    public static String hash(String s){
        return hashpw(s, gensalt());
    }

    public static boolean verify(String s, String hashed){
        return checkpw(s, hashed);
    }

}
