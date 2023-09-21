package com.gym.skido.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "ab_test")
public class ABTest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ab_test_id", nullable = false)
    private Long testId;
    private int appID;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_resource_id", nullable = false)
    private AppResource appResource;
    private String testMethod;
    private String hypothesis;
    private String changeType;
    private int sampleSize;
    private double sampleDistribution;
    private boolean isActive;
    private boolean isCompleted;
    private long completeCount;
    private double significanceValue;
    private boolean isConcluded;
    private String conclusionReason;
    private Date createdDate;
    private Date updatedDate;
    private Date completedDate;

}