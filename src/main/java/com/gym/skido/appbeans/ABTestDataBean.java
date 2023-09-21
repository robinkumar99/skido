package com.gym.skido.appbeans;

import com.gym.skido.entity.ABTest;
import com.gym.skido.entity.ABTestUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;


@Getter
@Setter
@Entity
public class ABTestDataBean {
    private Long testDataId;
    private ABTest abTest;

    private ABTestUser abTestUser;
    private Integer sampleGroupType;
    private boolean converted;


}
