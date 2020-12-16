package com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibsp_hm_mode_cutover 
 * IBSP_模式智能切换 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibsp_hm_mode_cutover")
public class IbspHmModeCutover implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBSP_模式智能切换主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBSP_HM_MODE_CUTOVER_ID_")
	private String ibspHmModeCutoverId;
	public void setIbspHmModeCutoverId(String ibspHmModeCutoverId) {
		this.ibspHmModeCutoverId=ibspHmModeCutoverId;
	}
	public void setIbspHmModeCutoverId(Object ibspHmModeCutoverId) {
		if (ibspHmModeCutoverId != null) {
			this.ibspHmModeCutoverId = ibspHmModeCutoverId.toString();
		}
	}
	public String getIbspHmModeCutoverId() {
		return this.ibspHmModeCutoverId ;
	}

	/**
	 * 盘口会员主键
	 */
	@Column(name="HANDICAP_MEMBER_ID_")
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public void setHandicapMemberId(Object handicapMemberId) {
		if (handicapMemberId != null) {
			this.handicapMemberId = handicapMemberId.toString();
		}
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}

	/**
	 * 切换组数据
	 */
	@Column(name="CUTOVER_GROUP_DATA_")
	private String cutoverGroupData;
	public void setCutoverGroupData(String cutoverGroupData) {
		this.cutoverGroupData=cutoverGroupData;
	}
	public void setCutoverGroupData(Object cutoverGroupData) {
		if (cutoverGroupData != null) {
			this.cutoverGroupData = cutoverGroupData.toString();
		}
	}
	public String getCutoverGroupData() {
		return this.cutoverGroupData ;
	}

	/**
	 * 切换组key
	 */
	@Column(name="CUTOVER_KEY_")
	private String cutoverKey;
	public void setCutoverKey(String cutoverKey) {
		this.cutoverKey=cutoverKey;
	}
	public void setCutoverKey(Object cutoverKey) {
		if (cutoverKey != null) {
			this.cutoverKey = cutoverKey.toString();
		}
	}
	public String getCutoverKey() {
		return this.cutoverKey ;
	}

	/**
	 * 当前切换组
	 */
	@Column(name="CURRENT_INDEX_")
	private Integer currentIndex;
	public void setCurrentIndex(Integer currentIndex) {
		this.currentIndex=currentIndex;
	}
	public void setCurrentIndex(Object currentIndex) {
		if (currentIndex != null) {
			if (currentIndex instanceof Integer) {
				this.currentIndex= (Integer) currentIndex;
			}else if (StringTool.isInteger(currentIndex.toString())) {
				this.currentIndex = Integer.parseInt(currentIndex.toString());
			}
		}
	}
	public Integer getCurrentIndex() {
		return this.currentIndex ;
	}

	/**
	 * 盈亏总额
	 */
	@Column(name="PROFIT_T_")
	private Long profitT;
	public void setProfitT(Long profitT) {
		this.profitT=profitT;
	}
	public void setProfitT(Object profitT) {
		if (profitT != null) {
			if (profitT instanceof Long) {
				this.profitT= (Long) profitT;
			}else if (StringTool.isInteger(profitT.toString())) {
				this.profitT = Long.parseLong(profitT.toString());
			}
		}
	}
	public Long getProfitT() {
		return this.profitT ;
	}

	/**
	 * 当前模式
	 */
	@Column(name="CURRENT_")
	private String current;
	public void setCurrent(String current) {
		this.current=current;
	}
	public void setCurrent(Object current) {
		if (current != null) {
			this.current = current.toString();
		}
	}
	public String getCurrent() {
		return this.current ;
	}

	/**
	 * 切换盈利金额
	 */
	@Column(name="CUTOVER_PROFIT_T_")
	private Long cutoverProfitT;
	public void setCutoverProfitT(Long cutoverProfitT) {
		this.cutoverProfitT=cutoverProfitT;
	}
	public void setCutoverProfitT(Object cutoverProfitT) {
		if (cutoverProfitT != null) {
			if (cutoverProfitT instanceof Long) {
				this.cutoverProfitT= (Long) cutoverProfitT;
			}else if (StringTool.isInteger(cutoverProfitT.toString())) {
				this.cutoverProfitT = Long.parseLong(cutoverProfitT.toString());
			}
		}
	}
	public Long getCutoverProfitT() {
		return this.cutoverProfitT ;
	}

	/**
	 * 切换亏损金额
	 */
	@Column(name="CUTOVER_LOSS_T_")
	private Long cutoverLossT;
	public void setCutoverLossT(Long cutoverLossT) {
		this.cutoverLossT=cutoverLossT;
	}
	public void setCutoverLossT(Object cutoverLossT) {
		if (cutoverLossT != null) {
			if (cutoverLossT instanceof Long) {
				this.cutoverLossT= (Long) cutoverLossT;
			}else if (StringTool.isInteger(cutoverLossT.toString())) {
				this.cutoverLossT = Long.parseLong(cutoverLossT.toString());
			}
		}
	}
	public Long getCutoverLossT() {
		return this.cutoverLossT ;
	}

	/**
	 * 切换模式
	 */
	@Column(name="CUTOVER_")
	private String cutover;
	public void setCutover(String cutover) {
		this.cutover=cutover;
	}
	public void setCutover(Object cutover) {
		if (cutover != null) {
			this.cutover = cutover.toString();
		}
	}
	public String getCutover() {
		return this.cutover ;
	}

	/**
	 * 重置资金
	 */
	@Column(name="RESET_PROFIT_")
	private String resetProfit;
	public void setResetProfit(String resetProfit) {
		this.resetProfit=resetProfit;
	}
	public void setResetProfit(Object resetProfit) {
		if (resetProfit != null) {
			this.resetProfit = resetProfit.toString();
		}
	}
	public String getResetProfit() {
		return this.resetProfit ;
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
	public void setCreateTime(Object createTime) throws ParseException {
		if (createTime != null) {
			if (createTime instanceof Date) {
				this.createTime= (Date) createTime;
			}else if (StringTool.isInteger(createTime.toString())) {
				this.createTime = new Date(Long.parseLong(createTime.toString()));
			}else {
				this.createTime = DateTool.getTime(createTime.toString());
			}
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
	public void setUpdateTime(Object updateTime) throws ParseException {
		if (updateTime != null) {
			if (updateTime instanceof Date) {
				this.updateTime= (Date) updateTime;
			}else if (StringTool.isInteger(updateTime.toString())) {
				this.updateTime = new Date(Long.parseLong(updateTime.toString()));
			}else {
				this.updateTime = DateTool.getTime(updateTime.toString());
			}
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