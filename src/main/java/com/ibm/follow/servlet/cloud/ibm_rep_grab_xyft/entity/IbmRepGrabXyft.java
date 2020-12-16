package com.ibm.follow.servlet.cloud.ibm_rep_grab_xyft.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_rep_grab_xyft 
 * 幸运飞艇XYFT_抓取数据IBM_REP_GRAB_XYFT的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_rep_grab_xyft")
public class IbmRepGrabXyft implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 幸运飞艇XYFT_抓取数据主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_REP_GRAB_XYFT_ID_")
	private String ibmRepGrabXyftId;
	public void setIbmRepGrabXyftId(String ibmRepGrabXyftId) {
		this.ibmRepGrabXyftId=ibmRepGrabXyftId;
	}
	public void setIbmRepGrabXyftId(Object ibmRepGrabXyftId) {
		if (ibmRepGrabXyftId != null) {
			this.ibmRepGrabXyftId = ibmRepGrabXyftId.toString();
		}else{
			this.ibmRepGrabXyftId = null;
		}
	}
	public String getIbmRepGrabXyftId() {
		return this.ibmRepGrabXyftId ;
	}

	/**
	 * 幸运飞艇XYFT_结果数据
	 */
	@Column(name="REP_DRAW_XYFT_ID_")
	private String repDrawXyftId;
	public void setRepDrawXyftId(String repDrawXyftId) {
		this.repDrawXyftId=repDrawXyftId;
	}
	public void setRepDrawXyftId(Object repDrawXyftId) {
		if (repDrawXyftId != null) {
			this.repDrawXyftId = repDrawXyftId.toString();
		}else{
			this.repDrawXyftId = null;
		}
	}
	public String getRepDrawXyftId() {
		return this.repDrawXyftId ;
	}

    /**
     * 盘口编码
     */
    @Column(name="HANDICAP_CODE_")
    private String handicapCode;
    public void setHandicapCode(String handicapCode) {
        this.handicapCode=handicapCode;
    }
    public void setHandicapCode(Object handicapCode) {
        if (handicapCode != null) {
            this.handicapCode = handicapCode.toString();
        }else{
            this.handicapCode = null;
        }
    }
    public String getHandicapCode() {
        return this.handicapCode ;
    }

	/**
	 * 游戏主键
	 */
	@Column(name="GAME_ID_")
	private String gameId;
	public void setGameId(String gameId) {
		this.gameId=gameId;
	}
	public void setGameId(Object gameId) {
		if (gameId != null) {
			this.gameId = gameId.toString();
		}else{
			this.gameId = null;
		}
	}
	public String getGameId() {
		return this.gameId ;
	}

    /**
     * 类型
     */
    @Column(name="TYPE_")
    private String type;
    public void setType(String type) {
        this.type=type;
    }
    public void setType(Object type) {
        if (type != null) {
            this.type = type.toString();
        }else{
            this.type = null;
        }
    }
    public String getType() {
        return this.type ;
    }

	/**
	 * 期数
	 */
	@Column(name="PERIOD_")
	private String period;
	public void setPeriod(String period) {
		this.period=period;
	}
	public void setPeriod(Object period) {
		if (period != null) {
			this.period = period.toString();
		}else{
			this.period = null;
		}
	}
	public String getPeriod() {
		return this.period ;
	}

	/**
	 * 开奖时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="DRAW_TIME_")
	private Date drawTime;
	public void setDrawTime(Date drawTime) {
		this.drawTime=drawTime;
	}
	public void setDrawTime(Object drawTime) {
		if (drawTime != null) {
			if (drawTime instanceof Date) {
				this.drawTime= (Date) drawTime;
			}else if (StringTool.isInteger(drawTime.toString())) {
				this.drawTime = new Date(Long.parseLong(drawTime.toString()));
			}else {
				this.drawTime = DateTool.getTime(drawTime.toString());
			}
		}else{
			this.drawTime = null;
		}
	}
	public Date getDrawTime() {
		return this.drawTime ;
	}

	/**
	 * 开奖时间数字型
	 */
	@Column(name="DRAW_TIME_LONG_")
	private Long drawTimeLong;
	public void setDrawTimeLong(Long drawTimeLong) {
		this.drawTimeLong=drawTimeLong;
	}
	public void setDrawTimeLong(Object drawTimeLong) {
		if (drawTimeLong != null) {
			if (drawTimeLong instanceof Long) {
				this.drawTimeLong= (Long) drawTimeLong;
			}else if (StringTool.isInteger(drawTimeLong.toString())) {
				this.drawTimeLong = Long.parseLong(drawTimeLong.toString());
			}
		}else{
			this.drawTimeLong = null;
		}
	}
	public Long getDrawTimeLong() {
		return this.drawTimeLong ;
	}

	/**
	 * 开奖号码
	 */
	@Column(name="DRAW_NUMBER_")
	private String drawNumber;
	public void setDrawNumber(String drawNumber) {
		this.drawNumber=drawNumber;
	}
	public void setDrawNumber(Object drawNumber) {
		if (drawNumber != null) {
			this.drawNumber = drawNumber.toString();
		}else{
			this.drawNumber = null;
		}
	}
	public String getDrawNumber() {
		return this.drawNumber ;
	}

	/**
	 * 第1名号码
	 */
	@Column(name="P1_NUMBER_")
	private String p1Number;
	public void setP1Number(String p1Number) {
		this.p1Number=p1Number;
	}
	public void setP1Number(Object p1Number) {
		if (p1Number != null) {
			this.p1Number = p1Number.toString();
		}else{
			this.p1Number = null;
		}
	}
	public String getP1Number() {
		return this.p1Number ;
	}

	/**
	 * 第2名号码
	 */
	@Column(name="P2_NUMBER_")
	private String p2Number;
	public void setP2Number(String p2Number) {
		this.p2Number=p2Number;
	}
	public void setP2Number(Object p2Number) {
		if (p2Number != null) {
			this.p2Number = p2Number.toString();
		}else{
			this.p2Number = null;
		}
	}
	public String getP2Number() {
		return this.p2Number ;
	}

	/**
	 * 第3名号码
	 */
	@Column(name="P3_NUMBER_")
	private String p3Number;
	public void setP3Number(String p3Number) {
		this.p3Number=p3Number;
	}
	public void setP3Number(Object p3Number) {
		if (p3Number != null) {
			this.p3Number = p3Number.toString();
		}else{
			this.p3Number = null;
		}
	}
	public String getP3Number() {
		return this.p3Number ;
	}

	/**
	 * 第4名号码
	 */
	@Column(name="P4_NUMBER_")
	private String p4Number;
	public void setP4Number(String p4Number) {
		this.p4Number=p4Number;
	}
	public void setP4Number(Object p4Number) {
		if (p4Number != null) {
			this.p4Number = p4Number.toString();
		}else{
			this.p4Number = null;
		}
	}
	public String getP4Number() {
		return this.p4Number ;
	}

	/**
	 * 第5名号码
	 */
	@Column(name="P5_NUMBER_")
	private String p5Number;
	public void setP5Number(String p5Number) {
		this.p5Number=p5Number;
	}
	public void setP5Number(Object p5Number) {
		if (p5Number != null) {
			this.p5Number = p5Number.toString();
		}else{
			this.p5Number = null;
		}
	}
	public String getP5Number() {
		return this.p5Number ;
	}

	/**
	 * 第6名号码
	 */
	@Column(name="P6_NUMBER_")
	private String p6Number;
	public void setP6Number(String p6Number) {
		this.p6Number=p6Number;
	}
	public void setP6Number(Object p6Number) {
		if (p6Number != null) {
			this.p6Number = p6Number.toString();
		}else{
			this.p6Number = null;
		}
	}
	public String getP6Number() {
		return this.p6Number ;
	}

	/**
	 * 第7名号码
	 */
	@Column(name="P7_NUMBER_")
	private String p7Number;
	public void setP7Number(String p7Number) {
		this.p7Number=p7Number;
	}
	public void setP7Number(Object p7Number) {
		if (p7Number != null) {
			this.p7Number = p7Number.toString();
		}else{
			this.p7Number = null;
		}
	}
	public String getP7Number() {
		return this.p7Number ;
	}

	/**
	 * 第8名号码
	 */
	@Column(name="P8_NUMBER_")
	private String p8Number;
	public void setP8Number(String p8Number) {
		this.p8Number=p8Number;
	}
	public void setP8Number(Object p8Number) {
		if (p8Number != null) {
			this.p8Number = p8Number.toString();
		}else{
			this.p8Number = null;
		}
	}
	public String getP8Number() {
		return this.p8Number ;
	}

	/**
	 * 第9名号码
	 */
	@Column(name="P9_NUMBER_")
	private String p9Number;
	public void setP9Number(String p9Number) {
		this.p9Number=p9Number;
	}
	public void setP9Number(Object p9Number) {
		if (p9Number != null) {
			this.p9Number = p9Number.toString();
		}else{
			this.p9Number = null;
		}
	}
	public String getP9Number() {
		return this.p9Number ;
	}

	/**
	 * 第10名号码
	 */
	@Column(name="P10_NUMBER_")
	private String p10Number;
	public void setP10Number(String p10Number) {
		this.p10Number=p10Number;
	}
	public void setP10Number(Object p10Number) {
		if (p10Number != null) {
			this.p10Number = p10Number.toString();
		}else{
			this.p10Number = null;
		}
	}
	public String getP10Number() {
		return this.p10Number ;
	}

	/**
	 * 创建时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME_")
	private Date createTime;
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public void setCreateTime(Object createTime) {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
		}else{
			this.createTime = null;
		}
	}
	public Date getCreateTime() {
		return this.createTime ;
	}

	/**
	 * 创建时间
	 */
	@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public void setCreateTimeLong(Object createTimeLong) {
		if (createTimeLong != null) {
			if (createTimeLong instanceof Long) {
				this.createTimeLong= (Long) createTimeLong;
			}else if (StringTool.isInteger(createTimeLong.toString())) {
				this.createTimeLong = Long.parseLong(createTimeLong.toString());
			}
		}else{
			this.createTimeLong = null;
		}
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}

	/**
	 * 更新时间
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="UPDATE_TIME_")
	private Date updateTime;
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public void setUpdateTime(Object updateTime) {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
		}else{
			this.updateTime = null;
		}
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}

	/**
	 * 更新时间
	 */
	@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public void setUpdateTimeLong(Object updateTimeLong) {
		if (updateTimeLong != null) {
			if (updateTimeLong instanceof Long) {
				this.updateTimeLong= (Long) updateTimeLong;
			}else if (StringTool.isInteger(updateTimeLong.toString())) {
				this.updateTimeLong = Long.parseLong(updateTimeLong.toString());
			}
		}else{
			this.updateTimeLong = null;
		}
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}

	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	@Column(name="STATE_")
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public void setState(Object state) {
		if (state != null) {
			this.state = state.toString();
		}else{
			this.state = null;
		}
	}
	public String getState() {
		return this.state ;
	}

	/**
	 * 描述
	 */
	@Column(name="DESC_")
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public void setDesc(Object desc) {
		if (desc != null) {
			this.desc = desc.toString();
		}else{
			this.desc = null;
		}
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/**
	 * 不映射
	 * @return 表名
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}