package com.oop.gof.staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SchoolStaffRegister {

    private static Map<Class<? extends SchoolStaff>, SchoolStaff> register = new HashMap<>();

    static {
        HeadMaster headMaster = new HeadMaster();
        Teacher teacher = new Teacher();
        SupervisorTeacher supervisorTeacher = new SupervisorTeacher();
        register.put(HeadMaster.class, headMaster);
        register.put(Teacher.class, teacher);
        register.put(SupervisorTeacher.class, supervisorTeacher);
    }

    private SchoolStaffRegister() {
    }

    public static Object createStaffMember(Class<? extends SchoolStaff> staffClass) throws CloneNotSupportedException {
        SchoolStaff clonedObject = (SchoolStaff) register.get(staffClass).clone();
        clonedObject.setDuties(new ArrayList<>());
        if(clonedObject instanceof SupervisorTeacher) {
            SupervisorTeacher clonedSupervisorTeacher = (SupervisorTeacher) clonedObject;
            clonedSupervisorTeacher.setTeachersToSupervise(new ArrayList<>());
        return clonedSupervisorTeacher;
        }
        return clonedObject;
    }
}
