package com.oop.gof.staff;

import java.util.*;

public class SchoolStaffService {

// Creational pattern - prototype.
    private List<SchoolStaff> primarySchoolStaff = new ArrayList<>();

    private Map<TeacherType, SchoolStaff> teachers = new EnumMap<>(TeacherType.class);

    public List<SchoolStaff> prototypePatternCreateSchoolStaff() throws CloneNotSupportedException {

        // One headmaster per school
        HeadMaster headMaster = (HeadMaster) SchoolStaffRegister.createStaffMember(HeadMaster.class);
        primarySchoolStaff.add(headMaster);

        // Create teachers based on amount of teacher types required (enum values)
        createTeachers();

        compositePatternCreateSuperVisors();

        return primarySchoolStaff;
    }

    public void createTeachers() throws CloneNotSupportedException {
        for(int numberOfRequiredTeachers = TeacherType.values().length; numberOfRequiredTeachers > 0 ; numberOfRequiredTeachers--) {
            SchoolStaff teacher = (Teacher) SchoolStaffRegister.createStaffMember(Teacher.class);
            // add responsibility specific to teacher profile
            teacher.addDuty(TeacherType.values()[numberOfRequiredTeachers-1].toString());
            teachers.put(TeacherType.values()[numberOfRequiredTeachers-1], teacher);
            primarySchoolStaff.add(teacher);
        }
    }

    public void compositePatternCreateSuperVisors() throws CloneNotSupportedException {
        SupervisorTeacher supervisorTeacher = (SupervisorTeacher) SchoolStaffRegister.createStaffMember(SupervisorTeacher.class);

// Structural pattern - composite. SchoolStaff may contain list of Teachers to supervise.
        // In turn - each teacher may or may not contain that list
        supervisorTeacher.addTeacherToSupervise(teachers.get(TeacherType.BIO));
        supervisorTeacher.addTeacherToSupervise(teachers.get(TeacherType.ENGLISH));
        supervisorTeacher.addDuty("sessions with headmaster");
        primarySchoolStaff.add(supervisorTeacher);

        // this supervisor will contain supervisor above, within list of persons to supervise
        SupervisorTeacher supervisorTeacher2 = (SupervisorTeacher) SchoolStaffRegister.createStaffMember(SupervisorTeacher.class);
        supervisorTeacher2.addTeacherToSupervise(teachers.get(TeacherType.SPORT));
        supervisorTeacher2.addTeacherToSupervise(supervisorTeacher);
        supervisorTeacher.addDuty("sport activities");
        primarySchoolStaff.add(supervisorTeacher2);
    }
}
