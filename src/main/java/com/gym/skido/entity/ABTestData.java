package com.gym.skido.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Table(name = "ab_test_data")
public class ABTestData {
    @Id
    @SequenceGenerator(name = "robin", sequenceName = "abtestdataseq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "robin")
    @Column(name = "ab_test_data_id", updatable = false, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ab_test_id")
    private ABTest abTest;
    @ManyToOne
    @JoinColumn(name = "ab_test_user_id")
    private ABTestUser abTestUser;
    private boolean converted;
    @NotNull
    @Column(name = "sample_group_type", nullable = false)
    private Integer sampleGroupType;

    public ABTestData(ABTest test, ABTestUser user) {
        this.abTest = test;
        this.abTestUser = user;
    }
}
