package Container;





import java.util.*;

// Class CSD
public class ComputerScienceDepartment {
    /*
    Instance Variables for CSD
     */
    protected static int EmployeeID = 100;
    protected static int StudentID = 1000;
    private ChairPerson chairPerson;
    private ArrayList<Faculty> faculties = new ArrayList<>();
    private ArrayList<UGrad> uGrads = new ArrayList<>();
    private ArrayList<Grad> grads = new ArrayList<>();
    private ArrayList<ProgramDirector> programDirectors = new ArrayList<>();

    /**
     * Constructor for CSD
     *
     * @param chairPerson
     */
    public ComputerScienceDepartment(ChairPerson chairPerson) {
        this.chairPerson = chairPerson;
    }

    /**
     * Getter Method For EmployeeID
     *
     * @return EmployeeID
     */
    public int getEmployeeID() {
        return this.chairPerson.getEmployeeID();
    }

    /*
    Getter Method for ChairPerson
    @return ChairPerson
     */
    public ChairPerson getChairPerson() {
        return this.chairPerson;
    }

    /**
     * Method that Hires Faculty and stores it in CSD records and assigns an appropriate Program Director
     *
     * @param faculty
     * @throws NoSpaceException
     */
    public void HireFaculty(Faculty faculty) throws NoSpaceException {
        if (this.faculties.size() == 70) throw new NoSpaceException();
        if (!this.faculties.contains(faculty)) {
            faculties.add(faculty);
            for (int i = 0; i < programDirectors.size(); i++) { // if the program director's program matches with the faculty's program then that factulty is added to ProgramDirector's Records
                if (programDirectors.get(i).getProgram().equals(faculty.getProgram()))
                    programDirectors.get(i).setFacultiesForProgramDirector(faculty);
            }
        }
    }

    /**
     * A generic Methods that checks for an object passed in an ArrayList as an input parameter.
     *
     * @param obj
     * @param people
     * @return true/false
     */
    public <T extends Person> boolean equals(T obj, ArrayList<? extends Person> people) {
        if (people.size() > 0) {
            for (Person p : people) {
                if (Objects.equals(people, obj)) return true;
            }
        }
        return false;
    }

    /**
     * Getter Method for Faculty
     *
     * @return ArrayList of faculties
     */
    public ArrayList<Faculty> getFaculty() {
        return faculties;
    }

    /**
     * Method that returns an integer which is a count of number of faculties stored in CSD records
     *
     * @return size of the ArrayList faculties
     */
    public int getNumOfFaculty() {
        return faculties.size();
    }

    /**
     * Admits an UnderGrad Student in the Department and assigns a faculty to it, the assignment is  first come, first served.
     *
     * @param uGrad
     * @throws NoSpaceException if the faculties available have no spaces or if the CSD department has reached limit of admitting Ugrad Students
     */
    public void AdmitStudent(UGrad uGrad) throws NoSpaceException {

        //  if(equals(uGrad,uGrads)) throw  new NoSpaceException();
        if (this.uGrads.size() == 500) throw new NoSpaceException();
        if (!(uGrads.contains(uGrad))) {
            populateFaculty(uGrad);
            uGrads.add(uGrad);
        }

    }

    /**
     * Method assigns the undergrad Student to a faculty
     *
     * @param ug
     * @throws NoSpaceException if faculties have reached the max limit for assigned Ugrad Students
     */
    private void populateFaculty(UGrad ug) throws NoSpaceException {
        for (int i = 0; i < faculties.size(); i++) {
            //if a faculty's record's size is less than 8 and if that faculty does not contain the student already then the student is added to faculty's records
            if (faculties.get(i).getAdvisingUgrads().size() < 8 && !faculties.get(i).getAdvisingUgrads().contains(ug)) {
                faculties.get(i).setAdvising(ug);
                ug.faculty = faculties.get(i);
                break;
            }
        }
    }

    /**
     * Method that HiresTA and assigns a Faculty as an advisor,works on the basis of first com first serve
     *
     * @param grad
     * @throws NoSpaceException if the faculty has exceeded the limit for assigned TA's
     */
    public void HireTA(Grad grad) throws NoSpaceException {
        if (this.grads.size() == 150) throw new NoSpaceException();
        if (!(grads.contains(grad))) {
            grads.add(grad);
            //For assigning advisor to the Grad Students
            for (int i = 0; i < faculties.size(); i++) {
                if (faculties.get(i).getTAs().size() < 5) {
                    faculties.get(i).setTAs(grad);
                    grad.advisor = faculties.get(i);
                    break;
                }
            }
        }
    }

    /**
     * Method that adds a Program Director to CSD records
     *
     * @param programDirector
     * @throws NoSpaceException if CSD exceeds the max limit for Program Directors
     */
    public void addProgramDirector(ProgramDirector programDirector) throws NoSpaceException {
        if (this.programDirectors.size() == 3) throw new NoSpaceException();
        this.programDirectors.add(programDirector);
    }

    /**
     * Method that Removes the faculty from CSD records and assigns the Grads and Ugrads to the next available Faculty
     *
     * @param faculty
     * @throws NoSpaceException
     * @throws NoSpecialtyException
     */
    public void RetireFaculty(Faculty faculty) throws NoSpecialtyException, NoSpaceException {
        faculties.remove(faculty);
        if (!equals(faculty, faculties)) throw new NoSpecialtyException();

        //check for the faculties of same program
        //populate them first
        //for TA's
        if (faculty.getTAs().size() > 0) {
            ArrayList<Faculty> sameProgramFaculties = new ArrayList<>();
            for (Faculty fac : faculties) {
                if (fac.getProgram().equals(faculty.getProgram())) sameProgramFaculties.add(fac);
            }
            //first populate the Grad students in to the faculties of the same program
            for (int i = 0; i < sameProgramFaculties.size(); i++) {
                if (sameProgramFaculties.get(i).getTAs().size() < 5) {
                    for (int j = 0; j < 5 - sameProgramFaculties.get(i).getTAs().size(); j++) {
                        faculty.getTAs().get(0).setAdvisor(faculties.get(i));
                        sameProgramFaculties.get(i).getTAs().add(faculty.getTAs().remove(0));
                    }
                }
            }

            //if all are full them assign them to the remaining ones
            if (faculty.getTAs().size() > 0) {
                for (int i = 0; i < faculties.size(); i++) {
                    if (faculties.get(i).getTAs().size() < 5) {
                        int x = faculties.get(i).getTAs().size();
                        for (int j = 0; j < 5 - x; j++) {
                            if (faculty.getTAs().size() == 0) break;
                            faculty.getTAs().get(0).setAdvisor(faculties.get(i));
                            faculties.get(i).getTAs().add(faculty.getTAs().remove(0));
                        }
                    }
                }
            }
        }
        if (faculty.getAdvisingUgrads().size() > 0) {
            ArrayList<Faculty> sameProgramFaculties = new ArrayList<>();
            for (Faculty fac : faculties) {
                if (fac.getProgram().equals(faculty.getProgram())) sameProgramFaculties.add(fac);
            }
            //first populate the Ugrad students in to the factulties of the same program
            for (int i = 0; i < sameProgramFaculties.size(); i++) {
                if (sameProgramFaculties.get(i).getAdvisingUgrads().size() < 8) {
                    for (int j = 0; j < 5 - sameProgramFaculties.get(i).getAdvisingUgrads().size(); j++) {
                        faculty.getAdvisingUgrads().get(0).setFaculty(faculties.get(i));
                        sameProgramFaculties.get(i).getAdvisingUgrads().add(faculty.getAdvisingUgrads().remove(0));
                    }
                }
            }
            if (faculty.getAdvisingUgrads().size() > 0) {
                for (int i = 0; i < faculties.size(); i++) {
                    if (faculties.get(i).getAdvisingUgrads().size() < 8) {
                        int x = faculties.get(i).getAdvisingUgrads().size();
                        for (int j = 0; j < 8 - x; j++) {
                            if (faculty.getAdvisingUgrads().size() == 0) break;
                            faculty.getAdvisingUgrads().get(0).setFaculty(faculties.get(i));
                            faculties.get(i).getAdvisingUgrads().add(faculty.getAdvisingUgrads().remove(0));
                        }
                    }
                }
            }
        }
    } //retire Faculty

    /**
     * Method that checks for Program of a Retiring faculty and returns a boolean
     *
     * @param faculty
     * @param faculties an Arraylist of faculty
     * @return true/false
     */
    public boolean equals(Faculty faculty, ArrayList<Faculty> faculties) {
        for (int i = 0; i < faculties.size(); i++) {
            if (faculties.get(i).getProgram().equals(faculty.getProgram())) return true;
        }
        return false;
    }


    /**
     * Methods returns number of Ugrad Students in CSD records
     *
     * @return number of Ugrad Students in CSD records
     */
    public int getNumOfUGradStudents() {
        return this.uGrads.size();
    }

    /**
     * Methods returns number of grad Students in CSD records
     *
     * @return number of grad Students in CSD records
     */
    public int getNumOfGradStudents() {
        return this.grads.size();
    }

    /**
     * Method removes Ugrad from records after Graduation and also from the records of the assigned faculty
     *
     * @param uGrad
     */
    public void AlumnusUGrad(UGrad uGrad) {
        this.uGrads.remove(uGrad);
        uGrad.getAdvisor().getAdvisingUgrads().remove(uGrad);
    }

    /**
     * Methods Method removes grad from records after post Graduation and also from the records of the assigned faculty
     *
     * @param grad
     * @throws NoTAException if that faculty has no Grad Students as TA's
     */
    public void AlumnusGrad(Grad grad) throws NoTAException {
        this.grads.remove(grad);
        grad.getAdvisor().getTAs().remove(grad);
        if (grad.getAdvisor().getTAs().size() == 0) throw new NoTAException();
    }

    /**
     * Method returns a sorted list of All Grad Students
     *
     * @return a sorted list of All Grad Students
     */
    public ArrayList<Grad> ExtractAllGradDetails() {
        ArrayList<Grad> list = this.grads;
        Collections.sort(list);
        return list;
    }

    /**
     * Method returns a sorted list of All UGrad Students
     *
     * @return a sorted list of All UGrad Students
     */
    public ArrayList<UGrad> ExtractAllUGradDetails() {
        ArrayList<UGrad> list = this.uGrads;
        Collections.sort(list);
        return list;
    }

    /**
     * Method returns a sorted list of All Faculties
     *
     * @return a sorted list of All Faculties
     */
    public ArrayList<Faculty> ExtractAllFacultyDetails() {
        ArrayList<Faculty> fac = this.faculties;
        Collections.sort(fac);
        return fac;
    }

    /**
     * Method returns a sorted list of All Faculties of the Program passed as a parameter
     *
     * @param Program
     * @return sorted list of All Faculties of the Program passed as a parameter
     */
    public ArrayList<Faculty> ExtractFacultyDetails(String Program) {
        ArrayList<Faculty> retFac = new ArrayList<>();
        for (Faculty faculty : faculties) {
            if (faculty.getProgram().equals(Program)) {
                retFac.add(faculty);
            }
        }
        Collections.sort(retFac);
        return retFac;
    }

    /**
     * Method that returns a sorted ArrayList of Ugrad students that have faculty passed as a parameter as faculty
     *
     * @param faculty
     * @return a sorted ArrayList of Ugrad students that have faculty passed as a parameter as faculty
     */
    public ArrayList<UGrad> ExtractAdviseesDetails(Faculty faculty) {
        ArrayList<UGrad> retUgrad = new ArrayList<>();
        for (UGrad uGrad : uGrads) {
            if (uGrad.getAdvisor().equals(faculty)) {
                retUgrad.add(uGrad);
            }
        }
        Collections.sort(retUgrad);
        return retUgrad;
    }

    /**
     * Method that returns a sorted ArrayList of Grad students that have faculty passed as a parameter as advisor
     *
     * @param faculty
     * @return a sorted ArrayList of Grad students that have faculty passed as a parameter as advisor
     */
    public ArrayList<Grad> ExtractTAsDetails(Faculty faculty) {
        ArrayList<Grad> retGrad = new ArrayList<>();
        for (Grad grad : grads) {
            if (grad.getAdvisor().equals(faculty)) {
                retGrad.add(grad);
            }
        }
        Collections.sort(retGrad);
        return retGrad;
    }

}// end of Class CSD

// Person Class this is  an abstract Class whosse
abstract class Person {
    /*
    Instance Variables for Person
     */
    protected String FirstName;
    protected String LastName;
    protected int Age;
    protected String Gender;
    protected String Address;

    /**
     * Constructor of Person  Class
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param gender
     * @param address
     */
    public Person(String firstName, String lastName, int age, String gender, String address) {
        FirstName = firstName;
        LastName = lastName;
        Age = age;
        Gender = gender;
        Address = address;
    }

    /**
     * toString Method
     *
     * @return returns a string representation of the instance variables of the Class
     */
    public String toString() {
        return "[" + FirstName + ", " + LastName + ", " + Age + ", " + Gender + ", " + Address + "]";
    }
}//person end

// Student Class that extends Person Class and Implements Comparable interface
class Student extends Person implements Comparable {
    /*
    Instance Variables for Student
     */
    protected int StudentID;

    /**
     * Constructor of Student  Class
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param gender
     * @param address
     */
    public Student(String firstName, String lastName, int age, String gender, String address) {
        super(firstName, lastName, age, gender, address);
    }

    /**
     * compareTo method in Student Class that Compares an obj's information which helps in sorting.
     *
     * @param obj
     * @return an integer based of comparision either 1,-1 or 0
     */
    @Override
    public int compareTo( Object obj) {
        Student obj1 = (Student) obj;
        if (this.FirstName.compareTo(obj1.FirstName) > 0) return 0;
        if (this.FirstName.compareTo(obj1.FirstName) < 0) return -1;
        return 1;
    }
}// end of class Student

// Class Ugrad Extends Student Class
class UGrad extends Student {
    /*
    Instance Variables for Ugrad
     */
    protected Faculty faculty;

    /**
     * Constructor of Ugrad  Class
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param gender
     * @param address
     */
    public UGrad(String firstName, String lastName, int age, String gender, String address) {
        super(firstName, lastName, age, gender, address);
        this.StudentID = ComputerScienceDepartment.StudentID;
        ComputerScienceDepartment.StudentID++;
    }

    /**
     * Setter Method for setting Faculty of Ugrad
     *
     * @param faculty
     */
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    /**
     * Getter Method for Ugrad
     *
     * @return faculty of Ugrad
     */

    public Faculty getAdvisor() {
        return this.faculty;
    }

    /**
     * toString Method
     *
     * @return returns a string representation of the instance variables of the Class
     */
    public String toString() {

        return "Undergraduate " + "[" + this.StudentID + "[" + super.toString() + "]]";
    }
}// end of Ugrad Class

class Grad extends Student {
    /*
    Instance Variables for Grad
     */
    protected Faculty advisor;

    /**
     * Constructor of Grad  Class
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param gender
     * @param address
     */
    public Grad(String firstName, String lastName, int age, String gender, String address) {
        super(firstName, lastName, age, gender, address);
        this.StudentID = ComputerScienceDepartment.StudentID;
        ComputerScienceDepartment.StudentID++;
    }

    /**
     * Getter Method for Grad
     *
     * @return Advisor of Grad
     */

    public Faculty getAdvisor() {
        return this.advisor;
    }

    /**
     * Setter Method for setting Advisor of Grad
     *
     * @param advisor
     */
    public void setAdvisor(Faculty advisor) {
        this.advisor = advisor;
    }

    /**
     * toString Method
     *
     * @return returns a string representation of the instance variables of the Class
     */
    public String toString() {
        return "Graduate " + "[" + this.StudentID + "[" + super.toString() + "]]";
    }
}// end of Class Grad

// Academics Class that Extends Person Class
class Academics extends Person {
    /*
    Instance Variables for Academics
     */
    protected int EmployeeID;
    protected double Salary;

    /**
     * Constructor of Academics  Class
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param gender
     * @param address
     */
    public Academics(String firstName, String lastName, int age, String gender, String address) {
        super(firstName, lastName, age, gender, address);
    }
}// end of Academics Class

// Faculty Class that Extends Academics Class Implements Comparable
class Faculty extends Academics implements Comparable {
    private String Program;
    private ArrayList<UGrad> uGrads = new ArrayList<>();
    private ArrayList<Grad> grads = new ArrayList<>();

    /**
     * Setter Method for setting EmployeeID
     *
     * @param employeeID
     */
    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    /**
     * Setter Method for setting EmployeeID
     *
     * @param sal
     */
    public void setSalary(double sal) {
        this.Salary = sal;
    }

    /**
     * compareTo method in Faculty Class that Compares an obj's information which helps in sorting.
     *
     * @param obj
     * @return an integer based of comparision either 1,-1 or 0
     */
    @Override
    public int compareTo( Object obj) {
        Faculty obj1 = (Faculty) obj;
        if (this.FirstName.compareTo(obj1.FirstName) > 0) return 0;
        if (this.FirstName.compareTo(obj1.FirstName) < 0) return -1;
        return 1;
    }

    /**
     * Constructor of Faculty  Class
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param gender
     * @param address
     */
    public Faculty(String firstName, String lastName, int age, String gender, String address) {
        super(firstName, lastName, age, gender, address);
        this.EmployeeID = ComputerScienceDepartment.EmployeeID;
        ComputerScienceDepartment.EmployeeID++;
    }

    /**
     * Setter Method for setting Program
     *
     * @param Program
     */
    public void setProgram(String Program) {
        this.Program = Program;
    }

    /**
     * Getter Method for Returning Program of Faculty
     *
     * @return program
     */
    public String getProgram() {
        return Program;
    }

    /**
     * Getter Method for Returning EmployeeID of Faculty
     *
     * @return EmployeeID
     */
    public int getEmployeeID() {
        return this.EmployeeID;
    }

    /**
     * Setter Method for setting Advising
     *
     * @param uGrad
     * @throws NoSpaceException
     */
    public void setAdvising(UGrad uGrad) throws NoSpaceException {
        if (this.uGrads.size() > 8) {
            throw new NoSpaceException();
        }
        this.uGrads.add(uGrad);
    }

    /**
     * Getter Method for Returning Ugrads of Faculty
     *
     * @return ArrayList of Ugrads
     */
    public ArrayList<UGrad> getAdvisingUgrads() {
        return this.uGrads;
    }

    /**
     * Getter Method for Returning Grads of Faculty
     *
     * @return ArrayList of Grads
     */
    public ArrayList<Grad> getTAs() {
        return this.grads;
    }

    /**
     * Setter Method for setting TA
     *
     * @param grad
     * @throws NoSpaceException
     */
    public void setTAs(Grad grad) throws NoSpaceException {
        if (this.grads.size() > 5) throw new NoSpaceException();
        this.grads.add(grad);
    }

    /**
     * Getter Method for Returning Number of UGrads of Faculty
     *
     * @return Number of UGrads
     */
    public int getNumOfAdvisingUGrads() {
        return this.uGrads.size();
    }

    /**
     * Getter Method for Returning Number of Grads of Faculty
     *
     * @return Number of Grads
     */
    public int getNumOfTAs() {
        return this.grads.size();
    }

    /**
     * toString Method
     *
     * @return returns a string representation of the instance variables of the Class
     */
    public String toString() {
        //return (String) this;
        return "Faculty " + this.Program + "[[" + this.EmployeeID + ", " + this.Salary + super.toString() + "]]";
    }
}// end of Class Faculty

//Class Administrator Extends Academics
class Administrator extends Academics {
    /*
    Instance Variables for Administrator
     */

    /**
     * Constructor of Administrator  Class
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param gender
     * @param address
     */
    public Administrator(String firstName, String lastName, int age, String gender, String address) {
        super(firstName, lastName, age, gender, address);
    }
}// end of Class Administrator

class ProgramDirector extends Administrator {
    /*
    Instance Variables for ProgramDirector
     */
    private String Program;
    private ArrayList<Faculty> facultiesForProgramDirector = new ArrayList<>();

    /**
     * Constructor of ProgramDirector  Class
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param gender
     * @param address
     */
    public ProgramDirector(String firstName, String lastName, int age, String gender, String address) {
        super(firstName, lastName, age, gender, address);
    }

    /**
     * Getter Method for Returning Program of Program Director
     *
     * @return program
     */
    public String getProgram() {
        return Program;
    }

    /**
     * Setter Method for setting Program
     *
     * @param program
     */
    public void setProgram(String program) {
        Program = program;
    }

    /**
     * Method for Assigning Faculties to Program Director
     *
     * @param faculty
     * @throws NoSpaceException if the ProgramDirector exceeds the maximum number of faculties that can be assigned
     */
    public void setFacultiesForProgramDirector(Faculty faculty) throws NoSpaceException {
        if (this.facultiesForProgramDirector.size() == 25) throw new NoSpaceException();
        this.facultiesForProgramDirector.add(faculty);
    }

}//end of Class Program Director

//ChairPerson Class Extends Administrator
class ChairPerson extends Administrator {
    /*
    Instance Variables for ChairPerson
     */
    private double Salary;

    /**
     * Constructor of ChairPerson  Class
     *
     * @param firstName
     * @param lastName
     * @param age
     * @param gender
     * @param address
     */
    public ChairPerson(String firstName, String lastName, int age, String gender, String address) {
        super(firstName, lastName, age, gender, address);
        this.EmployeeID = ComputerScienceDepartment.EmployeeID;
        ComputerScienceDepartment.EmployeeID++;
    }

    public double getSalary() {
        return Salary;
    }

    /**
     * Setter Method for setting EmployeeID
     *
     * @param salary
     */
    public void setSalary(double salary) {
        Salary = salary;
    }

    /**
     * Getter Method for Returning EmployeeID of ChairPerson
     *
     * @return EmployeeID
     */
    public int getEmployeeID() {
        return this.EmployeeID;
    }

    /**
     * toString Method
     *
     * @return returns a string representation of the instance variables of the Class
     */
    public String toString() {
        return "Chair Person " + "[[[" + this.EmployeeID + ", " + this.Salary + super.toString() + "]]]";
    }
}// end of the Class ChairPerson

// Class NoSpaceException Extends Exception
class NoSpaceException extends Exception {
    // Constructor
    public NoSpaceException() {
        super();
    }

    /**
     * OverLoaded Constructor
     *
     * @param message
     */
    public NoSpaceException(String message) {
        super(message);
    }
}// end of Class NoSpaceException

// Class NoTAException Extends Exception
class NoTAException extends Exception {
    // Constructor
    public NoTAException() {
        super();
    }

    /**
     * OverLoaded Constructor
     *
     * @param message
     */
    public NoTAException(String message) {
        super(message);
    }
}// end of Class NoTAException

// Class NoSpecialtyException Extends Exception
class NoSpecialtyException extends Exception {
    //Constructor
    public NoSpecialtyException() {
        super();
    }

    /**
     * OverLoaded Constructor
     *
     * @param message
     */
    public NoSpecialtyException(String message) {
        super(message);
    }
}// end of Class NoSpecialtyException




