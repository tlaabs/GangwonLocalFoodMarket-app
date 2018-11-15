package com.glfm.ganwonlocalfoodmarket.Util;

import android.content.SharedPreferences;

public class UserLoginSession {
    static private UserLoginSession session = null;
    private String id;

    private UserLoginSession() {
    }

    public static void setUser(String id) {
        if (session == null) {
            session = new UserLoginSession();
        }else {
            session.setUser(id);
        }
    }

    public static String getUser() {
        return session.getUser();
    }
}
