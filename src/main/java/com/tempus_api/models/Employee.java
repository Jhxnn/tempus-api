package com.tempus_api.models;


import jakarta.persistence.*;

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

    private double earningHour;

    private Enterprise enterprise;
}
