package com.gym.skido.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ab_test_resource")
public class ABTestResource {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long ab_test_resource_id;
    @OneToOne
    @JoinColumn(name = "app_resource_id", nullable = false)
    private AppResource resource;
    private String resourceURL;
    private String abTestResourceURL;
    private long parent_resource_id;
    private long ab_test_id;
}
