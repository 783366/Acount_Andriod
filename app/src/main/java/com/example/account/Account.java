package com.example.account;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "account_table")
@TypeConverters({Converters.class})//数据库不支持Date类型,只能转化为时间戳Long类型
public class Account {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "time")
    private Date time;

    @ColumnInfo(name = "remarks")
    private String remarks;
    // 构造函数，getter和setter
    public Account(double amount, String type, Date time, String remarks){
        this.amount = amount;
        this.type = type;
        this.time = time;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
