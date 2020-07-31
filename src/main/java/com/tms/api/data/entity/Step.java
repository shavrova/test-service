package com.tms.api.data.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "steps", uniqueConstraints = @UniqueConstraint(columnNames = "stepName"))
public class Step extends BaseEntity implements Serializable {

    @NotNull
    @Column(unique = true)
    private String stepId;

    @NotBlank
    private String stepName;

    private String comment;

    private String methodName;

    @ManyToMany(mappedBy = "steps")
    private List<Scenario> scenarios = new ArrayList<>();


}
