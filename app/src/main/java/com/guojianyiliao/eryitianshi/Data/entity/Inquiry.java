package com.guojianyiliao.eryitianshi.Data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/2 0002.
 * 问诊
 */
public class Inquiry implements Parcelable {
    private String icon;
    private String title;
    private String name;
    private String chatCost;
    private String adept;
    private String section;
    private String hospital;
    private String id;
    private String username;

    public Inquiry(String icon, String title, String name, String chatCost, String adept, String section, String hospital, String id, String username) {
        this.icon = icon;
        this.title = title;
        this.name = name;
        this.chatCost = chatCost;
        this.adept = adept;
        this.section = section;
        this.hospital = hospital;
        this.id = id;
        this.username = username;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChatCost() {
        return chatCost;
    }

    public void setChatCost(String chatCost) {
        this.chatCost = chatCost;
    }

    public String getAdept() {
        return adept;
    }

    public void setAdept(String adept) {
        this.adept = adept;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.chatCost);
        dest.writeString(this.adept);
        dest.writeString(this.section);
        dest.writeString(this.hospital);
        dest.writeString(this.id);
        dest.writeString(this.username);
    }

    protected Inquiry(Parcel in) {
        this.icon = in.readString();
        this.title = in.readString();
        this.name = in.readString();
        this.chatCost = in.readString();
        this.adept = in.readString();
        this.section = in.readString();
        this.hospital = in.readString();
        this.id = in.readString();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<Inquiry> CREATOR = new Parcelable.Creator<Inquiry>() {
        @Override
        public Inquiry createFromParcel(Parcel source) {
            return new Inquiry(source);
        }

        @Override
        public Inquiry[] newArray(int size) {
            return new Inquiry[size];
        }
    };
}
