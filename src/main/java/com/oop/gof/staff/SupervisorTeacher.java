package com.oop.gof.staff;

import java.util.ArrayList;
import java.util.List;

public class SupervisorTeacher extends SchoolStaff{

    private List<SchoolStaff> teachersToSupervise;

    SupervisorTeacher() {
        super.duties = new ArrayList<>();
        super.duties.add("responsible for students");
        super.duties.add("organizing student activities");
        teachersToSupervise = new ArrayList<>();
    }

    @Override
    public void addDuty(String duty) {
        super.duties.add(duty);
    }

    @Override
    public List<String> getDuties() {
        return duties;
    }

    public List<SchoolStaff> getTeachersToSupervise() {
        return teachersToSupervise;
    }

    public void addTeacherToSupervise(SchoolStaff teacher) {
        teachersToSupervise.add(teacher);
    }

    @Override
    public void setDuties(List<String> duties) {
        this.duties = duties;
    }

    @Override
    public String toString() {
        return "SupervisorTeacher{" +
                "teachersToSupervise=" + teachersToSupervise.size() +
                ", duties=" + duties.size() +
                '}';
    }

    public void setTeachersToSupervise(List<SchoolStaff> teachersToSupervise) {
        this.teachersToSupervise = teachersToSupervise;
    }
}
