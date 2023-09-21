package com.gym.skido.api;

import com.gym.skido.appbeans.AppResourceBean;
import com.gym.skido.context.CrudOperations;
import com.gym.skido.entity.*;

public class ABTestServiceImpl implements ABTestService {

    @Override
    public Boolean updateABTestData(AppResourceBean appResBean) {
        CrudOperations crudOps = new CrudOperations();
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
        CrudOperations crudOperations = new CrudOperations();
        AppResource appResource = crudOperations.getAppResource(ResourceId);
        appResBean.setAppResource(appResource);
        if (appResource.isABTestActive()) {
            ABTest abtestCurrent = crudOperations.findTest(appResource.getAbTestId());
            //Count of data in experiment sample group
            long count = crudOperations.getCountofExperimentalData(abtestCurrent);
            if (count < abtestCurrent.getCompleteCount()) {
                //Create/Update Test Data User
                ABTestUser user = new ABTestUser(abtestCurrent, currentUserId, "NewFromDehradun");
                user = crudOperations.getUserByReferenceId(user);
                //Create TestData
                ABTestData testData = new ABTestData(abtestCurrent, user);
                //Set Sample Group Type Control/Experiment (0/1)
                testData.setSampleGroupType((Math.random() * 100 <= abtestCurrent.getSampleDistribution()) ? 1 : 0);
                testData.setId(crudOperations.getABTestDatID());
                // Save TestData
                crudOperations.insertTesData(testData);
                appResBean.setCurrentTestData(testData);
            }
            double experimentalGroupSize = (abtestCurrent.getSampleDistribution() * abtestCurrent.getSampleSize()) / 100;
            count++;
            if (count == experimentalGroupSize) {
                //Update test and Make Calculations
                abtestCurrent.setActive(false);
                crudOperations.updateTest(abtestCurrent);
                getABTestReport(appResource.getAbTestId());
            }
        }
        return appResBean;
    }

    @Override
    public ABTestReport getABTestReport(Long abTestId) {
        CrudOperations crudOps = new CrudOperations();
        ABTest abTest = crudOps.findTest(abTestId);
        ABTestReport abTestReport = new ABTestReport(abTest);
        if (!abTest.isActive() && abTest.isCompleted()) {
            abTestReport = crudOps.calculateMetricsForABTest(abTest);
        }
        return abTestReport;
    }
}
