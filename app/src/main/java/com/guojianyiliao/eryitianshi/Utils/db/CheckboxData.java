package com.guojianyiliao.eryitianshi.Utils.db;

public class CheckboxData {

    private String money;

    private String id;

    public CheckboxData(String id, String money) {
        this.id = id;
        this.money = money;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
