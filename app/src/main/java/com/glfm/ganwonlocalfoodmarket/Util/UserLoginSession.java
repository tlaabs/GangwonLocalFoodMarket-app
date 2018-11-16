package com.glfm.ganwonlocalfoodmarket.Util;

import android.content.SharedPreferences;

public class UserLoginSession {
    static private UserLoginSession session = null;
    private String id;
    private String type;

    private UserLoginSession() {
    }

    public static void setUser(String id) {
        session.id = id;
    }

    public static String getUser(){
        return session.id;
    }

    public String getType() {
        return session.type;
    }

    public void setType(String type) {
        session.type = type;
    }

    public static UserLoginSession getSession() {
        if (session == null) {
            session = new UserLoginSession();
            return session;
        }
        else
            return session;
    }
}
