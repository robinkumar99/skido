package com.gym.skido.api;

import com.gym.skido.appbeans.ABTestBean;
import com.gym.skido.appbeans.AppResourceBean;
import com.gym.skido.entity.ABTestReport;

public interface ABTestService {
    /**
     * API to update A/B test if user proceeds further
     * @param abTestData (Update user response)
     * @return boolean (true if update is successful)
     */
    Boolean updateABTestData(AppResourceBean abTestData);


    /**
     * Create an A/B Test data before launching resource attached
     * @param resourceId (resource id)
     * @param currentUserId (user id of the current user)
     * @return AppResourceBean (resource bean from user session)
     */
    AppResourceBean createABTestData(String resourceId, String currentUserId);

    /**
     * API to create report calculations after completion of A/B Test
     * @param abtestId (A/B test)
     * @return void
     */
    ABTestReport getABTestReport(Long abtestId);

    /**
     * API to create a new A/B Test
     * @param appId (appId)
     * @param abTestBean (user inputs to create a Test)
     * @return boolean (true if transaction is successful)
     */
    Boolean createABTest(String appId, ABTestBean abTestBean);
}
