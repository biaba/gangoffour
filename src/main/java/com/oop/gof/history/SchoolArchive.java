package com.oop.gof.history;

import com.oop.gof.staff.SchoolStaff;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SchoolArchive {

    List<ArchivedSchoolStaff> archivedSchoolStaff = new ArrayList<>();

    public ArchivedSchoolStaff addToArchive(SchoolStaff schoolStaff) {
        ArchivedSchoolStaff schoolStaffToLeave = new ArchivedSchoolStaff(schoolStaff);
        archivedSchoolStaff.add(schoolStaffToLeave);
        return schoolStaffToLeave;
    }

    public List<ArchivedSchoolStaff> getFromArchiveByRole(Class<?> schoolStaff) {
        return archivedSchoolStaff.stream()
                .filter(archiveEntry ->
                        archiveEntry.getRole().isAssignableFrom(schoolStaff))
                .collect(Collectors.toList());
    }

    public ArchivedSchoolStaff getFromArchive(ArchivedSchoolStaff requestedArchivedStaff) {
        int requiredIndex = archivedSchoolStaff.indexOf(requestedArchivedStaff);
        return archivedSchoolStaff.get(requiredIndex);
    }
}
