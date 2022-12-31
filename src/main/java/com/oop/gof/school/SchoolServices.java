package com.oop.gof.school;

import com.oop.gof.staff.SchoolStaff;

import java.util.List;

public interface SchoolServices {

    List<EducationType> education();

    List<String> otherServices();

    List<SchoolStaff> schoolStaff();
}
