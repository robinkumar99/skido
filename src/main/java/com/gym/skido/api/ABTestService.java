package com.gym.skido.api;

import com.gym.skido.appbeans.AppResourceBean;
import com.gym.skido.entity.ABTestReport;

public interface ABTestService {
    Boolean updateABTestData(AppResourceBean abTestData);

    AppResourceBean createABTestData(String resourceId, String currentUserId);

    ABTestReport getABTestReport(Long abtestId);
}
