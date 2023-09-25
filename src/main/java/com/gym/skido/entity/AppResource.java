package com.gym.skido.entity;


import com.gym.skido.appbeans.AppResourceBean;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_resource", schema = "abtests")
public class AppResource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resource_id", nullable = false)
    private Long resourceId;
    @Column(name = "app_id", nullable = false)
    private String appId;
    @Column(name = "url", nullable = false)
    private String fileURL;
    @Column(name = "abtest_active", nullable = false)
    private boolean isABTestActive;
    @Column(name = "ab_test_id", nullable = false)
    private Long abTestId;
}