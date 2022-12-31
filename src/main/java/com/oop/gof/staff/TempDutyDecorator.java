package com.oop.gof.staff;

import org.apache.logging.log4j.LogManager;

import java.util.List;

public class TempDutyDecorator extends SchoolStaff{

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(TempDutyDecorator.class);

    private SchoolStaff schoolStaff;
    private int tempDutyDays;
    private String duty;

    public TempDutyDecorator(int tempDutyDays, SchoolStaff schoolStaff, String duty) {
        this.schoolStaff = schoolStaff;
        this.tempDutyDays = tempDutyDays;
        this.duty = duty;
    }

    @Override
    public void addDuty(String duty) {
        schoolStaff.addDuty(duty);
    }

    @Override
    public List<String> getDuties() {
        LOGGER.info("Following duties - {} - added for {} days", duty, tempDutyDays);
        return schoolStaff.getDuties();
    }

    @Override
    public void setDuties(List<String> duties) {
        schoolStaff.setDuties(duties);
    }
}
