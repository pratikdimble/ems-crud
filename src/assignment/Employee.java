package day3.assignment;

import java.time.LocalDate;

class Employee extends Person {
    private int employeeId;
    private String designation;
    private String department;
    private LocalDate joiningDate;

    public Employee(int employeeId, String name, int age, String address, String department,
                    String designation, LocalDate joiningDate) {
        super(name, age, address);
        this.employeeId = employeeId;
        this.department = department;
        this.designation = designation;
        this.joiningDate = joiningDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }

    @Override
    public String toString() {
        return super.toString() + ", Employee ID: " + employeeId
                + ", Department: " + department
                + ", Designation: " + designation
                + ", Joining Date: " + joiningDate;
    }

   /* @Override
    public int compareTo(Employee o) {
        return this.joiningDate.compareTo(o.joiningDate);
    }*/
}

