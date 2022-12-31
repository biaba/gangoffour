# GangOfFour


# 1. Creational patterns
 # Prototype
   SchoolStaff contains register of created classes (HeadMaster, SupervisorTeacher, Teacher), which are deep cloned when requested.

# 2. Structural patterns
 # Composite 
   Subclass (SupervisorTeacher) extends (SchoolStaff) and contain reference to List<SchoolStaff>, which may contain Teacher or SupervisorTeacher
 # Bridge
   Abstract class BaseSchool contains reference to List<SchoolStaff (abstract class)>. Each of abstract classes may be extended independently.
 # Decorator
   VacationDecorator and TempDutyDecorator extend the SchoolStaff and hold the reference to it. It offers ability to add top duties on existing ones.

# 3. Behavioural patterns
 # Memento
   ArchivedSchoolStaff class holds internal state of SchoolStaff class at desired point in time. If requested, could be retrieved back.
   SchoolArchive class handles storing/retrieving cases.
    
    
