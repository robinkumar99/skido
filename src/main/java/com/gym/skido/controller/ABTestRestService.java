package com.gym.skido.controller;

import com.gym.skido.api.ABTestService;
import com.gym.skido.api.ABTestServiceImpl;
import com.gym.skido.appbeans.ABTestBean;
import com.gym.skido.appbeans.AppResourceBean;
import com.gym.skido.entity.ABTestReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abtest")
public class ABTestRestService {
    public static final Logger logger = LoggerFactory.getLogger(ABTestRestService.class);
    ABTestService abTestService = new ABTestServiceImpl();

    /**
     * API to create a new A/B Test
     * @param id (appId)
     * @param abtestbean (user inputs to create a Test)
     * @return boolean (true if transaction is successful)
     */
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> createABTest(@PathVariable("id") String id, @RequestBody ABTestBean abtestbean) {
        logger.info("Creating Data for an A/B test before loading resource " + id);
        return new ResponseEntity<Boolean>(abTestService.createABTest(id,abtestbean), HttpStatus.OK);
    }

    /**
     * Create an A/B Test data before launching resource attached
     * @param id (resource id)
     * @param userId (user id of the current user)
     * @return AppResourceBean (resource bean from user session)
     */
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.GET)
    public ResponseEntity<AppResourceBean> createABTestData(@PathVariable("id") String id, @RequestParam String userId) {
        logger.info("Creating Data for an A/B test before loading resource " + id);
        return new ResponseEntity<AppResourceBean>(abTestService.createABTestData(id, userId), HttpStatus.OK);
    }

    /**
     * API to update A/B test if user proceeds further
     * @param id (resource id)
     * @param appResourceBean (resource bean from user session which holds the A/B test data attached as well
     * @return boolean (true if update is successful)
     */
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateABTestData(@PathVariable("id") String id, @RequestBody AppResourceBean appResourceBean) {
        logger.info("Capturing conversion for " + id);
        return new ResponseEntity<Boolean>(abTestService.updateABTestData(appResourceBean), HttpStatus.OK);
    }

    /**
     * API to create report calculations after completion of A/B Test
     * @param testId (A/B test Id)
     * @return void
     */
    @RequestMapping(value = "/resource/report/{testId}", method = RequestMethod.GET)
    public ResponseEntity<ABTestReport> getABTestReport(@PathVariable("testId") String testId) {
        logger.info("Creating A/B test calculations for " + testId);
        return new ResponseEntity<ABTestReport>(abTestService.getABTestReport(Long.parseLong(testId)), HttpStatus.OK);
    }
}
