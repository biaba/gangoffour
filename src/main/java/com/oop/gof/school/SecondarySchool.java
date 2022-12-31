package com.oop.gof.school;

import com.oop.gof.staff.SchoolStaff;

import java.util.ArrayList;
import java.util.List;

public class SecondarySchool extends BaseSchool{

    public SecondarySchool(List<SchoolStaff> schoolStaff){
        super.schoolStaff = schoolStaff;
    }

    @Override
    public List<EducationType> education() {
        return new ArrayList<>(List.of(EducationType.PRIMARY, EducationType.SECONDARY));
    }

    @Override
    public List<String> otherServices() {
        return new ArrayList<>(List.of("Sport Activities"));
    }

    @Override
    public List<SchoolStaff> schoolStaff() {
        return super.schoolStaff;
    }
}
