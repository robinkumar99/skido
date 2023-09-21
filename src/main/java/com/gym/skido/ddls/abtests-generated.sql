create table ab_test_resource
(
    resource_id         BIGINT(19)   not null,
    app_resourceurl     VARCHAR(255) null,
    new_resourceurl     VARCHAR(255) null,
    parent_resourceid   BIGINT(19)   null,
    ab_test_resource_id BIGINT(19)   not null
        primary key
);

create index FK_AB_TEST_RESOURCE_ON_RESOURCE
    on ab_test_resource (resource_id);

create table app_resource
(
    resource_id   BIGINT(19)   not null
        primary key,
    url           VARCHAR(255) null,
    abtest_active BIT          not null,
    app_id        INT(10)      null,
    ab_test_id    BIGINT(19)   null
);

create table ab_test
(
    ab_test_id          BIGINT(19)    not null
        primary key,
    appid               INT(10)       not null,
    test_method         VARCHAR(255)  not null,
    hypothesis          VARCHAR(255)  not null,
    change_type         VARCHAR(255)  not null,
    sample_size         INT(10)       not null,
    sample_distribution FLOAT(12, 0)  not null comment 'Percentage of Experimental Group in Sample',
    is_active           BIT           not null,
    is_completed        BIT           not null,
    complete_count      BIGINT(19)    not null,
    significance_value  DOUBLE(22, 0) not null,
    is_concluded        BIT           not null,
    conclusion_reason   VARCHAR(255)  null,
    created_date        DATE(10) not null,
    updated_date        DATE(10) null,
    completed_date      DATE(10) null,
    app_resource_id     BIGINT(19)    not null comment 'Application Resource Id on which test is performed',
    abtest_resource_id  BIGINT(19)    not null comment 'Updated Resource Id',
    constraint ab_test_ab_test_resource_fk
        foreign key (abtest_resource_id) references ab_test_resource (ab_test_resource_id),
    constraint ab_test_app_resource_fk
        foreign key (app_resource_id) references app_resource (resource_id)
);

create table ab_test_report
(
    ab_test_report_id             BIGINT(19)    not null
        primary key,
    ab_test_id                    BIGINT(19)    not null,
    control_conversion_count      BIGINT(19)    not null,
    challenger_conversion_count   BIGINT(19)    not null,
    control_group_count           BIGINT(19)    not null,
    challenger_group_count        BIGINT(19)    not null,
    uplift                        DOUBLE(22, 0) null,
    significance                  DOUBLE(22, 0) null,
    challenger_probability_to_win DOUBLE(22, 0) null,
    have_winner                   BIT           null,
    winner                        VARCHAR(255)  null,
    constraint FK_AB_TEST_REPORT_ON_AB_TEST
        foreign key (ab_test_id) references ab_test (ab_test_id)
);

create table ab_test_user
(
    ab_test_user_id   BIGINT(19)   not null
        primary key,
    ab_test_id        BIGINT(19)   not null,
    reference_user_id BIGINT(19)   null,
    user_properties   VARCHAR(255) null,
    constraint FK_AB_TEST_USER_ON_AB_TEST
        foreign key (ab_test_id) references ab_test (ab_test_id)
);

create table ab_test_data
(
    ab_test_data_id   BIGINT(19) not null
        primary key,
    ab_test_id        BIGINT(19) null,
    ab_test_user_id   BIGINT(19) null,
    converted         BIT        not null,
    sample_group_type INT(10)    not null comment 'Control (0)/ Experiment (1)',
    constraint FK_AB_TEST_DATA_ON_AB_TEST
        foreign key (ab_test_id) references ab_test (ab_test_id),
    constraint FK_AB_TEST_DATA_ON_AB_TEST_USER
        foreign key (ab_test_user_id) references ab_test_user (ab_test_user_id)
);

