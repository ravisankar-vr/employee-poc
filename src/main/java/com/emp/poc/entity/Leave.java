package com.emp.poc.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "EMP_LEAVE", schema = "ems")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LV_ID")
    private Integer leaveId;

    @Column(name = "LV_TYP")
    private String leaveType;

    @Column(name = "TAKEN_LV")
    private String takenLeaves;

    @Column(name = "TOTAL_LV")
    private String totalLeaves;

    @Column(name = "BALANCE_LV")
    private String balanceLeaves;

   /* @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @JoinColumn(name = "EMP_ID")
    private Employee employee;*/

    /*@ManyToOne // or @OneToMany based on your use case
    @JoinColumn(name = "EMP_ID")
    private Employee employee;*/

    @OneToMany(mappedBy = "leave")
    private List<Employee> emps;

    public Leave(String leaveType, String takenLeaves, String totalLeaves, String balanceLeaves) {
        this.leaveType = leaveType;
        this.takenLeaves = takenLeaves;
        this.totalLeaves = totalLeaves;
        this.balanceLeaves = balanceLeaves;
        //this.employee = employee;
    }

    public Leave(Integer leaveId, String leaveType, String takenLeaves, String totalLeaves, String balanceLeaves) {
        this.leaveId=leaveId;
        this.leaveType = leaveType;
        this.takenLeaves = takenLeaves;
        this.totalLeaves = totalLeaves;
        this.balanceLeaves = balanceLeaves;
        //this.employee = employee;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getTakenLeaves() {
        return takenLeaves;
    }

    public void setTakenLeaves(String takenLeaves) {
        this.takenLeaves = takenLeaves;
    }

    public String getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(String totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public String getBalanceLeaves() {
        return balanceLeaves;
    }

    public void setBalanceLeaves(String balanceLeaves) {
        this.balanceLeaves = balanceLeaves;
    }

    /*public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }*/
}
