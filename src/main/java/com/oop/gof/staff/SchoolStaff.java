package com.oop.gof.staff;

import java.util.List;

public abstract class SchoolStaff implements Cloneable{

    protected List<String> duties;

    public abstract void addDuty(String duty);

    public abstract List<String> getDuties();

    public abstract void setDuties(List<String> duties);

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
