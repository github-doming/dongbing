package com.ibm.follow.servlet.cloud.ibm_periods_select.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_periods_select
 * IBM_查询期数实体类
 * @author lxl
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_periods_select")
public class IbmPeriodsSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询期数主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IBM_PERIODS_ID_")
    private String ibmPeriodsId;

    /**
     * 基准期数
     */
    @Column(name="BENCHMARK_PERIODS")
    private Long benchmarkPeriods;

    /**
     * 基准时间
     */
    @Column(name="TIME_PERIODS")
    private Long timePeriods;

    /**
     * 总间隔天数
     */
    @Column(name="BETWEEN_DAYS")
    private Long betweenDays;

    /**
     * 间隔时间一
     */
    @Column(name="BETWEEN_TIME_ONE")
    private Long betweenTimeOne;

    /**
     * 间隔时间二
     */
    @Column(name="BETWEEN_TIME_TWO")
    private Long betweenTimeTwo;

    /**
     * 更新时间
     */
    @Temporal( TemporalType.TIMESTAMP)
    @Column(name="UPDATE_TIME_")
    private Date updateTime;

    /**
     * 更新时间Long
     */
    @Column(name="UPDATE_TIME_LONG_")
    private Long updateTimeLong;

    /**
     * 状态
     */
    @Column(name="STATE_")
    private String state;

    /**
     * 描述
     */
    @Column(name="DESC_")
    private String desc;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIbmPeriodsId() {
        return ibmPeriodsId;
    }

    public IbmPeriodsSelect setIbmPeriodsId(String ibmPeriodsId) {
        if (ibmPeriodsId != null) {
            this.ibmPeriodsId = ibmPeriodsId;
        }
        return this;
    }

    public Long getBenchmarkPeriods() {
        return benchmarkPeriods;
    }

    public IbmPeriodsSelect setBenchmarkPeriods(Long benchmarkPeriods) {
        if (benchmarkPeriods != null) {
            this.benchmarkPeriods = benchmarkPeriods;
        }
        return this;
    }

    public Long getTimePeriods() {
        return timePeriods;
    }

    public IbmPeriodsSelect setTimePeriods(Long timePeriods) {
        if (timePeriods != null) {
            this.timePeriods = timePeriods;
        }
        return this;
    }

    public Long getBetweenDays() {
        return betweenDays;
    }

    public IbmPeriodsSelect setBetweenDays(Long betweenDays) {
        if (betweenDays != null) {
            this.betweenDays = betweenDays;
        }
        return this;
    }

    public Long getBetweenTimeOne() {
        return betweenTimeOne;
    }

    public IbmPeriodsSelect setBetweenTimeOne(Long betweenTimeOne) {
        if (betweenTimeOne != null) {
            this.betweenTimeOne = betweenTimeOne;
        }
        return this;
    }

    public Long getBetweenTimeTwo() {
        return betweenTimeTwo;
    }

    public IbmPeriodsSelect setBetweenTimeTwo(Long betweenTimeTwo) {
        if (betweenTimeTwo != null) {
            this.betweenTimeTwo = betweenTimeTwo;
        }
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public IbmPeriodsSelect setUpdateTime(Date updateTime) {
        if (updateTime != null) {
            this.updateTime = updateTime;
        }
        return this;
    }

    public Long getUpdateTimeLong() {
        return updateTimeLong;
    }

    public IbmPeriodsSelect setUpdateTimeLong(Long updateTimeLong) {
        if (updateTimeLong != null) {
            this.updateTimeLong = updateTimeLong;
        }
        return this;
    }

    public String getState() {
        return state;
    }

    public IbmPeriodsSelect setState(String state) {
        if (state != null) {
            this.state = state;
        }
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public IbmPeriodsSelect setDesc(String desc) {
        if (desc != null) {
            this.desc = desc;
        }
        return this;
    }

    private String tableNameMy;
    @Transient
    public String getTableNameMy() {return tableNameMy;}
    public void setTableNameMy(String tableNameMy) {this.tableNameMy = tableNameMy;}

}
