package com.gym.skido.entity;

import com.gym.skido.appbeans.ABTestBean;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    public ABTest (ABTestBean abTestBean, AppResource appResource){
        this.appID = abTestBean.getAppID();
        this.appResource = appResource;
        this.completeCount = abTestBean.getCompleteCount();
        this.changeType = abTestBean.getChangeType();
        this.hypothesis = abTestBean.getHypothesis();
        this.isActive = true;
        this.isCompleted = false;
        this.isConcluded = false;
        this.sampleDistribution = abTestBean.getSampleDistribution();
        this.sampleSize = abTestBean.getSampleSize();
        this.significanceValue = abTestBean.getSignificanceValue();
        this.testMethod = abTestBean.getTestMethod();
        this.createdDate = new Date(System.currentTimeMillis());
    }

    public ABTest() {

    }
}