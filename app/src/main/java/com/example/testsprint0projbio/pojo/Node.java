package com.example.testsprint0projbio.pojo;

public class Node {
    private String uuid;
    private int user;

    public Node(String uuid, int user) {
        this.uuid = uuid;
        this.user = user;
    }

    public Node(String uuid) {
        this.uuid = uuid;
        this.user = 0;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
