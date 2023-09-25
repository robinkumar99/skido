package com.gym.skido.appbeans;

import com.gym.skido.entity.AppResource;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.sql.Date;

@Getter
@Setter
@Entity
public class ABTestBean {
    private Long testId;
    private int appID;
    private Long appResourceId;
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
