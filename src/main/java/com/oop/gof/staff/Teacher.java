package com.oop.gof.staff;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends SchoolStaff{


    Teacher() {
        super.duties = new ArrayList<>();
        super.duties.add("responsible for students");
        super.duties.add("organizing student activities");
    }
    @Override
    public void addDuty(String duty) {
        super.duties.add(duty);
    }

    @Override
    public List<String> getDuties() {
        return duties;
    }

    @Override
    public void setDuties(List<String> duties) {
        this.duties = duties;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "duties=" + duties +
                '}';
    }
}
