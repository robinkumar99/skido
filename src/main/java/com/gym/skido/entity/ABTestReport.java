package com.gym.skido.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ab_test_report")
public class ABTestReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ab_test_report_id", nullable = false)
    private long abTestReportId;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ab_test_id", nullable = false)
    private ABTest abTest;
    @NotNull
    @Column(name = "control_conversion_count", nullable = false)
    private Long controlConversionCount;

    @NotNull
    @Column(name = "challenger_conversion_count", nullable = false)
    private Long challengerConversionCount;

    @NotNull
    @Column(name = "control_group_count", nullable = false)
    private Long controlGroupCount;

    @NotNull
    @Column(name = "challenger_group_count", nullable = false)
    private Long challengerGroupCount;

    @NotNull
    @Column(name = "challenger_probability_to_win", nullable = false)
    private Double challengerProbabilityToWin1;

    @NotNull
    @Column(name = "have_winner", nullable = false)
    private Boolean haveWinner1 = false;

    @Column(name = "uplift")
    private Double uplift;

    @Column(name = "significance")
    private Double significance;

    @Column(name = "winner")
    private String winner;


    public ABTestReport() {
    }

    public ABTestReport(ABTest abTest) {
        this.abTest = abTest;
    }
}
