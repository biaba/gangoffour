package com.oop.gof.history;

import com.oop.gof.staff.SchoolStaff;
import org.apache.logging.log4j.LogManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArchivedSchoolStaff {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ArchivedSchoolStaff.class);

    private Class<? extends SchoolStaff> role;
    private LocalDate leaveDate;
    private List<String> duties = new ArrayList<>();

    ArchivedSchoolStaff(SchoolStaff schoolStaff) {
        this.role = schoolStaff.getClass();
        this.leaveDate = LocalDate.now();
        if(schoolStaff.getDuties().isEmpty()) {
            this.duties.addAll(new ArrayList<>(schoolStaff.getDuties()));
        }
        LOGGER.info("School staff - {} - added to archive on {}", this.role, this.leaveDate);
    }

    public Class<? extends SchoolStaff> getRole() {
        return role;
    }

    public void setRole(Class<? extends SchoolStaff> role) {
        this.role = role;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
    }

    @Override
    public String toString() {
        return "ArchivedSchoolStaff{" +
                "role=" + role +
                ", leaveDate=" + leaveDate +
                '}';
    }
}
