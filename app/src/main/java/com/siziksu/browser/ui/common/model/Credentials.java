package com.siziksu.browser.ui.common.model;

public class Credentials {

    public String user;
    public String password;

    @Override
    public String toString() {
        return "{" + user + ", " + password + "}";
    }
}
