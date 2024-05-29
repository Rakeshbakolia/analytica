package com.rtb.analytica.models;

import com.rtb.analytica.enums.TypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter @Setter
@Table(name = "launcher")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Launcher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String launcherId;
    @Enumerated(EnumType.STRING)
    private TypeEnum type;
    private Date registrationDate;
}
