package com.recycledemo.model;

import java.io.Serializable;

/**
 * Created by Travis on 2016/10/22.
 */
public class User implements Serializable {
    private String userName;
    private String userName1;
    private String userName2;
    private String userName3;
    private String userName4;
    private String userName5;
    private String userName6;

    public User(String userName, String userName1, String userName2, String userName3, String userName4, String userName5, String userName6) {
        this.userName = userName;
        this.userName1 = userName1;
        this.userName2 = userName2;
        this.userName3 = userName3;
        this.userName4 = userName4;
        this.userName5 = userName5;
        this.userName6 = userName6;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName1() {
        return userName1;
    }

    public void setUserName1(String userName1) {
        this.userName1 = userName1;
    }

    public String getUserName2() {
        return userName2;
    }

    public void setUserName2(String userName2) {
        this.userName2 = userName2;
    }

    public String getUserName3() {
        return userName3;
    }

    public void setUserName3(String userName3) {
        this.userName3 = userName3;
    }

    public String getUserName4() {
        return userName4;
    }

    public void setUserName4(String userName4) {
        this.userName4 = userName4;
    }

    public String getUserName5() {
        return userName5;
    }

    public void setUserName5(String userName5) {
        this.userName5 = userName5;
    }

    public String getUserName6() {
        return userName6;
    }

    public void setUserName6(String userName6) {
        this.userName6 = userName6;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userName1='" + userName1 + '\'' +
                ", userName2='" + userName2 + '\'' +
                ", userName3='" + userName3 + '\'' +
                ", userName4='" + userName4 + '\'' +
                ", userName5='" + userName5 + '\'' +
                ", userName6='" + userName6 + '\'' +
                '}';
    }
}
