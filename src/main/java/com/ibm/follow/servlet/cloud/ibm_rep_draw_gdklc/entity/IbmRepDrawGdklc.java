package com.ibm.follow.servlet.cloud.ibm_rep_draw_gdklc.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * The persistent class for the database table ibm_rep_draw_gdklc 
 * IBM_结果数据_广东快乐十分 实体类
 * @author Robot
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_rep_draw_gdklc")
public class IbmRepDrawGdklc implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IBM_结果数据_广东快乐十分主键
	 * 根据底层数据库
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IBM_REP_DRAW_GDKLC_ID_")
	private String ibmRepDrawGdklcId;
	public void setIbmRepDrawGdklcId(String ibmRepDrawGdklcId) {
		this.ibmRepDrawGdklcId=ibmRepDrawGdklcId;
	}
	public void setIbmRepDrawGdklcId(Object ibmRepDrawGdklcId) {
		if (ibmRepDrawGdklcId != null) {
			this.ibmRepDrawGdklcId = ibmRepDrawGdklcId.toString();
		}
	}
	public String getIbmRepDrawGdklcId() {
		return this.ibmRepDrawGdklcId ;
	}

	/**
	 * 抓取数据_广东快乐十分主键
	 */
	@Column(name="REP_GRAB_GDKLC_ID_")
	private String repGrabGdklcId;
	public void setRepGrabGdklcId(String repGrabGdklcId) {
		this.repGrabGdklcId=repGrabGdklcId;
	}
	public void setRepGrabGdklcId(Object repGrabGdklcId) {
		if (repGrabGdklcId != null) {
			this.repGrabGdklcId = repGrabGdklcId.toString();
		}
	}
	public String getRepGrabGdklcId() {
		return this.repGrabGdklcId ;
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
	public void setDrawTime(Object drawTime) throws ParseException {
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
	 * 第1名尾大小
	 */
	@Column(name="P1_TAIL_SIZE_")
	private String p1TailSize;
	public void setP1TailSize(String p1TailSize) {
		this.p1TailSize=p1TailSize;
	}
	public void setP1TailSize(Object p1TailSize) {
		if (p1TailSize != null) {
			this.p1TailSize = p1TailSize.toString();
		}
	}
	public String getP1TailSize() {
		return this.p1TailSize ;
	}

	/**
	 * 第2名尾大小
	 */
	@Column(name="P2_TAIL_SIZE_")
	private String p2TailSize;
	public void setP2TailSize(String p2TailSize) {
		this.p2TailSize=p2TailSize;
	}
	public void setP2TailSize(Object p2TailSize) {
		if (p2TailSize != null) {
			this.p2TailSize = p2TailSize.toString();
		}
	}
	public String getP2TailSize() {
		return this.p2TailSize ;
	}

	/**
	 * 第3名尾大小
	 */
	@Column(name="P3_TAIL_SIZE_")
	private String p3TailSize;
	public void setP3TailSize(String p3TailSize) {
		this.p3TailSize=p3TailSize;
	}
	public void setP3TailSize(Object p3TailSize) {
		if (p3TailSize != null) {
			this.p3TailSize = p3TailSize.toString();
		}
	}
	public String getP3TailSize() {
		return this.p3TailSize ;
	}

	/**
	 * 第4名尾大小
	 */
	@Column(name="P4_TAIL_SIZE_")
	private String p4TailSize;
	public void setP4TailSize(String p4TailSize) {
		this.p4TailSize=p4TailSize;
	}
	public void setP4TailSize(Object p4TailSize) {
		if (p4TailSize != null) {
			this.p4TailSize = p4TailSize.toString();
		}
	}
	public String getP4TailSize() {
		return this.p4TailSize ;
	}

	/**
	 * 第5名尾大小
	 */
	@Column(name="P5_TAIL_SIZE_")
	private String p5TailSize;
	public void setP5TailSize(String p5TailSize) {
		this.p5TailSize=p5TailSize;
	}
	public void setP5TailSize(Object p5TailSize) {
		if (p5TailSize != null) {
			this.p5TailSize = p5TailSize.toString();
		}
	}
	public String getP5TailSize() {
		return this.p5TailSize ;
	}

	/**
	 * 第6名尾大小
	 */
	@Column(name="P6_TAIL_SIZE_")
	private String p6TailSize;
	public void setP6TailSize(String p6TailSize) {
		this.p6TailSize=p6TailSize;
	}
	public void setP6TailSize(Object p6TailSize) {
		if (p6TailSize != null) {
			this.p6TailSize = p6TailSize.toString();
		}
	}
	public String getP6TailSize() {
		return this.p6TailSize ;
	}

	/**
	 * 第7名尾大小
	 */
	@Column(name="P7_TAIL_SIZE_")
	private String p7TailSize;
	public void setP7TailSize(String p7TailSize) {
		this.p7TailSize=p7TailSize;
	}
	public void setP7TailSize(Object p7TailSize) {
		if (p7TailSize != null) {
			this.p7TailSize = p7TailSize.toString();
		}
	}
	public String getP7TailSize() {
		return this.p7TailSize ;
	}

	/**
	 * 第8名尾大小
	 */
	@Column(name="P8_TAIL_SIZE_")
	private String p8TailSize;
	public void setP8TailSize(String p8TailSize) {
		this.p8TailSize=p8TailSize;
	}
	public void setP8TailSize(Object p8TailSize) {
		if (p8TailSize != null) {
			this.p8TailSize = p8TailSize.toString();
		}
	}
	public String getP8TailSize() {
		return this.p8TailSize ;
	}

	/**
	 * 第1名尾大小_英文
	 */
	@Column(name="P1_TAIL_SIZE_EN_")
	private String p1TailSizeEn;
	public void setP1TailSizeEn(String p1TailSizeEn) {
		this.p1TailSizeEn=p1TailSizeEn;
	}
	public void setP1TailSizeEn(Object p1TailSizeEn) {
		if (p1TailSizeEn != null) {
			this.p1TailSizeEn = p1TailSizeEn.toString();
		}
	}
	public String getP1TailSizeEn() {
		return this.p1TailSizeEn ;
	}

	/**
	 * 第2名尾大小_英文
	 */
	@Column(name="P2_TAIL_SIZE_EN_")
	private String p2TailSizeEn;
	public void setP2TailSizeEn(String p2TailSizeEn) {
		this.p2TailSizeEn=p2TailSizeEn;
	}
	public void setP2TailSizeEn(Object p2TailSizeEn) {
		if (p2TailSizeEn != null) {
			this.p2TailSizeEn = p2TailSizeEn.toString();
		}
	}
	public String getP2TailSizeEn() {
		return this.p2TailSizeEn ;
	}

	/**
	 * 第3名尾大小_英文
	 */
	@Column(name="P3_TAIL_SIZE_EN_")
	private String p3TailSizeEn;
	public void setP3TailSizeEn(String p3TailSizeEn) {
		this.p3TailSizeEn=p3TailSizeEn;
	}
	public void setP3TailSizeEn(Object p3TailSizeEn) {
		if (p3TailSizeEn != null) {
			this.p3TailSizeEn = p3TailSizeEn.toString();
		}
	}
	public String getP3TailSizeEn() {
		return this.p3TailSizeEn ;
	}

	/**
	 * 第4名尾大小_英文
	 */
	@Column(name="P4_TAIL_SIZE_EN_")
	private String p4TailSizeEn;
	public void setP4TailSizeEn(String p4TailSizeEn) {
		this.p4TailSizeEn=p4TailSizeEn;
	}
	public void setP4TailSizeEn(Object p4TailSizeEn) {
		if (p4TailSizeEn != null) {
			this.p4TailSizeEn = p4TailSizeEn.toString();
		}
	}
	public String getP4TailSizeEn() {
		return this.p4TailSizeEn ;
	}

	/**
	 * 第5名尾大小_英文
	 */
	@Column(name="P5_TAIL_SIZE_EN_")
	private String p5TailSizeEn;
	public void setP5TailSizeEn(String p5TailSizeEn) {
		this.p5TailSizeEn=p5TailSizeEn;
	}
	public void setP5TailSizeEn(Object p5TailSizeEn) {
		if (p5TailSizeEn != null) {
			this.p5TailSizeEn = p5TailSizeEn.toString();
		}
	}
	public String getP5TailSizeEn() {
		return this.p5TailSizeEn ;
	}

	/**
	 * 第6名尾大小_英文
	 */
	@Column(name="P6_TAIL_SIZE_EN_")
	private String p6TailSizeEn;
	public void setP6TailSizeEn(String p6TailSizeEn) {
		this.p6TailSizeEn=p6TailSizeEn;
	}
	public void setP6TailSizeEn(Object p6TailSizeEn) {
		if (p6TailSizeEn != null) {
			this.p6TailSizeEn = p6TailSizeEn.toString();
		}
	}
	public String getP6TailSizeEn() {
		return this.p6TailSizeEn ;
	}

	/**
	 * 第7名尾大小_英文
	 */
	@Column(name="P7_TAIL_SIZE_EN_")
	private String p7TailSizeEn;
	public void setP7TailSizeEn(String p7TailSizeEn) {
		this.p7TailSizeEn=p7TailSizeEn;
	}
	public void setP7TailSizeEn(Object p7TailSizeEn) {
		if (p7TailSizeEn != null) {
			this.p7TailSizeEn = p7TailSizeEn.toString();
		}
	}
	public String getP7TailSizeEn() {
		return this.p7TailSizeEn ;
	}

	/**
	 * 第8名尾大小_英文
	 */
	@Column(name="P8_TAIL_SIZE_EN_")
	private String p8TailSizeEn;
	public void setP8TailSizeEn(String p8TailSizeEn) {
		this.p8TailSizeEn=p8TailSizeEn;
	}
	public void setP8TailSizeEn(Object p8TailSizeEn) {
		if (p8TailSizeEn != null) {
			this.p8TailSizeEn = p8TailSizeEn.toString();
		}
	}
	public String getP8TailSizeEn() {
		return this.p8TailSizeEn ;
	}

	/**
	 * 第1名合数单双
	 */
	@Column(name="P1_SUM_SINGLE_")
	private String p1SumSingle;
	public void setP1SumSingle(String p1SumSingle) {
		this.p1SumSingle=p1SumSingle;
	}
	public void setP1SumSingle(Object p1SumSingle) {
		if (p1SumSingle != null) {
			this.p1SumSingle = p1SumSingle.toString();
		}
	}
	public String getP1SumSingle() {
		return this.p1SumSingle ;
	}

	/**
	 * 第2名合数单双
	 */
	@Column(name="P2_SUM_SINGLE_")
	private String p2SumSingle;
	public void setP2SumSingle(String p2SumSingle) {
		this.p2SumSingle=p2SumSingle;
	}
	public void setP2SumSingle(Object p2SumSingle) {
		if (p2SumSingle != null) {
			this.p2SumSingle = p2SumSingle.toString();
		}
	}
	public String getP2SumSingle() {
		return this.p2SumSingle ;
	}

	/**
	 * 第第3名合数单双
	 */
	@Column(name="P3_SUM_SINGLE_")
	private String p3SumSingle;
	public void setP3SumSingle(String p3SumSingle) {
		this.p3SumSingle=p3SumSingle;
	}
	public void setP3SumSingle(Object p3SumSingle) {
		if (p3SumSingle != null) {
			this.p3SumSingle = p3SumSingle.toString();
		}
	}
	public String getP3SumSingle() {
		return this.p3SumSingle ;
	}

	/**
	 * 第4名合数单双
	 */
	@Column(name="P4_SUM_SINGLE_")
	private String p4SumSingle;
	public void setP4SumSingle(String p4SumSingle) {
		this.p4SumSingle=p4SumSingle;
	}
	public void setP4SumSingle(Object p4SumSingle) {
		if (p4SumSingle != null) {
			this.p4SumSingle = p4SumSingle.toString();
		}
	}
	public String getP4SumSingle() {
		return this.p4SumSingle ;
	}

	/**
	 * 第5名合数单双
	 */
	@Column(name="P5_SUM_SINGLE_")
	private String p5SumSingle;
	public void setP5SumSingle(String p5SumSingle) {
		this.p5SumSingle=p5SumSingle;
	}
	public void setP5SumSingle(Object p5SumSingle) {
		if (p5SumSingle != null) {
			this.p5SumSingle = p5SumSingle.toString();
		}
	}
	public String getP5SumSingle() {
		return this.p5SumSingle ;
	}

	/**
	 * 第6名合数单双
	 */
	@Column(name="P6_SUM_SINGLE_")
	private String p6SumSingle;
	public void setP6SumSingle(String p6SumSingle) {
		this.p6SumSingle=p6SumSingle;
	}
	public void setP6SumSingle(Object p6SumSingle) {
		if (p6SumSingle != null) {
			this.p6SumSingle = p6SumSingle.toString();
		}
	}
	public String getP6SumSingle() {
		return this.p6SumSingle ;
	}

	/**
	 * 第7名合数单双
	 */
	@Column(name="P7_SUM_SINGLE_")
	private String p7SumSingle;
	public void setP7SumSingle(String p7SumSingle) {
		this.p7SumSingle=p7SumSingle;
	}
	public void setP7SumSingle(Object p7SumSingle) {
		if (p7SumSingle != null) {
			this.p7SumSingle = p7SumSingle.toString();
		}
	}
	public String getP7SumSingle() {
		return this.p7SumSingle ;
	}

	/**
	 * 第8名合数单双
	 */
	@Column(name="P8_SUM_SINGLE_")
	private String p8SumSingle;
	public void setP8SumSingle(String p8SumSingle) {
		this.p8SumSingle=p8SumSingle;
	}
	public void setP8SumSingle(Object p8SumSingle) {
		if (p8SumSingle != null) {
			this.p8SumSingle = p8SumSingle.toString();
		}
	}
	public String getP8SumSingle() {
		return this.p8SumSingle ;
	}

	/**
	 * 第1名合数单双_英文
	 */
	@Column(name="P1_SUM_SINGLE_EN_")
	private String p1SumSingleEn;
	public void setP1SumSingleEn(String p1SumSingleEn) {
		this.p1SumSingleEn=p1SumSingleEn;
	}
	public void setP1SumSingleEn(Object p1SumSingleEn) {
		if (p1SumSingleEn != null) {
			this.p1SumSingleEn = p1SumSingleEn.toString();
		}
	}
	public String getP1SumSingleEn() {
		return this.p1SumSingleEn ;
	}

	/**
	 * 第2名合数单双_英文
	 */
	@Column(name="P2_SUM_SINGLE_EN_")
	private String p2SumSingleEn;
	public void setP2SumSingleEn(String p2SumSingleEn) {
		this.p2SumSingleEn=p2SumSingleEn;
	}
	public void setP2SumSingleEn(Object p2SumSingleEn) {
		if (p2SumSingleEn != null) {
			this.p2SumSingleEn = p2SumSingleEn.toString();
		}
	}
	public String getP2SumSingleEn() {
		return this.p2SumSingleEn ;
	}

	/**
	 * 第3名合数单双_英文
	 */
	@Column(name="P3_SUM_SINGLE_EN_")
	private String p3SumSingleEn;
	public void setP3SumSingleEn(String p3SumSingleEn) {
		this.p3SumSingleEn=p3SumSingleEn;
	}
	public void setP3SumSingleEn(Object p3SumSingleEn) {
		if (p3SumSingleEn != null) {
			this.p3SumSingleEn = p3SumSingleEn.toString();
		}
	}
	public String getP3SumSingleEn() {
		return this.p3SumSingleEn ;
	}

	/**
	 * 第4名合数单双_英文
	 */
	@Column(name="P4_SUM_SINGLE_EN_")
	private String p4SumSingleEn;
	public void setP4SumSingleEn(String p4SumSingleEn) {
		this.p4SumSingleEn=p4SumSingleEn;
	}
	public void setP4SumSingleEn(Object p4SumSingleEn) {
		if (p4SumSingleEn != null) {
			this.p4SumSingleEn = p4SumSingleEn.toString();
		}
	}
	public String getP4SumSingleEn() {
		return this.p4SumSingleEn ;
	}

	/**
	 * 第5名合数单双_英文
	 */
	@Column(name="P5_SUM_SINGLE_EN_")
	private String p5SumSingleEn;
	public void setP5SumSingleEn(String p5SumSingleEn) {
		this.p5SumSingleEn=p5SumSingleEn;
	}
	public void setP5SumSingleEn(Object p5SumSingleEn) {
		if (p5SumSingleEn != null) {
			this.p5SumSingleEn = p5SumSingleEn.toString();
		}
	}
	public String getP5SumSingleEn() {
		return this.p5SumSingleEn ;
	}

	/**
	 * 第6名合数单双_英文
	 */
	@Column(name="P6_SUM_SINGLE_EN_")
	private String p6SumSingleEn;
	public void setP6SumSingleEn(String p6SumSingleEn) {
		this.p6SumSingleEn=p6SumSingleEn;
	}
	public void setP6SumSingleEn(Object p6SumSingleEn) {
		if (p6SumSingleEn != null) {
			this.p6SumSingleEn = p6SumSingleEn.toString();
		}
	}
	public String getP6SumSingleEn() {
		return this.p6SumSingleEn ;
	}

	/**
	 * 第7名合数单双_英文
	 */
	@Column(name="P7_SUM_SINGLE_EN_")
	private String p7SumSingleEn;
	public void setP7SumSingleEn(String p7SumSingleEn) {
		this.p7SumSingleEn=p7SumSingleEn;
	}
	public void setP7SumSingleEn(Object p7SumSingleEn) {
		if (p7SumSingleEn != null) {
			this.p7SumSingleEn = p7SumSingleEn.toString();
		}
	}
	public String getP7SumSingleEn() {
		return this.p7SumSingleEn ;
	}

	/**
	 * 第8名合数单双_英文
	 */
	@Column(name="P8_SUM_SINGLE_EN_")
	private String p8SumSingleEn;
	public void setP8SumSingleEn(String p8SumSingleEn) {
		this.p8SumSingleEn=p8SumSingleEn;
	}
	public void setP8SumSingleEn(Object p8SumSingleEn) {
		if (p8SumSingleEn != null) {
			this.p8SumSingleEn = p8SumSingleEn.toString();
		}
	}
	public String getP8SumSingleEn() {
		return this.p8SumSingleEn ;
	}

	/**
	 * 第1名中发白
	 */
	@Column(name="P1_MSW_")
	private String p1Msw;
	public void setP1Msw(String p1Msw) {
		this.p1Msw=p1Msw;
	}
	public void setP1Msw(Object p1Msw) {
		if (p1Msw != null) {
			this.p1Msw = p1Msw.toString();
		}
	}
	public String getP1Msw() {
		return this.p1Msw ;
	}

	/**
	 * 第2名中发白
	 */
	@Column(name="P2_MSW_")
	private String p2Msw;
	public void setP2Msw(String p2Msw) {
		this.p2Msw=p2Msw;
	}
	public void setP2Msw(Object p2Msw) {
		if (p2Msw != null) {
			this.p2Msw = p2Msw.toString();
		}
	}
	public String getP2Msw() {
		return this.p2Msw ;
	}

	/**
	 * 第3名中发白
	 */
	@Column(name="P3_MSW_")
	private String p3Msw;
	public void setP3Msw(String p3Msw) {
		this.p3Msw=p3Msw;
	}
	public void setP3Msw(Object p3Msw) {
		if (p3Msw != null) {
			this.p3Msw = p3Msw.toString();
		}
	}
	public String getP3Msw() {
		return this.p3Msw ;
	}

	/**
	 * 第4名中发白
	 */
	@Column(name="P4_MSW_")
	private String p4Msw;
	public void setP4Msw(String p4Msw) {
		this.p4Msw=p4Msw;
	}
	public void setP4Msw(Object p4Msw) {
		if (p4Msw != null) {
			this.p4Msw = p4Msw.toString();
		}
	}
	public String getP4Msw() {
		return this.p4Msw ;
	}

	/**
	 * 第5名中发白
	 */
	@Column(name="P5_MSW_")
	private String p5Msw;
	public void setP5Msw(String p5Msw) {
		this.p5Msw=p5Msw;
	}
	public void setP5Msw(Object p5Msw) {
		if (p5Msw != null) {
			this.p5Msw = p5Msw.toString();
		}
	}
	public String getP5Msw() {
		return this.p5Msw ;
	}

	/**
	 * 第6名中发白
	 */
	@Column(name="P6_MSW_")
	private String p6Msw;
	public void setP6Msw(String p6Msw) {
		this.p6Msw=p6Msw;
	}
	public void setP6Msw(Object p6Msw) {
		if (p6Msw != null) {
			this.p6Msw = p6Msw.toString();
		}
	}
	public String getP6Msw() {
		return this.p6Msw ;
	}

	/**
	 * 第7名中发白
	 */
	@Column(name="P7_MSW_")
	private String p7Msw;
	public void setP7Msw(String p7Msw) {
		this.p7Msw=p7Msw;
	}
	public void setP7Msw(Object p7Msw) {
		if (p7Msw != null) {
			this.p7Msw = p7Msw.toString();
		}
	}
	public String getP7Msw() {
		return this.p7Msw ;
	}

	/**
	 * 第8名中发白
	 */
	@Column(name="P8_MSW_")
	private String p8Msw;
	public void setP8Msw(String p8Msw) {
		this.p8Msw=p8Msw;
	}
	public void setP8Msw(Object p8Msw) {
		if (p8Msw != null) {
			this.p8Msw = p8Msw.toString();
		}
	}
	public String getP8Msw() {
		return this.p8Msw ;
	}

	/**
	 * 第1名中发白_英文
	 */
	@Column(name="P1_MSW_EN_")
	private String p1MswEn;
	public void setP1MswEn(String p1MswEn) {
		this.p1MswEn=p1MswEn;
	}
	public void setP1MswEn(Object p1MswEn) {
		if (p1MswEn != null) {
			this.p1MswEn = p1MswEn.toString();
		}
	}
	public String getP1MswEn() {
		return this.p1MswEn ;
	}

	/**
	 * 第2名中发白_英文
	 */
	@Column(name="P2_MSW_EN_")
	private String p2MswEn;
	public void setP2MswEn(String p2MswEn) {
		this.p2MswEn=p2MswEn;
	}
	public void setP2MswEn(Object p2MswEn) {
		if (p2MswEn != null) {
			this.p2MswEn = p2MswEn.toString();
		}
	}
	public String getP2MswEn() {
		return this.p2MswEn ;
	}

	/**
	 * 第3名中发白_英文
	 */
	@Column(name="P3_MSW_EN_")
	private String p3MswEn;
	public void setP3MswEn(String p3MswEn) {
		this.p3MswEn=p3MswEn;
	}
	public void setP3MswEn(Object p3MswEn) {
		if (p3MswEn != null) {
			this.p3MswEn = p3MswEn.toString();
		}
	}
	public String getP3MswEn() {
		return this.p3MswEn ;
	}

	/**
	 * 第4名中发白_英文
	 */
	@Column(name="P4_MSW_EN_")
	private String p4MswEn;
	public void setP4MswEn(String p4MswEn) {
		this.p4MswEn=p4MswEn;
	}
	public void setP4MswEn(Object p4MswEn) {
		if (p4MswEn != null) {
			this.p4MswEn = p4MswEn.toString();
		}
	}
	public String getP4MswEn() {
		return this.p4MswEn ;
	}

	/**
	 * 第5名中发白_英文
	 */
	@Column(name="P5_MSW_EN_")
	private String p5MswEn;
	public void setP5MswEn(String p5MswEn) {
		this.p5MswEn=p5MswEn;
	}
	public void setP5MswEn(Object p5MswEn) {
		if (p5MswEn != null) {
			this.p5MswEn = p5MswEn.toString();
		}
	}
	public String getP5MswEn() {
		return this.p5MswEn ;
	}

	/**
	 * 第6名中发白_英文
	 */
	@Column(name="P6_MSW_EN_")
	private String p6MswEn;
	public void setP6MswEn(String p6MswEn) {
		this.p6MswEn=p6MswEn;
	}
	public void setP6MswEn(Object p6MswEn) {
		if (p6MswEn != null) {
			this.p6MswEn = p6MswEn.toString();
		}
	}
	public String getP6MswEn() {
		return this.p6MswEn ;
	}

	/**
	 * 第7名中发白_英文
	 */
	@Column(name="P7_MSW_EN_")
	private String p7MswEn;
	public void setP7MswEn(String p7MswEn) {
		this.p7MswEn=p7MswEn;
	}
	public void setP7MswEn(Object p7MswEn) {
		if (p7MswEn != null) {
			this.p7MswEn = p7MswEn.toString();
		}
	}
	public String getP7MswEn() {
		return this.p7MswEn ;
	}

	/**
	 * 第8名中发白_英文
	 */
	@Column(name="P8_MSW_EN_")
	private String p8MswEn;
	public void setP8MswEn(String p8MswEn) {
		this.p8MswEn=p8MswEn;
	}
	public void setP8MswEn(Object p8MswEn) {
		if (p8MswEn != null) {
			this.p8MswEn = p8MswEn.toString();
		}
	}
	public String getP8MswEn() {
		return this.p8MswEn ;
	}

	/**
	 * 第1名方位
	 */
	@Column(name="P1_POSITION_")
	private String p1Position;
	public void setP1Position(String p1Position) {
		this.p1Position=p1Position;
	}
	public void setP1Position(Object p1Position) {
		if (p1Position != null) {
			this.p1Position = p1Position.toString();
		}
	}
	public String getP1Position() {
		return this.p1Position ;
	}

	/**
	 * 第2名方位
	 */
	@Column(name="P2_POSITION_")
	private String p2Position;
	public void setP2Position(String p2Position) {
		this.p2Position=p2Position;
	}
	public void setP2Position(Object p2Position) {
		if (p2Position != null) {
			this.p2Position = p2Position.toString();
		}
	}
	public String getP2Position() {
		return this.p2Position ;
	}

	/**
	 * 第3名方位
	 */
	@Column(name="P3_POSITION_")
	private String p3Position;
	public void setP3Position(String p3Position) {
		this.p3Position=p3Position;
	}
	public void setP3Position(Object p3Position) {
		if (p3Position != null) {
			this.p3Position = p3Position.toString();
		}
	}
	public String getP3Position() {
		return this.p3Position ;
	}

	/**
	 * 第4名方位
	 */
	@Column(name="P4_POSITION_")
	private String p4Position;
	public void setP4Position(String p4Position) {
		this.p4Position=p4Position;
	}
	public void setP4Position(Object p4Position) {
		if (p4Position != null) {
			this.p4Position = p4Position.toString();
		}
	}
	public String getP4Position() {
		return this.p4Position ;
	}

	/**
	 * 第5名方位
	 */
	@Column(name="P5_POSITION_")
	private String p5Position;
	public void setP5Position(String p5Position) {
		this.p5Position=p5Position;
	}
	public void setP5Position(Object p5Position) {
		if (p5Position != null) {
			this.p5Position = p5Position.toString();
		}
	}
	public String getP5Position() {
		return this.p5Position ;
	}

	/**
	 * 第6名方位
	 */
	@Column(name="P6_POSITION_")
	private String p6Position;
	public void setP6Position(String p6Position) {
		this.p6Position=p6Position;
	}
	public void setP6Position(Object p6Position) {
		if (p6Position != null) {
			this.p6Position = p6Position.toString();
		}
	}
	public String getP6Position() {
		return this.p6Position ;
	}

	/**
	 * 第7名方位
	 */
	@Column(name="P7_POSITION_")
	private String p7Position;
	public void setP7Position(String p7Position) {
		this.p7Position=p7Position;
	}
	public void setP7Position(Object p7Position) {
		if (p7Position != null) {
			this.p7Position = p7Position.toString();
		}
	}
	public String getP7Position() {
		return this.p7Position ;
	}

	/**
	 * 第8名方位
	 */
	@Column(name="P8_POSITION_")
	private String p8Position;
	public void setP8Position(String p8Position) {
		this.p8Position=p8Position;
	}
	public void setP8Position(Object p8Position) {
		if (p8Position != null) {
			this.p8Position = p8Position.toString();
		}
	}
	public String getP8Position() {
		return this.p8Position ;
	}

	/**
	 * 第1名方位_英文
	 */
	@Column(name="P1_POSITION_EN_")
	private String p1PositionEn;
	public void setP1PositionEn(String p1PositionEn) {
		this.p1PositionEn=p1PositionEn;
	}
	public void setP1PositionEn(Object p1PositionEn) {
		if (p1PositionEn != null) {
			this.p1PositionEn = p1PositionEn.toString();
		}
	}
	public String getP1PositionEn() {
		return this.p1PositionEn ;
	}

	/**
	 * 第2名方位_英文
	 */
	@Column(name="P2_POSITION_EN_")
	private String p2PositionEn;
	public void setP2PositionEn(String p2PositionEn) {
		this.p2PositionEn=p2PositionEn;
	}
	public void setP2PositionEn(Object p2PositionEn) {
		if (p2PositionEn != null) {
			this.p2PositionEn = p2PositionEn.toString();
		}
	}
	public String getP2PositionEn() {
		return this.p2PositionEn ;
	}

	/**
	 * 第3名方位_英文
	 */
	@Column(name="P3_POSITION_EN_")
	private String p3PositionEn;
	public void setP3PositionEn(String p3PositionEn) {
		this.p3PositionEn=p3PositionEn;
	}
	public void setP3PositionEn(Object p3PositionEn) {
		if (p3PositionEn != null) {
			this.p3PositionEn = p3PositionEn.toString();
		}
	}
	public String getP3PositionEn() {
		return this.p3PositionEn ;
	}

	/**
	 * 第4名方位_英文
	 */
	@Column(name="P4_POSITION_EN_")
	private String p4PositionEn;
	public void setP4PositionEn(String p4PositionEn) {
		this.p4PositionEn=p4PositionEn;
	}
	public void setP4PositionEn(Object p4PositionEn) {
		if (p4PositionEn != null) {
			this.p4PositionEn = p4PositionEn.toString();
		}
	}
	public String getP4PositionEn() {
		return this.p4PositionEn ;
	}

	/**
	 * 第5名方位_英文
	 */
	@Column(name="P5_POSITION_EN_")
	private String p5PositionEn;
	public void setP5PositionEn(String p5PositionEn) {
		this.p5PositionEn=p5PositionEn;
	}
	public void setP5PositionEn(Object p5PositionEn) {
		if (p5PositionEn != null) {
			this.p5PositionEn = p5PositionEn.toString();
		}
	}
	public String getP5PositionEn() {
		return this.p5PositionEn ;
	}

	/**
	 * 第6名方位_英文
	 */
	@Column(name="P6_POSITION_EN_")
	private String p6PositionEn;
	public void setP6PositionEn(String p6PositionEn) {
		this.p6PositionEn=p6PositionEn;
	}
	public void setP6PositionEn(Object p6PositionEn) {
		if (p6PositionEn != null) {
			this.p6PositionEn = p6PositionEn.toString();
		}
	}
	public String getP6PositionEn() {
		return this.p6PositionEn ;
	}

	/**
	 * 第7名方位_英文
	 */
	@Column(name="P7_POSITION_EN_")
	private String p7PositionEn;
	public void setP7PositionEn(String p7PositionEn) {
		this.p7PositionEn=p7PositionEn;
	}
	public void setP7PositionEn(Object p7PositionEn) {
		if (p7PositionEn != null) {
			this.p7PositionEn = p7PositionEn.toString();
		}
	}
	public String getP7PositionEn() {
		return this.p7PositionEn ;
	}

	/**
	 * 第8名方位_英文
	 */
	@Column(name="P8_POSITION_EN_")
	private String p8PositionEn;
	public void setP8PositionEn(String p8PositionEn) {
		this.p8PositionEn=p8PositionEn;
	}
	public void setP8PositionEn(Object p8PositionEn) {
		if (p8PositionEn != null) {
			this.p8PositionEn = p8PositionEn.toString();
		}
	}
	public String getP8PositionEn() {
		return this.p8PositionEn ;
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
	 * 总和尾大小
	 */
	@Column(name="TOTAL_TAIL_SIZE_")
	private String totalTailSize;
	public void setTotalTailSize(String totalTailSize) {
		this.totalTailSize=totalTailSize;
	}
	public void setTotalTailSize(Object totalTailSize) {
		if (totalTailSize != null) {
			this.totalTailSize = totalTailSize.toString();
		}
	}
	public String getTotalTailSize() {
		return this.totalTailSize ;
	}

	/**
	 * 总和尾大小_英文
	 */
	@Column(name="TOTAL_TAIL_SIZE_EN_")
	private String totalTailSizeEn;
	public void setTotalTailSizeEn(String totalTailSizeEn) {
		this.totalTailSizeEn=totalTailSizeEn;
	}
	public void setTotalTailSizeEn(Object totalTailSizeEn) {
		if (totalTailSizeEn != null) {
			this.totalTailSizeEn = totalTailSizeEn.toString();
		}
	}
	public String getTotalTailSizeEn() {
		return this.totalTailSizeEn ;
	}

	/**
	 * 总和龙虎
	 */
	@Column(name="TOTAL_DRAGON_")
	private String totalDragon;
	public void setTotalDragon(String totalDragon) {
		this.totalDragon=totalDragon;
	}
	public void setTotalDragon(Object totalDragon) {
		if (totalDragon != null) {
			this.totalDragon = totalDragon.toString();
		}
	}
	public String getTotalDragon() {
		return this.totalDragon ;
	}

	/**
	 * 总和龙虎_英文
	 */
	@Column(name="TOTAL_DRAGON_EN_")
	private String totalDragonEn;
	public void setTotalDragonEn(String totalDragonEn) {
		this.totalDragonEn=totalDragonEn;
	}
	public void setTotalDragonEn(Object totalDragonEn) {
		if (totalDragonEn != null) {
			this.totalDragonEn = totalDragonEn.toString();
		}
	}
	public String getTotalDragonEn() {
		return this.totalDragonEn ;
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
	 * 创建时间数字型
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
	 * 更新时间数字型
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