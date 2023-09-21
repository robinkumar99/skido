package com.gym.skido.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ab_test_user")
public class ABTestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ab_test_user_id", nullable = false)
    private Long id;
    @JoinColumn(name = "ab_test_id")
    private ABTest abTest;
    private long referenceUserId;
    private boolean isControlGroup;
    private String userProperties;

    public ABTestUser() {

    }

    public ABTestUser(ABTest abtestCurrent, String refUserId, String userProperties) {
        if (refUserId != null) {
            this.referenceUserId = Long.parseLong(refUserId);
        }
        this.abTest = abtestCurrent;
        this.userProperties = userProperties;
    }

    public void setUserProperties(String userProperties) {
        this.userProperties = userProperties;
    }

    public void setAbTest(ABTest abTest) {
        this.abTest = abTest;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReferenceUserId(long referenceUserId) {
        this.referenceUserId = referenceUserId;
    }

}