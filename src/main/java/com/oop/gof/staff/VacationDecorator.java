package com.oop.gof.staff;

import org.apache.logging.log4j.LogManager;

import java.util.List;

public class VacationDecorator extends SchoolStaff{

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(VacationDecorator.class);

    private SchoolStaff schoolStaff;
    private int vacationDays;

    public VacationDecorator(int vacationDays, SchoolStaff schoolStaff) {
       this.schoolStaff = schoolStaff;
       this.vacationDays = vacationDays;
    }

    @Override
    public void addDuty(String duty) {
        schoolStaff.addDuty(duty);
    }

    @Override
    public List<String> getDuties() {
        LOGGER.info("Following duties cancelled for {} days", vacationDays);
        return schoolStaff.getDuties();
    }

    @Override
    public void setDuties(List<String> duties) {
        schoolStaff.setDuties(duties);
    }
}
