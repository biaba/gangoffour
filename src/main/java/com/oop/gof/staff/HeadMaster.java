package com.oop.gof.staff;

import java.util.ArrayList;
import java.util.List;

public class HeadMaster extends SchoolStaff{

    HeadMaster() {
        super.duties = new ArrayList<>();
        super.duties.add("responsible for teachers");
        super.duties.add("sessions with parents");
    }

    @Override
    public void addDuty(String duty) {
        super.duties.add(duty);
    }

    @Override
    public List<String> getDuties() {
        return super.duties;
    }

    @Override
    public void setDuties(List<String> duties) {
        this.duties = duties;
    }

    @Override
    public String toString() {
        return "HeadMaster{" +
                "duties=" + duties +
                '}';
    }
}
