package com.emp.poc.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Entity
@Table(name = "DEPARTMENT")
public class Department {

    @Id
    @Column(name = "DEPT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;

    @NotNull
    @Column(name = "DEPT_NAME")
    private String deptName;

   @OneToMany(mappedBy = "department")
   private List<Employee> emps;

    public Department(String deptName) {
        this.deptName = deptName;
    }

    public Department(Integer departmentId, String deptName) {
        this.departmentId = departmentId;
        this.deptName = deptName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
