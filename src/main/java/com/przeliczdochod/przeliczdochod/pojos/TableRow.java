package com.przeliczdochod.przeliczdochod.pojos;

import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class TableRow {
    @NotNull
    double amount;
    double exchange_rate;
    double PLN_amount;
    @NotNull
    @Future
   // @Past LocalDate min_date = LocalDate.of(2012,1,1);
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    String symbol;
    LocalDate Exchange_rate_date;
    int id;
    public TableRow() {
    }

    public TableRow(double amount, double exchange_rate, double PLN_amount, Date date,String symbol) {
        this.amount = amount;
        this.exchange_rate = exchange_rate;
        this.PLN_amount = PLN_amount;
        this.date = LocalDate.of(date.getYear(),date.getMonth(),date.getDay());
        this.symbol=symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getExchange_rate_date() {
        return Exchange_rate_date;
    }

    public void setExchange_rate_date(LocalDate exchange_rate_date) {
        Exchange_rate_date = exchange_rate_date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(double exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    public double getPLN_amount() {
        return PLN_amount;
    }

    public void setPLN_amount(double PLN_amount) {
        this.PLN_amount = PLN_amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
