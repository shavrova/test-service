package com.tms.api.data.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
    @Column(unique = true, updatable = false)
    private String stepId;

    @NotBlank
    private String stepName;

    private String comment;

    private String methodName;

}
