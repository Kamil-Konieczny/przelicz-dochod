package com.przeliczdochod.przeliczdochod.pojos;

public class Rates
{
    private String no;

    private String mid;

    private String effectiveDate;

    public String getNo ()
    {
        return no;
    }

    public void setNo (String no)
    {
        this.no = no;
    }

    public String getMid ()
    {
        return mid;
    }

    public void setMid (String mid)
    {
        this.mid = mid;
    }

    public String getEffectiveDate ()
    {
        return effectiveDate;
    }

    public void setEffectiveDate (String effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [no = "+no+", mid = "+mid+", effectiveDate = "+effectiveDate+"]";
    }
}

