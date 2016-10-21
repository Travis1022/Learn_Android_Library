package com.demo.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xuanwei.tian on 2016/10/18.
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    @Unique
    @Property(nameInDb = "user_number")
    private String userNumber;
    @NotNull
    @Property(nameInDb = "user_password")
    private String userPassword;

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNumber() {
        return this.userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1345332339)
    public User(Long id, @NotNull String userNumber, @NotNull String userPassword) {
        this.id = id;
        this.userNumber = userNumber;
        this.userPassword = userPassword;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userNumber='" + userNumber + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}

