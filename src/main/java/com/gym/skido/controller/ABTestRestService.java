package com.gym.skido.controller;

import com.gym.skido.api.ABTestService;
import com.gym.skido.api.ABTestServiceImpl;
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

    @RequestMapping(value = "/resource/{id}", method = RequestMethod.GET)
    public ResponseEntity<AppResourceBean> createABTestData(@PathVariable("id") String id, @RequestParam String userId) {
        logger.info("Creating Data for an A/B test before loading resource " + id);
        return new ResponseEntity<AppResourceBean>(abTestService.createABTestData(id, userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/resource/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateABTestData(@PathVariable("id") String id, @RequestBody AppResourceBean abTestData) {
        logger.info("Capturing conversion for " + id);
        return new ResponseEntity<Boolean>(abTestService.updateABTestData(abTestData), HttpStatus.OK);
    }

    @RequestMapping(value = "/resource/report/{testId}", method = RequestMethod.GET)
    public ResponseEntity<ABTestReport> getABTestReport(@PathVariable("testId") String testId) {
        logger.info("Creating A/B test calculations for " + testId);
        return new ResponseEntity<ABTestReport>(abTestService.getABTestReport(Long.parseLong(testId)), HttpStatus.OK);
    }
}
