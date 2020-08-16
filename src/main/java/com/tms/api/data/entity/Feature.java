package com.tms.api.data.entity;

import lombok.*;

import javax.persistence.*;
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
@Table(name = "features", uniqueConstraints = @UniqueConstraint(columnNames = "feature_name"))
public class Feature extends BaseEntity implements Serializable {

    @Column(nullable = false, updatable = false, unique = true, name = "feature_id")
    private String featureId;

    @Column(nullable = false, length = 100, name = "feature_name")
    private String featureName;

    @Column(length = 100, name = "feature_description")
    private String featureDescription;


    @Column(name = "class_name")
    private String className;

    @OneToMany(mappedBy = "feature", orphanRemoval = true)
    private List<Scenario> scenarios = new ArrayList<>();
}
