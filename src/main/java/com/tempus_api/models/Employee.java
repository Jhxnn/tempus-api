package com.tempus_api.models;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID employeeId;

    private String name;

    private String password;

    private BigDecimal earningHour;

    @ManyToOne
    @JoinColumn(name = "enterprise_id",referencedColumnName = "id")
    private Enterprise enterprise;

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getEarningHour() {
        return earningHour;
    }

    public void setEarningHour(BigDecimal earningHour) {
        this.earningHour = earningHour;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
