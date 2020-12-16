package com.ibm.old.v1.cloud.ibm_rep_draw_xyft.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_rep_draw_xyft 
 * 幸运飞艇XYFT_结果数据IBM_REP_DRAW_XYFT的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_rep_draw_xyft")
public class IbmRepDrawXyftT implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 索引
	 */
	@Column(name="IDX_")
	private Long idx;
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public void setIdx(Object idx) {
		if (idx != null) {
			if (idx instanceof Long) {
				this.idx= (Long) idx;
			}else if (StringTool.isInteger(idx.toString())) {
				this.idx = Long.parseLong(idx.toString());
			}
		}
	}
	public Long getIdx() {
		return this.idx ;
	}

	/**
	 * 幸运飞艇XYFT_结果数据主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_REP_DRAW_XYFT_ID_")
	private String ibmRepDrawXyftId;
	public void setIbmRepDrawXyftId(String ibmRepDrawXyftId) {
		this.ibmRepDrawXyftId=ibmRepDrawXyftId;
	}
	public void setIbmRepDrawXyftId(Object ibmRepDrawXyftId) {
		if (ibmRepDrawXyftId != null) {
			this.ibmRepDrawXyftId = ibmRepDrawXyftId.toString();
		}
	}
	public String getIbmRepDrawXyftId() {
		return this.ibmRepDrawXyftId ;
	}

	/**
	 * 幸运飞艇XYFT_抓取数据
	 */
	@Column(name="REP_GRAB_XYFT_ID_")
	private String repGrabXyftId;
	public void setRepGrabXyftId(String repGrabXyftId) {
		this.repGrabXyftId=repGrabXyftId;
	}
	public void setRepGrabXyftId(Object repGrabXyftId) {
		if (repGrabXyftId != null) {
			this.repGrabXyftId = repGrabXyftId.toString();
		}
	}
	public String getRepGrabXyftId() {
		return this.repGrabXyftId ;
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
		}
	}
	public String getGameId() {
		return this.gameId ;
	}

	/**
	 * 期数PERIOD_
	 */
	@Column(name="PERIOD_")
	private String period;
	public void setPeriod(String period) {
		this.period=period;
	}
	public void setPeriod(Object period) {
		if (period != null) {
			this.period = period.toString();
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
	public void setDrawTime(Object drawTime)  {
		if (drawTime != null) {
			if (drawTime instanceof Date) {
				this.drawTime= (Date) drawTime;
			}else if (StringTool.isInteger(drawTime.toString())) {
				this.drawTime = new Date(Long.parseLong(drawTime.toString()));
			}else {
				this.drawTime = DateTool.getTime(drawTime.toString());
			}
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
		}
	}
	public String getP10Number() {
		return this.p10Number ;
	}

	/**
	 * 冠亚和号码
	 */
	@Column(name="CHAMPION_SUM_NUNMBER_")
	private String championSumNunmber;
	public void setChampionSumNunmber(String championSumNunmber) {
		this.championSumNunmber=championSumNunmber;
	}
	public void setChampionSumNunmber(Object championSumNunmber) {
		if (championSumNunmber != null) {
			this.championSumNunmber = championSumNunmber.toString();
		}
	}
	public String getChampionSumNunmber() {
		return this.championSumNunmber ;
	}

	/**
	 * 第1名号码_英文
	 */
	@Column(name="P1_NUMBER_EN_")
	private String p1NumberEn;
	public void setP1NumberEn(String p1NumberEn) {
		this.p1NumberEn=p1NumberEn;
	}
	public void setP1NumberEn(Object p1NumberEn) {
		if (p1NumberEn != null) {
			this.p1NumberEn = p1NumberEn.toString();
		}
	}
	public String getP1NumberEn() {
		return this.p1NumberEn ;
	}

	/**
	 * 第2名号码_英文
	 */
	@Column(name="P2_NUMBER_EN_")
	private String p2NumberEn;
	public void setP2NumberEn(String p2NumberEn) {
		this.p2NumberEn=p2NumberEn;
	}
	public void setP2NumberEn(Object p2NumberEn) {
		if (p2NumberEn != null) {
			this.p2NumberEn = p2NumberEn.toString();
		}
	}
	public String getP2NumberEn() {
		return this.p2NumberEn ;
	}

	/**
	 * 第3名号码_英文
	 */
	@Column(name="P3_NUMBER_EN_")
	private String p3NumberEn;
	public void setP3NumberEn(String p3NumberEn) {
		this.p3NumberEn=p3NumberEn;
	}
	public void setP3NumberEn(Object p3NumberEn) {
		if (p3NumberEn != null) {
			this.p3NumberEn = p3NumberEn.toString();
		}
	}
	public String getP3NumberEn() {
		return this.p3NumberEn ;
	}

	/**
	 * 第4名号码_英文
	 */
	@Column(name="P4_NUMBER_EN_")
	private String p4NumberEn;
	public void setP4NumberEn(String p4NumberEn) {
		this.p4NumberEn=p4NumberEn;
	}
	public void setP4NumberEn(Object p4NumberEn) {
		if (p4NumberEn != null) {
			this.p4NumberEn = p4NumberEn.toString();
		}
	}
	public String getP4NumberEn() {
		return this.p4NumberEn ;
	}

	/**
	 * 第5名号码_英文
	 */
	@Column(name="P5_NUMBER_EN_")
	private String p5NumberEn;
	public void setP5NumberEn(String p5NumberEn) {
		this.p5NumberEn=p5NumberEn;
	}
	public void setP5NumberEn(Object p5NumberEn) {
		if (p5NumberEn != null) {
			this.p5NumberEn = p5NumberEn.toString();
		}
	}
	public String getP5NumberEn() {
		return this.p5NumberEn ;
	}

	/**
	 * 第6名号码_英文
	 */
	@Column(name="P6_NUMBER_EN_")
	private String p6NumberEn;
	public void setP6NumberEn(String p6NumberEn) {
		this.p6NumberEn=p6NumberEn;
	}
	public void setP6NumberEn(Object p6NumberEn) {
		if (p6NumberEn != null) {
			this.p6NumberEn = p6NumberEn.toString();
		}
	}
	public String getP6NumberEn() {
		return this.p6NumberEn ;
	}

	/**
	 * 第7名号码_英文
	 */
	@Column(name="P7_NUMBER_EN_")
	private String p7NumberEn;
	public void setP7NumberEn(String p7NumberEn) {
		this.p7NumberEn=p7NumberEn;
	}
	public void setP7NumberEn(Object p7NumberEn) {
		if (p7NumberEn != null) {
			this.p7NumberEn = p7NumberEn.toString();
		}
	}
	public String getP7NumberEn() {
		return this.p7NumberEn ;
	}

	/**
	 * 第8名号码_英文
	 */
	@Column(name="P8_NUMBER_EN_")
	private String p8NumberEn;
	public void setP8NumberEn(String p8NumberEn) {
		this.p8NumberEn=p8NumberEn;
	}
	public void setP8NumberEn(Object p8NumberEn) {
		if (p8NumberEn != null) {
			this.p8NumberEn = p8NumberEn.toString();
		}
	}
	public String getP8NumberEn() {
		return this.p8NumberEn ;
	}

	/**
	 * 第9名号码_英文
	 */
	@Column(name="P9_NUMBER_EN_")
	private String p9NumberEn;
	public void setP9NumberEn(String p9NumberEn) {
		this.p9NumberEn=p9NumberEn;
	}
	public void setP9NumberEn(Object p9NumberEn) {
		if (p9NumberEn != null) {
			this.p9NumberEn = p9NumberEn.toString();
		}
	}
	public String getP9NumberEn() {
		return this.p9NumberEn ;
	}

	/**
	 * 第10名号码_英文
	 */
	@Column(name="P10_NUMBER_EN_")
	private String p10NumberEn;
	public void setP10NumberEn(String p10NumberEn) {
		this.p10NumberEn=p10NumberEn;
	}
	public void setP10NumberEn(Object p10NumberEn) {
		if (p10NumberEn != null) {
			this.p10NumberEn = p10NumberEn.toString();
		}
	}
	public String getP10NumberEn() {
		return this.p10NumberEn ;
	}

	/**
	 * 冠亚和号码_英文
	 */
	@Column(name="CHAMPION_SUM_NUNMBER_EN_")
	private String championSumNunmberEn;
	public void setChampionSumNunmberEn(String championSumNunmberEn) {
		this.championSumNunmberEn=championSumNunmberEn;
	}
	public void setChampionSumNunmberEn(Object championSumNunmberEn) {
		if (championSumNunmberEn != null) {
			this.championSumNunmberEn = championSumNunmberEn.toString();
		}
	}
	public String getChampionSumNunmberEn() {
		return this.championSumNunmberEn ;
	}

	/**
	 * 第1名大小
	 */
	@Column(name="P1_SIZE_")
	private String p1Size;
	public void setP1Size(String p1Size) {
		this.p1Size=p1Size;
	}
	public void setP1Size(Object p1Size) {
		if (p1Size != null) {
			this.p1Size = p1Size.toString();
		}
	}
	public String getP1Size() {
		return this.p1Size ;
	}

	/**
	 * 第2名大小
	 */
	@Column(name="P2_SIZE_")
	private String p2Size;
	public void setP2Size(String p2Size) {
		this.p2Size=p2Size;
	}
	public void setP2Size(Object p2Size) {
		if (p2Size != null) {
			this.p2Size = p2Size.toString();
		}
	}
	public String getP2Size() {
		return this.p2Size ;
	}

	/**
	 * 第3名大小
	 */
	@Column(name="P3_SIZE_")
	private String p3Size;
	public void setP3Size(String p3Size) {
		this.p3Size=p3Size;
	}
	public void setP3Size(Object p3Size) {
		if (p3Size != null) {
			this.p3Size = p3Size.toString();
		}
	}
	public String getP3Size() {
		return this.p3Size ;
	}

	/**
	 * 第4名大小
	 */
	@Column(name="P4_SIZE_")
	private String p4Size;
	public void setP4Size(String p4Size) {
		this.p4Size=p4Size;
	}
	public void setP4Size(Object p4Size) {
		if (p4Size != null) {
			this.p4Size = p4Size.toString();
		}
	}
	public String getP4Size() {
		return this.p4Size ;
	}

	/**
	 * 第5名大小
	 */
	@Column(name="P5_SIZE_")
	private String p5Size;
	public void setP5Size(String p5Size) {
		this.p5Size=p5Size;
	}
	public void setP5Size(Object p5Size) {
		if (p5Size != null) {
			this.p5Size = p5Size.toString();
		}
	}
	public String getP5Size() {
		return this.p5Size ;
	}

	/**
	 * 第6名大小
	 */
	@Column(name="P6_SIZE_")
	private String p6Size;
	public void setP6Size(String p6Size) {
		this.p6Size=p6Size;
	}
	public void setP6Size(Object p6Size) {
		if (p6Size != null) {
			this.p6Size = p6Size.toString();
		}
	}
	public String getP6Size() {
		return this.p6Size ;
	}

	/**
	 * 第7名大小
	 */
	@Column(name="P7_SIZE_")
	private String p7Size;
	public void setP7Size(String p7Size) {
		this.p7Size=p7Size;
	}
	public void setP7Size(Object p7Size) {
		if (p7Size != null) {
			this.p7Size = p7Size.toString();
		}
	}
	public String getP7Size() {
		return this.p7Size ;
	}

	/**
	 * 第8名大小
	 */
	@Column(name="P8_SIZE_")
	private String p8Size;
	public void setP8Size(String p8Size) {
		this.p8Size=p8Size;
	}
	public void setP8Size(Object p8Size) {
		if (p8Size != null) {
			this.p8Size = p8Size.toString();
		}
	}
	public String getP8Size() {
		return this.p8Size ;
	}

	/**
	 * 第9名大小
	 */
	@Column(name="P9_SIZE_")
	private String p9Size;
	public void setP9Size(String p9Size) {
		this.p9Size=p9Size;
	}
	public void setP9Size(Object p9Size) {
		if (p9Size != null) {
			this.p9Size = p9Size.toString();
		}
	}
	public String getP9Size() {
		return this.p9Size ;
	}

	/**
	 * 第10名大小
	 */
	@Column(name="P10_SIZE_")
	private String p10Size;
	public void setP10Size(String p10Size) {
		this.p10Size=p10Size;
	}
	public void setP10Size(Object p10Size) {
		if (p10Size != null) {
			this.p10Size = p10Size.toString();
		}
	}
	public String getP10Size() {
		return this.p10Size ;
	}

	/**
	 * 冠亚和大小
	 */
	@Column(name="CHAMPION_SUM_SIZE_")
	private String championSumSize;
	public void setChampionSumSize(String championSumSize) {
		this.championSumSize=championSumSize;
	}
	public void setChampionSumSize(Object championSumSize) {
		if (championSumSize != null) {
			this.championSumSize = championSumSize.toString();
		}
	}
	public String getChampionSumSize() {
		return this.championSumSize ;
	}

	/**
	 * 第1名大小_英文
	 */
	@Column(name="P1_SIZE_EN_")
	private String p1SizeEn;
	public void setP1SizeEn(String p1SizeEn) {
		this.p1SizeEn=p1SizeEn;
	}
	public void setP1SizeEn(Object p1SizeEn) {
		if (p1SizeEn != null) {
			this.p1SizeEn = p1SizeEn.toString();
		}
	}
	public String getP1SizeEn() {
		return this.p1SizeEn ;
	}

	/**
	 * 第2名大小_英文
	 */
	@Column(name="P2_SIZE_EN_")
	private String p2SizeEn;
	public void setP2SizeEn(String p2SizeEn) {
		this.p2SizeEn=p2SizeEn;
	}
	public void setP2SizeEn(Object p2SizeEn) {
		if (p2SizeEn != null) {
			this.p2SizeEn = p2SizeEn.toString();
		}
	}
	public String getP2SizeEn() {
		return this.p2SizeEn ;
	}

	/**
	 * 第3名大小_英文
	 */
	@Column(name="P3_SIZE_EN_")
	private String p3SizeEn;
	public void setP3SizeEn(String p3SizeEn) {
		this.p3SizeEn=p3SizeEn;
	}
	public void setP3SizeEn(Object p3SizeEn) {
		if (p3SizeEn != null) {
			this.p3SizeEn = p3SizeEn.toString();
		}
	}
	public String getP3SizeEn() {
		return this.p3SizeEn ;
	}

	/**
	 * 第4名大小_英文
	 */
	@Column(name="P4_SIZE_EN_")
	private String p4SizeEn;
	public void setP4SizeEn(String p4SizeEn) {
		this.p4SizeEn=p4SizeEn;
	}
	public void setP4SizeEn(Object p4SizeEn) {
		if (p4SizeEn != null) {
			this.p4SizeEn = p4SizeEn.toString();
		}
	}
	public String getP4SizeEn() {
		return this.p4SizeEn ;
	}

	/**
	 * 第5名大小_英文
	 */
	@Column(name="P5_SIZE_EN_")
	private String p5SizeEn;
	public void setP5SizeEn(String p5SizeEn) {
		this.p5SizeEn=p5SizeEn;
	}
	public void setP5SizeEn(Object p5SizeEn) {
		if (p5SizeEn != null) {
			this.p5SizeEn = p5SizeEn.toString();
		}
	}
	public String getP5SizeEn() {
		return this.p5SizeEn ;
	}

	/**
	 * 第6名大小_英文
	 */
	@Column(name="P6_SIZE_EN_")
	private String p6SizeEn;
	public void setP6SizeEn(String p6SizeEn) {
		this.p6SizeEn=p6SizeEn;
	}
	public void setP6SizeEn(Object p6SizeEn) {
		if (p6SizeEn != null) {
			this.p6SizeEn = p6SizeEn.toString();
		}
	}
	public String getP6SizeEn() {
		return this.p6SizeEn ;
	}

	/**
	 * 第7名大小_英文
	 */
	@Column(name="P7_SIZE_EN_")
	private String p7SizeEn;
	public void setP7SizeEn(String p7SizeEn) {
		this.p7SizeEn=p7SizeEn;
	}
	public void setP7SizeEn(Object p7SizeEn) {
		if (p7SizeEn != null) {
			this.p7SizeEn = p7SizeEn.toString();
		}
	}
	public String getP7SizeEn() {
		return this.p7SizeEn ;
	}

	/**
	 * 第8名大小_英文
	 */
	@Column(name="P8_SIZE_EN_")
	private String p8SizeEn;
	public void setP8SizeEn(String p8SizeEn) {
		this.p8SizeEn=p8SizeEn;
	}
	public void setP8SizeEn(Object p8SizeEn) {
		if (p8SizeEn != null) {
			this.p8SizeEn = p8SizeEn.toString();
		}
	}
	public String getP8SizeEn() {
		return this.p8SizeEn ;
	}

	/**
	 * 第9名大小_英文
	 */
	@Column(name="P9_SIZE_EN_")
	private String p9SizeEn;
	public void setP9SizeEn(String p9SizeEn) {
		this.p9SizeEn=p9SizeEn;
	}
	public void setP9SizeEn(Object p9SizeEn) {
		if (p9SizeEn != null) {
			this.p9SizeEn = p9SizeEn.toString();
		}
	}
	public String getP9SizeEn() {
		return this.p9SizeEn ;
	}

	/**
	 * 第10名大小_英文
	 */
	@Column(name="P10_SIZE_EN_")
	private String p10SizeEn;
	public void setP10SizeEn(String p10SizeEn) {
		this.p10SizeEn=p10SizeEn;
	}
	public void setP10SizeEn(Object p10SizeEn) {
		if (p10SizeEn != null) {
			this.p10SizeEn = p10SizeEn.toString();
		}
	}
	public String getP10SizeEn() {
		return this.p10SizeEn ;
	}

	/**
	 * 冠亚和大小_英文
	 */
	@Column(name="CHAMPION_SUM_SIZE_EN_")
	private String championSumSizeEn;
	public void setChampionSumSizeEn(String championSumSizeEn) {
		this.championSumSizeEn=championSumSizeEn;
	}
	public void setChampionSumSizeEn(Object championSumSizeEn) {
		if (championSumSizeEn != null) {
			this.championSumSizeEn = championSumSizeEn.toString();
		}
	}
	public String getChampionSumSizeEn() {
		return this.championSumSizeEn ;
	}

	/**
	 * 第1名单双
	 */
	@Column(name="P1_SINGLE_")
	private String p1Single;
	public void setP1Single(String p1Single) {
		this.p1Single=p1Single;
	}
	public void setP1Single(Object p1Single) {
		if (p1Single != null) {
			this.p1Single = p1Single.toString();
		}
	}
	public String getP1Single() {
		return this.p1Single ;
	}

	/**
	 * 第2名单双
	 */
	@Column(name="P2_SINGLE_")
	private String p2Single;
	public void setP2Single(String p2Single) {
		this.p2Single=p2Single;
	}
	public void setP2Single(Object p2Single) {
		if (p2Single != null) {
			this.p2Single = p2Single.toString();
		}
	}
	public String getP2Single() {
		return this.p2Single ;
	}

	/**
	 * 第3名单双
	 */
	@Column(name="P3_SINGLE_")
	private String p3Single;
	public void setP3Single(String p3Single) {
		this.p3Single=p3Single;
	}
	public void setP3Single(Object p3Single) {
		if (p3Single != null) {
			this.p3Single = p3Single.toString();
		}
	}
	public String getP3Single() {
		return this.p3Single ;
	}

	/**
	 * 第4名单双
	 */
	@Column(name="P4_SINGLE_")
	private String p4Single;
	public void setP4Single(String p4Single) {
		this.p4Single=p4Single;
	}
	public void setP4Single(Object p4Single) {
		if (p4Single != null) {
			this.p4Single = p4Single.toString();
		}
	}
	public String getP4Single() {
		return this.p4Single ;
	}

	/**
	 * 第5名单双
	 */
	@Column(name="P5_SINGLE_")
	private String p5Single;
	public void setP5Single(String p5Single) {
		this.p5Single=p5Single;
	}
	public void setP5Single(Object p5Single) {
		if (p5Single != null) {
			this.p5Single = p5Single.toString();
		}
	}
	public String getP5Single() {
		return this.p5Single ;
	}

	/**
	 * 第6名单双
	 */
	@Column(name="P6_SINGLE_")
	private String p6Single;
	public void setP6Single(String p6Single) {
		this.p6Single=p6Single;
	}
	public void setP6Single(Object p6Single) {
		if (p6Single != null) {
			this.p6Single = p6Single.toString();
		}
	}
	public String getP6Single() {
		return this.p6Single ;
	}

	/**
	 * 第7名单双
	 */
	@Column(name="P7_SINGLE_")
	private String p7Single;
	public void setP7Single(String p7Single) {
		this.p7Single=p7Single;
	}
	public void setP7Single(Object p7Single) {
		if (p7Single != null) {
			this.p7Single = p7Single.toString();
		}
	}
	public String getP7Single() {
		return this.p7Single ;
	}

	/**
	 * 第8名单双
	 */
	@Column(name="P8_SINGLE_")
	private String p8Single;
	public void setP8Single(String p8Single) {
		this.p8Single=p8Single;
	}
	public void setP8Single(Object p8Single) {
		if (p8Single != null) {
			this.p8Single = p8Single.toString();
		}
	}
	public String getP8Single() {
		return this.p8Single ;
	}

	/**
	 * 第9名单双
	 */
	@Column(name="P9_SINGLE_")
	private String p9Single;
	public void setP9Single(String p9Single) {
		this.p9Single=p9Single;
	}
	public void setP9Single(Object p9Single) {
		if (p9Single != null) {
			this.p9Single = p9Single.toString();
		}
	}
	public String getP9Single() {
		return this.p9Single ;
	}

	/**
	 * 第10名单双
	 */
	@Column(name="P10_SINGLE_")
	private String p10Single;
	public void setP10Single(String p10Single) {
		this.p10Single=p10Single;
	}
	public void setP10Single(Object p10Single) {
		if (p10Single != null) {
			this.p10Single = p10Single.toString();
		}
	}
	public String getP10Single() {
		return this.p10Single ;
	}

	/**
	 * 冠亚军和单双
	 */
	@Column(name="CHAMPION_SUM_SINGLE_")
	private String championSumSingle;
	public void setChampionSumSingle(String championSumSingle) {
		this.championSumSingle=championSumSingle;
	}
	public void setChampionSumSingle(Object championSumSingle) {
		if (championSumSingle != null) {
			this.championSumSingle = championSumSingle.toString();
		}
	}
	public String getChampionSumSingle() {
		return this.championSumSingle ;
	}

	/**
	 * 第1名单双_英文
	 */
	@Column(name="P1_SINGLE_EN_")
	private String p1SingleEn;
	public void setP1SingleEn(String p1SingleEn) {
		this.p1SingleEn=p1SingleEn;
	}
	public void setP1SingleEn(Object p1SingleEn) {
		if (p1SingleEn != null) {
			this.p1SingleEn = p1SingleEn.toString();
		}
	}
	public String getP1SingleEn() {
		return this.p1SingleEn ;
	}

	/**
	 * 第2名单双_英文
	 */
	@Column(name="P2_SINGLE_EN_")
	private String p2SingleEn;
	public void setP2SingleEn(String p2SingleEn) {
		this.p2SingleEn=p2SingleEn;
	}
	public void setP2SingleEn(Object p2SingleEn) {
		if (p2SingleEn != null) {
			this.p2SingleEn = p2SingleEn.toString();
		}
	}
	public String getP2SingleEn() {
		return this.p2SingleEn ;
	}

	/**
	 * 第3名单双_英文
	 */
	@Column(name="P3_SINGLE_EN_")
	private String p3SingleEn;
	public void setP3SingleEn(String p3SingleEn) {
		this.p3SingleEn=p3SingleEn;
	}
	public void setP3SingleEn(Object p3SingleEn) {
		if (p3SingleEn != null) {
			this.p3SingleEn = p3SingleEn.toString();
		}
	}
	public String getP3SingleEn() {
		return this.p3SingleEn ;
	}

	/**
	 * 第4名单双_英文
	 */
	@Column(name="P4_SINGLE_EN_")
	private String p4SingleEn;
	public void setP4SingleEn(String p4SingleEn) {
		this.p4SingleEn=p4SingleEn;
	}
	public void setP4SingleEn(Object p4SingleEn) {
		if (p4SingleEn != null) {
			this.p4SingleEn = p4SingleEn.toString();
		}
	}
	public String getP4SingleEn() {
		return this.p4SingleEn ;
	}

	/**
	 * 第5名单双_英文
	 */
	@Column(name="P5_SINGLE_EN_")
	private String p5SingleEn;
	public void setP5SingleEn(String p5SingleEn) {
		this.p5SingleEn=p5SingleEn;
	}
	public void setP5SingleEn(Object p5SingleEn) {
		if (p5SingleEn != null) {
			this.p5SingleEn = p5SingleEn.toString();
		}
	}
	public String getP5SingleEn() {
		return this.p5SingleEn ;
	}

	/**
	 * 第6名单双_英文
	 */
	@Column(name="P6_SINGLE_EN_")
	private String p6SingleEn;
	public void setP6SingleEn(String p6SingleEn) {
		this.p6SingleEn=p6SingleEn;
	}
	public void setP6SingleEn(Object p6SingleEn) {
		if (p6SingleEn != null) {
			this.p6SingleEn = p6SingleEn.toString();
		}
	}
	public String getP6SingleEn() {
		return this.p6SingleEn ;
	}

	/**
	 * 第7名单双_英文
	 */
	@Column(name="P7_SINGLE_EN_")
	private String p7SingleEn;
	public void setP7SingleEn(String p7SingleEn) {
		this.p7SingleEn=p7SingleEn;
	}
	public void setP7SingleEn(Object p7SingleEn) {
		if (p7SingleEn != null) {
			this.p7SingleEn = p7SingleEn.toString();
		}
	}
	public String getP7SingleEn() {
		return this.p7SingleEn ;
	}

	/**
	 * 第8名单双_英文
	 */
	@Column(name="P8_SINGLE_EN_")
	private String p8SingleEn;
	public void setP8SingleEn(String p8SingleEn) {
		this.p8SingleEn=p8SingleEn;
	}
	public void setP8SingleEn(Object p8SingleEn) {
		if (p8SingleEn != null) {
			this.p8SingleEn = p8SingleEn.toString();
		}
	}
	public String getP8SingleEn() {
		return this.p8SingleEn ;
	}

	/**
	 * 第9名单双_英文
	 */
	@Column(name="P9_SINGLE_EN_")
	private String p9SingleEn;
	public void setP9SingleEn(String p9SingleEn) {
		this.p9SingleEn=p9SingleEn;
	}
	public void setP9SingleEn(Object p9SingleEn) {
		if (p9SingleEn != null) {
			this.p9SingleEn = p9SingleEn.toString();
		}
	}
	public String getP9SingleEn() {
		return this.p9SingleEn ;
	}

	/**
	 * 第10名单双_英文
	 */
	@Column(name="P10_SINGLE_EN_")
	private String p10SingleEn;
	public void setP10SingleEn(String p10SingleEn) {
		this.p10SingleEn=p10SingleEn;
	}
	public void setP10SingleEn(Object p10SingleEn) {
		if (p10SingleEn != null) {
			this.p10SingleEn = p10SingleEn.toString();
		}
	}
	public String getP10SingleEn() {
		return this.p10SingleEn ;
	}

	/**
	 * 冠亚和单双_英文
	 */
	@Column(name="CHAMPION_SUM_SINGLE_EN_")
	private String championSumSingleEn;
	public void setChampionSumSingleEn(String championSumSingleEn) {
		this.championSumSingleEn=championSumSingleEn;
	}
	public void setChampionSumSingleEn(Object championSumSingleEn) {
		if (championSumSingleEn != null) {
			this.championSumSingleEn = championSumSingleEn.toString();
		}
	}
	public String getChampionSumSingleEn() {
		return this.championSumSingleEn ;
	}

	/**
	 * 第1名龙虎
	 */
	@Column(name="P1_DRAGON_")
	private String p1Dragon;
	public void setP1Dragon(String p1Dragon) {
		this.p1Dragon=p1Dragon;
	}
	public void setP1Dragon(Object p1Dragon) {
		if (p1Dragon != null) {
			this.p1Dragon = p1Dragon.toString();
		}
	}
	public String getP1Dragon() {
		return this.p1Dragon ;
	}

	/**
	 * 第2名龙虎
	 */
	@Column(name="P2_DRAGON_")
	private String p2Dragon;
	public void setP2Dragon(String p2Dragon) {
		this.p2Dragon=p2Dragon;
	}
	public void setP2Dragon(Object p2Dragon) {
		if (p2Dragon != null) {
			this.p2Dragon = p2Dragon.toString();
		}
	}
	public String getP2Dragon() {
		return this.p2Dragon ;
	}

	/**
	 * 第3名龙虎
	 */
	@Column(name="P3_DRAGON_")
	private String p3Dragon;
	public void setP3Dragon(String p3Dragon) {
		this.p3Dragon=p3Dragon;
	}
	public void setP3Dragon(Object p3Dragon) {
		if (p3Dragon != null) {
			this.p3Dragon = p3Dragon.toString();
		}
	}
	public String getP3Dragon() {
		return this.p3Dragon ;
	}

	/**
	 * 第4名龙虎
	 */
	@Column(name="P4_DRAGON_")
	private String p4Dragon;
	public void setP4Dragon(String p4Dragon) {
		this.p4Dragon=p4Dragon;
	}
	public void setP4Dragon(Object p4Dragon) {
		if (p4Dragon != null) {
			this.p4Dragon = p4Dragon.toString();
		}
	}
	public String getP4Dragon() {
		return this.p4Dragon ;
	}

	/**
	 * 第5名龙虎
	 */
	@Column(name="P5_DRAGON_")
	private String p5Dragon;
	public void setP5Dragon(String p5Dragon) {
		this.p5Dragon=p5Dragon;
	}
	public void setP5Dragon(Object p5Dragon) {
		if (p5Dragon != null) {
			this.p5Dragon = p5Dragon.toString();
		}
	}
	public String getP5Dragon() {
		return this.p5Dragon ;
	}

	/**
	 * 第1名龙虎_英文
	 */
	@Column(name="P1_DRAGON_EN_")
	private String p1DragonEn;
	public void setP1DragonEn(String p1DragonEn) {
		this.p1DragonEn=p1DragonEn;
	}
	public void setP1DragonEn(Object p1DragonEn) {
		if (p1DragonEn != null) {
			this.p1DragonEn = p1DragonEn.toString();
		}
	}
	public String getP1DragonEn() {
		return this.p1DragonEn ;
	}

	/**
	 * 第2名龙虎_英文
	 */
	@Column(name="P2_DRAGON_EN_")
	private String p2DragonEn;
	public void setP2DragonEn(String p2DragonEn) {
		this.p2DragonEn=p2DragonEn;
	}
	public void setP2DragonEn(Object p2DragonEn) {
		if (p2DragonEn != null) {
			this.p2DragonEn = p2DragonEn.toString();
		}
	}
	public String getP2DragonEn() {
		return this.p2DragonEn ;
	}

	/**
	 * 第3名龙虎_英文
	 */
	@Column(name="P3_DRAGON_EN_")
	private String p3DragonEn;
	public void setP3DragonEn(String p3DragonEn) {
		this.p3DragonEn=p3DragonEn;
	}
	public void setP3DragonEn(Object p3DragonEn) {
		if (p3DragonEn != null) {
			this.p3DragonEn = p3DragonEn.toString();
		}
	}
	public String getP3DragonEn() {
		return this.p3DragonEn ;
	}

	/**
	 * 第4名龙虎_英文
	 */
	@Column(name="P4_DRAGON_EN_")
	private String p4DragonEn;
	public void setP4DragonEn(String p4DragonEn) {
		this.p4DragonEn=p4DragonEn;
	}
	public void setP4DragonEn(Object p4DragonEn) {
		if (p4DragonEn != null) {
			this.p4DragonEn = p4DragonEn.toString();
		}
	}
	public String getP4DragonEn() {
		return this.p4DragonEn ;
	}

	/**
	 * 第5名龙虎_英文
	 */
	@Column(name="P5_DRAGON_EN_")
	private String p5DragonEn;
	public void setP5DragonEn(String p5DragonEn) {
		this.p5DragonEn=p5DragonEn;
	}
	public void setP5DragonEn(Object p5DragonEn) {
		if (p5DragonEn != null) {
			this.p5DragonEn = p5DragonEn.toString();
		}
	}
	public String getP5DragonEn() {
		return this.p5DragonEn ;
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