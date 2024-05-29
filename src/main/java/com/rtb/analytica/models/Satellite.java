package com.rtb.analytica.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "satellite")
@Builder
public class Satellite{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String satelliteId;
    private String country;
    @Column(name = "launch_date")
    private Date launchDate;
    private Double mass;
    @ManyToOne
    @JoinColumn(name = "launcher_id", nullable = false)
    private Launcher launcher;
}
