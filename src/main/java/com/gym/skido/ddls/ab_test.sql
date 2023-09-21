CREATE TABLE abtests.app_resource
(
    resource_id   BIGINT       NOT NULL,
    url           VARCHAR(255) NULL,
    abtest_active BIT(1)       NOT NULL,
    CONSTRAINT pk_app_resource PRIMARY KEY (resource_id)
);
create table abtests.ab_test
(
    ab_test_id          bigint       not null
        primary key,
    appid               int          not null,
    test_method         varchar(255) not null,
    hypothesis          varchar(255) not null,
    change_type         varchar(255) not null,
    sample_size         int          not null,
    sample_distribution float        not null comment 'Percentage of Experimental Group in Sample',
    is_active           bit          not null,
    is_completed        bit          not null,
    complete_count      bigint       not null,
    significance_value  double       not null,
    is_concluded        bit          not null,
    conclusion_reason   varchar(255) null,
    created_date        date         not null,
    updated_date        date         null,
    completed_date      date         null,
    app_resource_id     bigint       not null comment 'Application Resource Id on which test is performed',
    abtest_resource_id  bigint       not null comment 'Updated Resource Id',
    constraint ab_test_app_resource_fk
        foreign key (app_resource_id) references app_resource (resource_id)
);
create table abtests.ab_test_resource
(
    id                  bigint auto_increment
        primary key,
    resource_id         bigint       not null,
    app_resourceurl     varchar(255) null,
    new_resourceurl     varchar(255) null,
    parent_resourceid   bigint       not null,
    ab_test_id          bigint       not null,
    ab_test_resource_id bigint       null,
    constraint FK_AB_TEST_RESOURCE_ON_AB_TEST_RESOURCE
        foreign key (ab_test_resource_id) references ab_test (ab_test_id),
    constraint FK_AB_TEST_RESOURCE_ON_RESOURCE
        foreign key (resource_id) references app_resource (resource_id)
);
create table abtests.ab_test_user
(
    ab_test_user_id   bigint       not null
        primary key,
    ab_test_id        bigint       not null,
    reference_user_id bigint       null,
    user_properties   varchar(255) null,
    constraint FK_AB_TEST_USER_ON_AB_TEST
        foreign key (ab_test_id) references ab_test (ab_test_id)
);
create table abtests.ab_test_data
(
    ab_test_data_id   bigint not null
        primary key,
    ab_test_id        bigint null,
    ab_test_user_id   bigint null,
    converted         bit    not null,
    sample_group_type int    not null comment 'Control (0)/ Experiment (1)',
    constraint FK_AB_TEST_DATA_ON_AB_TEST
        foreign key (ab_test_id) references ab_test (ab_test_id),
    constraint FK_AB_TEST_DATA_ON_AB_TEST_USER
        foreign key (ab_test_user_id) references ab_test_user (ab_test_user_id)
);
create table abtests.ab_test_report
(
    ab_test_report_id             bigint       not null
        primary key,
    ab_test_id                    bigint       not null,
    control_conversion_count      bigint       not null,
    challenger_conversion_count   bigint       not null,
    control_group_count           bigint       not null,
    challenger_group_count        bigint       not null,
    uplift                        double       null,
    significance                  double       null,
    challenger_probability_to_win double       null,
    have_winner                   bit          null,
    winner                        varchar(255) null,
    constraint FK_AB_TEST_REPORT_ON_AB_TEST
        foreign key (ab_test_id) references abtests.ab_test (ab_test_id)
);