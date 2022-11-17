package com.example.velvethills.Models;

public class Order {
    private int id;
    private String code;
    private String dateCreate;
    private String time;
    private String dateClose;
    private String timeProcat;
    private String klient;
    private String status;

    public Order(int id, String code, String dateCreate, String time, String dateClose, String timeProcat, String klient, String status) {
        this.id = id;
        this.code = code;
        this.dateCreate = dateCreate;
        this.time = time;
        this.dateClose = dateClose;
        this.timeProcat = timeProcat;
        this.klient = klient;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateClose() {
        return dateClose;
    }

    public void setDateClose(String dateClose) {
        this.dateClose = dateClose;
    }

    public String getTimeProcat() {
        return timeProcat;
    }

    public void setTimeProcat(String timeProcat) {
        this.timeProcat = timeProcat;
    }

    public String getKlient() {
        return klient;
    }

    public void setKlient(String klient) {
        this.klient = klient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
