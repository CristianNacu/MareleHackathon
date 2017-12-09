package com.farminator.farminator.utils;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sebastian on 09-Dec-17.
 */

public final class utils {
    private static final Map<String, String> USER_DATA = new HashMap<String, String>();
    static{
        USER_DATA.put("gigel", "gigel");
        USER_DATA.put("ramon", "ramon");
    }

    public static boolean checkUser(String username,String password){
        if(username.isEmpty() || password.isEmpty()){
            return false;
        }

        if(!USER_DATA.containsKey(username) || !(USER_DATA.get(username).equals(password))){
            return false;
        }

        return true;
    }
}
