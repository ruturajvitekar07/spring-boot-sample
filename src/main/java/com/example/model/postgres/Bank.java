package com.example.model.postgres;

import jakarta.persistence.*;

@Entity
@Table(name = "banks")
public class Bank{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bankId;
    private String bankName;
    private String branch;
    private String city;

    public Bank() {
    }

    public Bank(int bankId, String bankName, String branch, String city) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.branch = branch;
        this.city = city;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", branch='" + branch + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}