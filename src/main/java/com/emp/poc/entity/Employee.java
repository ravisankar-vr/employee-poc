package com.emp.poc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
@Builder
public class Employee {

    @Id
    @Column(name = "EMP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "EMP_FIRSTNM")
    @NotNull
    private String firstName;

    @Column(name = "EMP_LASTNM")
    @NotNull
    private String lastName;

    @NotNull
    @Column(name = "EMP_EMAIL")
    private String email;

    @Column(name = "SALARY")
    private double salary;

    @Column(name = "JOB_NM")
    private String jobNm;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "DEPT_ID")
    private Department department;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "LV_ID")
    private Leave leave;


    public Employee(Integer id, String firstName, String lastName, String email, double salary, String jobNm, Department department,Leave leave) {
        this.Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.jobNm = jobNm;
        this.department=department;
        this.leave = leave;
    }
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getJobNm() {
        return jobNm;
    }

    public void setJobNm(String jobNm) {
        this.jobNm = jobNm;
    }

     public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Leave getLeave() {
        return leave;
    }

    public void setLeave(Leave leave) {
        this.leave = leave;
    }
}
