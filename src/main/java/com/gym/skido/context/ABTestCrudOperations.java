package com.gym.skido.context;

import com.gym.skido.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.sql.Date;


/**
 * JPA CRUD Operations
 *
 * @author Ramesh Fadatare
 */
public class ABTestCrudOperations {
    public void insertTesData(ABTestData data) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(data);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Long getNextID(String tableName) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Long testDataId = Long.parseLong(entityManager
                .createQuery(ABTestQueries.nextIdForTable + tableName + "'")
                .getSingleResult().toString());
        entityManager.getTransaction().commit();
        entityManager.close();
        return testDataId;
    }

    public ABTestData findTestData(long id) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        ABTestData test = entityManager.find(ABTestData.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return test;
    }

    public ABTest findTest(long id) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        ABTest test = entityManager.find(ABTest.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return test;
    }

    public void updateTesData(ABTestData testData) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        ABTestData data = entityManager.find(ABTestData.class, testData.getId());
        // The entity object is physically updated in the database when the transaction
        // is committed
        data.setConverted(true);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void removeTestData(long id) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        ABTestData student = entityManager.find(ABTestData.class, id);
        entityManager.remove(student);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public long getCountofExperimentalData(ABTest abtestCurrent) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        long count = (long) entityManager.createQuery(ABTestQueries.totalSampleCount + abtestCurrent.getTestId()).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return count;
    }

    public AppResource getAppResource(String resourceId) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        AppResource appResource = entityManager.find(AppResource.class, Long.parseLong(resourceId));
        entityManager.getTransaction().commit();
        entityManager.close();
        return appResource;
    }

    public void updateTest(ABTest abtestCurrent) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        ABTest test = entityManager.find(ABTest.class, abtestCurrent.getTestId());
        if (!abtestCurrent.isActive()) {
            test.setActive(false);
            test.setCompleted(true);
            test.setCompletedDate(new Date(System.currentTimeMillis()));
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateABTestUser(ABTestUser currentUser) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        ABTestUser user = entityManager.find(ABTestUser.class, currentUser.getId());
        user.setReferenceUserId(currentUser.getReferenceUserId());
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public ABTestUser getUserByReferenceId(ABTestUser currentUser) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        String testUserId = entityManager.createQuery(ABTestQueries.referenceUserQuery + currentUser.getReferenceUserId(), String.class).getSingleResult();
        if (testUserId != null && !testUserId.isEmpty()) {
            currentUser.setId(Long.parseLong(testUserId));
        } else {
            entityManager.persist(currentUser);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return currentUser;
    }

    public ABTestReport calculateMetricsForABTest(ABTest abTest) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(ABTestQueries.QueryForTestReportOne + abTest.getTestId() + " group by data.ab_test_id");
        Object[] calculations = (Object[]) query.getResultList().get(0);
        ABTestReport report = new ABTestReport(abTest);
        report.setControlGroupCount((Long) calculations[0]);
        report.setChallengerGroupCount((Long) calculations[1]);
        report.setControlConversionCount((Long) calculations[2]);
        report.setChallengerConversionCount((Long) calculations[3]);
        entityManager.getTransaction().commit();
        entityManager.close();
        return report;
    }

    public AppResource findAppResource(Long resourceId) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        AppResource appResource = entityManager.find(AppResource.class, resourceId);
        entityManager.getTransaction().commit();
        entityManager.close();
        return  appResource;
    }

    public void insertABTest(ABTest abtest) {
        EntityManager entityManager = ABTestJPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        abtest.setTestId(getNextID("ab_test"));
        entityManager.persist(abtest);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}