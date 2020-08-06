package com.tms.api.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@Entity
@Table(name = "scenarios", uniqueConstraints = @UniqueConstraint(columnNames = "scenario_name"))
public class Scenario extends BaseEntity implements Serializable {

    @Column(nullable = false, unique = true, updatable = false, name = "scenario_id")
    private String scenarioId;

    @Column(nullable = false, length = 100, name = "scenario_name")
    private String scenarioName;

    @Column(length = 1000, name = "scenario_description")
    private String scenarioDescription;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = true)
    private Feature feature;

    @ManyToMany
    @JoinTable(
            name = "scenario_step",
            joinColumns = @JoinColumn(name = "scenario_id"),
            inverseJoinColumns = @JoinColumn(name = "step_id"))
    private List<Step> steps = new LinkedList<>();

}