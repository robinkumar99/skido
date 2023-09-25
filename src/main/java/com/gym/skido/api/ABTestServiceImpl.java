package com.gym.skido.api;

import com.gym.skido.appbeans.ABTestBean;
import com.gym.skido.appbeans.AppResourceBean;
import com.gym.skido.context.ABTestCrudOperations;
import com.gym.skido.entity.*;

public class ABTestServiceImpl implements ABTestService {


    @Override
    public Boolean createABTest(String appId, ABTestBean abtestbean) {
        abtestbean.setAppID(Integer.parseInt(appId));
        ABTestCrudOperations crudOps =  new ABTestCrudOperations();
        Long resourceId = abtestbean.getAppResourceId();
        AppResource appResource = crudOps.findAppResource(resourceId);
        ABTest abTest = new ABTest(abtestbean,appResource);
        crudOps.insertABTest(abTest);
        return true;
    }

    /**
     *
     * @param appResBean
     * @return
     */
    @Override
    public Boolean updateABTestData(AppResourceBean appResBean) {
        ABTestCrudOperations crudOps = new ABTestCrudOperations();
        ABTestData currentTestData = appResBean.getCurrentTestData();
        if (currentTestData != null) {
            crudOps.updateTesData(currentTestData);
            ABTestUser currentUser = currentTestData.getAbTestUser();
            if (currentUser != null)
                crudOps.updateABTestUser(currentUser);
        }
        return true;
    }

    /**
     * @param ResourceId:    Key of the App Resource on which test is being performed
     * @param currentUserId: Not null when user is a registered user and system can detect it
     * @return AppResourceBean
     */
    @Override
    public AppResourceBean createABTestData(String ResourceId, String currentUserId) {
        AppResourceBean appResBean = new AppResourceBean();
        ABTestCrudOperations ABTestCrudOperations = new ABTestCrudOperations();
        AppResource appResource = ABTestCrudOperations.getAppResource(ResourceId);
        appResBean.setAppResource(appResource);
        if (appResource.isABTestActive()) {
            ABTest abtestCurrent = ABTestCrudOperations.findTest(appResource.getAbTestId());
            //Count of data in experiment sample group
            long count = ABTestCrudOperations.getCountofExperimentalData(abtestCurrent);
            if (count < abtestCurrent.getCompleteCount()) {
                //Create/Update Test Data User
                ABTestUser user = new ABTestUser(abtestCurrent, currentUserId, "NewFromDehradun");
                user = ABTestCrudOperations.getUserByReferenceId(user);
                //Create TestData
                ABTestData testData = new ABTestData(abtestCurrent, user);
                //Set Sample Group Type Control/Experiment (0/1)
                testData.setSampleGroupType((Math.random() * 100 <= abtestCurrent.getSampleDistribution()) ? 1 : 0);
                testData.setId(ABTestCrudOperations.getNextID("ab_test_data"));
                // Save TestData
                ABTestCrudOperations.insertTesData(testData);
                appResBean.setCurrentTestData(testData);
            }
            double experimentalGroupSize = (abtestCurrent.getSampleDistribution() * abtestCurrent.getSampleSize()) / 100;
            count++;
            if (count == experimentalGroupSize) {
                //Update test and Make Calculations
                abtestCurrent.setActive(false);
                ABTestCrudOperations.updateTest(abtestCurrent);
                getABTestReport(appResource.getAbTestId());
            }
        }
        return appResBean;
    }

    @Override
    public ABTestReport getABTestReport(Long abTestId) {
        ABTestCrudOperations crudOps = new ABTestCrudOperations();
        ABTest abTest = crudOps.findTest(abTestId);
        ABTestReport abTestReport = new ABTestReport(abTest);
        if (!abTest.isActive() && abTest.isCompleted()) {
            abTestReport = crudOps.calculateMetricsForABTest(abTest);
        }
        return abTestReport;
    }
}
