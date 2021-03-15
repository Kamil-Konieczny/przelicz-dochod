package com.przeliczdochod.przeliczdochod.pojos;


public class MyPojo
{
    private String code;

    private Rates[] rates;

    private String currency;

    private String table;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public Rates[] getRates ()
    {
        return rates;
    }

    public void setRates (Rates[] rates)
    {
        this.rates = rates;
    }

    public String getCurrency ()
    {
        return currency;
    }

    public void setCurrency (String currency)
    {
        this.currency = currency;
    }

    public String getTable ()
    {
        return table;
    }

    public void setTable (String table)
    {
        this.table = table;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", rates = "+rates+", currency = "+currency+", table = "+table+"]";
    }
}