package com.benben.carmanager.model;


import java.io.Serializable;

//单词实体类
public class User implements Serializable {
    private int _id;
    private String username;
    private String password;
    private String nickName;
    private String idNumber;
    private String name;

    //默认构造方法
    public User() {             //默认构造方法
        super();
    }

    //定义有参数构造方法，初始化信息实体中的字段


    public User(int _id, String username, String password) {
        this._id = _id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String nickName, String idNumber) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.idNumber = idNumber;
    }

    public User(int _id, String username, String password, String nickName, String idNumber) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.idNumber = idNumber;
    }

    public User(int _id, String username, String password, String nickName, String idNumber, String name) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.idNumber = idNumber;
        this.name = name;
    }

    public User(String username, String password, String nickName, String idNumber, String name) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.idNumber = idNumber;
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
