package com.gym.skido.appbeans;


import com.gym.skido.entity.ABTestData;
import com.gym.skido.entity.AppResource;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class AppResourceBean {
    private Long id;
    private String appId;
    private String url;
    private boolean ABTestActive;
    private Long currentAbtestDataId;
    private ABTestData currentTestData;

    public AppResourceBean(AppResource appResource) {
        this.id = appResource.getResourceId();
        this.appId = appResource.getAppId();
        this.url = appResource.getFileURL();
        this.ABTestActive = appResource.isABTestActive();
    }

    public AppResourceBean() {

    }

    public void setCurrentTestData(ABTestData currentTestData) {
        this.currentTestData = currentTestData;
        this.currentAbtestDataId = currentTestData.getId();
    }

    public void setAppResource(AppResource appResource) {
        this.id = appResource.getResourceId();
        this.appId = appResource.getAppId();
        this.url = appResource.getFileURL();
        this.ABTestActive = appResource.isABTestActive();
    }
}
