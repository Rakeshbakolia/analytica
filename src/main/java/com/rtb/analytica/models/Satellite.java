package com.rtb.analytica.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "satellite")
@Builder
@Accessors(chain = true)
public class Satellite{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String satelliteCode;
    private String country;
    @Column(name = "launch_date")
    private Date launchDate;
    private Double mass;
    @ManyToOne
    @JoinColumn(name = "launcher_id", nullable = false)
    private Launcher launcher;
}
