package com.gym.skido.context;

public class ABTestQueries {
    protected static final String QueryForTestReportOne =
            "Select " +
                    " SUM(case when (data.sample_group_type=0) then 1 else 0)  as control_samples," +
                    " SUM(data.sample_group_type) AS challenger_samples," +
                    " SUM(case when (data.sample_group_type=0 and converted)" +
                    " then 1 else 0 end) as control_conversions" +
                    " SUM(case when (data.sample_group_type=1 and converted)" +
                    " then 1 else 0 end) as challenger_conversions," +
                    " from ab_test_data data," +
                    " ab_test_user user," +
                    " ab_test test" +
                    " where data.ab_test_id = test.ab_test_id" +
                    " and data.ab_test_user_id = user.ab_test_user_id" +
                    " and data.ab_test_id =";
    protected static final String referenceUserQuery =
            "Select ab_test_user_id from ab_test_user where reference_user_id = ";
    protected static final String totalSampleCount =
            "Select Sum(sample_group_type) from ab_test_data where ab_test_id = ";
    protected static final String nextIdForTable =
            "select Auto_increment from information_schema.tables where table_name='";
}
