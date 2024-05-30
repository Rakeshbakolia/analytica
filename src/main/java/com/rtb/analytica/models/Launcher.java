package com.rtb.analytica.models;

import com.rtb.analytica.enums.TypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.Date;
import java.util.Set;

@Getter @Setter
@Table(name = "launcher")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Launcher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String launcherId;
    @Enumerated(EnumType.STRING)
    private TypeEnum type;
    private Date registrationDate;
}
