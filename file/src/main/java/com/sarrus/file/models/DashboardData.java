package com.sarrus.file.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "dashboard_data")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User user;
    private Double value1;
    private Double value2;
    private Double value3;
    private Double value4;
    private Double value5;
}
