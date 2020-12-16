package com.ibm.old.v1.cloud.ibm_rep_draw_cqssc.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_rep_draw_cqssc 
 * 重庆时时彩CQSSC_结果数据IBM_REP_DRAW_CQSSC的实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_rep_draw_cqssc")
public class IbmRepDrawCqsscT implements Serializable {

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
	 * 重庆时时彩CQSSC_结果数据主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_REP_DRAW_CQSSC_ID_")
	private String ibmRepDrawCqsscId;
	public void setIbmRepDrawCqsscId(String ibmRepDrawCqsscId) {
		this.ibmRepDrawCqsscId=ibmRepDrawCqsscId;
	}
	public void setIbmRepDrawCqsscId(Object ibmRepDrawCqsscId) {
		if (ibmRepDrawCqsscId != null) {
			this.ibmRepDrawCqsscId = ibmRepDrawCqsscId.toString();
		}
	}
	public String getIbmRepDrawCqsscId() {
		return this.ibmRepDrawCqsscId ;
	}

	/**
	 * 重庆时时彩CQSSC_抓取数据
	 */
	@Column(name="REP_GRAB_CQSSC_ID_")
	private String repGrabCqsscId;
	public void setRepGrabCqsscId(String repGrabCqsscId) {
		this.repGrabCqsscId=repGrabCqsscId;
	}
	public void setRepGrabCqsscId(Object repGrabCqsscId) {
		if (repGrabCqsscId != null) {
			this.repGrabCqsscId = repGrabCqsscId.toString();
		}
	}
	public String getRepGrabCqsscId() {
		return this.repGrabCqsscId ;
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
	 * 龙虎和
	 */
	@Column(name="DRAGON_TIGER_")
	private String dragonTiger;
	public void setDragonTiger(String dragonTiger) {
		this.dragonTiger=dragonTiger;
	}
	public void setDragonTiger(Object dragonTiger) {
		if (dragonTiger != null) {
			this.dragonTiger = dragonTiger.toString();
		}
	}
	public String getDragonTiger() {
		return this.dragonTiger ;
	}

	/**
	 * 龙虎和_英文
	 */
	@Column(name="DRAGON_TIGER_EN_")
	private String dragonTigerEn;
	public void setDragonTigerEn(String dragonTigerEn) {
		this.dragonTigerEn=dragonTigerEn;
	}
	public void setDragonTigerEn(Object dragonTigerEn) {
		if (dragonTigerEn != null) {
			this.dragonTigerEn = dragonTigerEn.toString();
		}
	}
	public String getDragonTigerEn() {
		return this.dragonTigerEn ;
	}

	/**
	 * 总和
	 */
	@Column(name="TOTAL_")
	private String total;
	public void setTotal(String total) {
		this.total=total;
	}
	public void setTotal(Object total) {
		if (total != null) {
			this.total = total.toString();
		}
	}
	public String getTotal() {
		return this.total ;
	}

	/**
	 * 总和_英文
	 */
	@Column(name="TOTAL_EN_")
	private String totalEn;
	public void setTotalEn(String totalEn) {
		this.totalEn=totalEn;
	}
	public void setTotalEn(Object totalEn) {
		if (totalEn != null) {
			this.totalEn = totalEn.toString();
		}
	}
	public String getTotalEn() {
		return this.totalEn ;
	}

	/**
	 * 总和大小
	 */
	@Column(name="TOTAL_SIZE_")
	private String totalSize;
	public void setTotalSize(String totalSize) {
		this.totalSize=totalSize;
	}
	public void setTotalSize(Object totalSize) {
		if (totalSize != null) {
			this.totalSize = totalSize.toString();
		}
	}
	public String getTotalSize() {
		return this.totalSize ;
	}

	/**
	 * 总和大小_英文
	 */
	@Column(name="TOTAL_SIZE_EN_")
	private String totalSizeEn;
	public void setTotalSizeEn(String totalSizeEn) {
		this.totalSizeEn=totalSizeEn;
	}
	public void setTotalSizeEn(Object totalSizeEn) {
		if (totalSizeEn != null) {
			this.totalSizeEn = totalSizeEn.toString();
		}
	}
	public String getTotalSizeEn() {
		return this.totalSizeEn ;
	}

	/**
	 * 总和单双
	 */
	@Column(name="TOTAL_SINGLE_")
	private String totalSingle;
	public void setTotalSingle(String totalSingle) {
		this.totalSingle=totalSingle;
	}
	public void setTotalSingle(Object totalSingle) {
		if (totalSingle != null) {
			this.totalSingle = totalSingle.toString();
		}
	}
	public String getTotalSingle() {
		return this.totalSingle ;
	}

	/**
	 * 总和单双_英文
	 */
	@Column(name="TOTAL_SINGLE_EN_")
	private String totalSingleEn;
	public void setTotalSingleEn(String totalSingleEn) {
		this.totalSingleEn=totalSingleEn;
	}
	public void setTotalSingleEn(Object totalSingleEn) {
		if (totalSingleEn != null) {
			this.totalSingleEn = totalSingleEn.toString();
		}
	}
	public String getTotalSingleEn() {
		return this.totalSingleEn ;
	}

	/**
	 * 前三
	 */
	@Column(name="TOP_")
	private String top;
	public void setTop(String top) {
		this.top=top;
	}
	public void setTop(Object top) {
		if (top != null) {
			this.top = top.toString();
		}
	}
	public String getTop() {
		return this.top ;
	}

	/**
	 * 前三_英文
	 */
	@Column(name="TOP_EN_")
	private String topEn;
	public void setTopEn(String topEn) {
		this.topEn=topEn;
	}
	public void setTopEn(Object topEn) {
		if (topEn != null) {
			this.topEn = topEn.toString();
		}
	}
	public String getTopEn() {
		return this.topEn ;
	}

	/**
	 * 中三
	 */
	@Column(name="CENTRE_")
	private String centre;
	public void setCentre(String centre) {
		this.centre=centre;
	}
	public void setCentre(Object centre) {
		if (centre != null) {
			this.centre = centre.toString();
		}
	}
	public String getCentre() {
		return this.centre ;
	}

	/**
	 * 中三_英文
	 */
	@Column(name="CENTRE_EN_")
	private String centreEn;
	public void setCentreEn(String centreEn) {
		this.centreEn=centreEn;
	}
	public void setCentreEn(Object centreEn) {
		if (centreEn != null) {
			this.centreEn = centreEn.toString();
		}
	}
	public String getCentreEn() {
		return this.centreEn ;
	}

	/**
	 * 后三
	 */
	@Column(name="LATER_")
	private String later;
	public void setLater(String later) {
		this.later=later;
	}
	public void setLater(Object later) {
		if (later != null) {
			this.later = later.toString();
		}
	}
	public String getLater() {
		return this.later ;
	}

	/**
	 * 后三_英文
	 */
	@Column(name="LATER_EN_")
	private String laterEn;
	public void setLaterEn(String laterEn) {
		this.laterEn=laterEn;
	}
	public void setLaterEn(Object laterEn) {
		if (laterEn != null) {
			this.laterEn = laterEn.toString();
		}
	}
	public String getLaterEn() {
		return this.laterEn ;
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