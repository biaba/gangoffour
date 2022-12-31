package com.oop.gof;

import com.oop.gof.history.SchoolArchive;
import com.oop.gof.school.BaseSchool;
import com.oop.gof.school.PrimarySchool;
import com.oop.gof.staff.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GofApplication {

    private static final Logger LOGGER = LogManager.getLogger(GofApplication.class);


    public static void main(String[] args) throws CloneNotSupportedException {

        SchoolStaffService schoolStaffService = new SchoolStaffService();
        // Prototype pattern - SchoolStaffRegister holds one instance per class of SchoolStaff child classes and can be requested when new instance
        // of the same class needed
        List<SchoolStaff> primarySchoolStaff = schoolStaffService.prototypePatternCreateSchoolStaff();
        GofApplication.printDuties(primarySchoolStaff);
        // Decorator pattern - *Decorator classed hold reference to SchoolStaff and implement the same interface. Duties can get
        // an "extra layer" on top of an existing duties
        decoratorPatter(primarySchoolStaff.get(6));
        // Bridge pattern - BaseSchool holds reference to SchoolStaff - both abstract classes and may extend independently
        BaseSchool primarySchool = new PrimarySchool(primarySchoolStaff);
        GofApplication.printSchoolInfo(primarySchool);
        // Memento pattern - The internal state of SchoolStaff at this point in time gets stored in ArchivedSchoolStaff
        mementoPattern(primarySchoolStaff);

    }

    public static void printDuties(List<SchoolStaff> primarySchoolStaff) {
        for (SchoolStaff schoolStaff : primarySchoolStaff) {
            printInfoAboutEmployee(schoolStaff);
            schoolStaff.getDuties()
                    .forEach(LOGGER::info);
        }
    }

    public static void printInfoAboutEmployee(SchoolStaff schoolStaff) {
        LOGGER.info("Employees role: {} ", schoolStaff);
        if (schoolStaff.getClass() == SupervisorTeacher.class) {
            LOGGER.info("Supervisor teacher is responsible for:");
            ((SupervisorTeacher) schoolStaff).getTeachersToSupervise().stream()
                    .forEach(LOGGER::info);
        }
    }

    public static void printSchoolInfo(BaseSchool baseSchool) {
        LOGGER.info("School services: {}", baseSchool.otherServices());
        LOGGER.info("School education: {} ", baseSchool.education());
        LOGGER.info("School staff:");
        baseSchool.schoolStaff().stream()
                .forEach(LOGGER::info);
    }

    public static void decoratorPatter(SchoolStaff schoolStaff) {
        SchoolStaff tempDuty = new TempDutyDecorator(3, schoolStaff, "organizing picnic");
        LOGGER.info("Getting addition duty on top of existing ones: ");
        tempDuty.getDuties().stream()
                .forEach(LOGGER::info);
        SchoolStaff vacationDecorator = new VacationDecorator(5, tempDuty);
        LOGGER.info("Now going to vacation: ");
        vacationDecorator.getDuties().stream()
                .forEach(LOGGER::info);
    }

    public static void mementoPattern(List<SchoolStaff> schoolStaff) {
        // adding all to archive
        SchoolArchive schoolArchive = new SchoolArchive();
        for (SchoolStaff staff : schoolStaff) {
            schoolArchive.addToArchive(staff);
        }
        // retrieving only staff of role Teacher
        LOGGER.info("Retrieved from archive:");
        schoolArchive.getFromArchiveByRole(Teacher.class).stream()
                .forEach(LOGGER::info);
    }
}
