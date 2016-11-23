package com.sky.pm.model;

import java.io.Serializable;

/**
 * Created by 机械革命 on 2016/8/14.
 */

public class User implements Serializable {


    /**
     * Id : 4
     * Name : 18322301874
     * NickName : qwe
     * Password : 123456
     * Phone : 18322301874
     */

    private int Id;
    private String Name;
    private String NickName;
    private String Password;
    private String Phone;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
}
