package all.gen.ga_gameplay.c.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the database table ga_gameplay 
 * 游戏系统_玩法 GA_GAMEPLAY  如：特号，龙虎，大小，单双,冠亚号+位置  (171种玩法)的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "ga_gameplay")
public class GaGameplayT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//索引
@Column(name="IDX_")
	private Long idx;
	
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//游戏玩法主键 GA_GAMEPLAY_ID_
@Column(name="GA_GAMEPLAY_ID_")
	private String gaGameplayId;
	
	public void setGaGameplayId(String gaGameplayId) {
		this.gaGameplayId=gaGameplayId;
	}
	public String getGaGameplayId() {
		return this.gaGameplayId ;
	}
			
			
//玩法名称 GAMEPLAY_NAME_
@Column(name="GAMEPLAY_NAME_")
	private String gameplayName;
	
	public void setGameplayName(String gameplayName) {
		this.gameplayName=gameplayName;
	}
	public String getGameplayName() {
		return this.gameplayName ;
	}
			
			
//玩法编码GAMEPLAY_CODE_
@Column(name="GAMEPLAY_CODE_")
	private String gameplayCode;
	
	public void setGameplayCode(String gameplayCode) {
		this.gameplayCode=gameplayCode;
	}
	public String getGameplayCode() {
		return this.gameplayCode ;
	}
			
			
//游戏主键GA_GAME_ID_
@Column(name="GA_GAME_ID_")
	private String gaGameId;
	
	public void setGaGameId(String gaGameId) {
		this.gaGameId=gaGameId;
	}
	public String getGaGameId() {
		return this.gaGameId ;
	}
			
			
//游戏系统_玩法类型(分类)主键GA_GAMEPLAY_TREE_ID_
@Column(name="GA_GAMEPLAY_TREE_ID_")
	private String gaGameplayTreeId;
	
	public void setGaGameplayTreeId(String gaGameplayTreeId) {
		this.gaGameplayTreeId=gaGameplayTreeId;
	}
	public String getGaGameplayTreeId() {
		return this.gaGameplayTreeId ;
	}
			
			
//玩法类型编码GAMEPLAY_TREE_CODE_
@Column(name="GAMEPLAY_TREE_CODE_")
	private String gameplayTreeCode;
	
	public void setGameplayTreeCode(String gameplayTreeCode) {
		this.gameplayTreeCode=gameplayTreeCode;
	}
	public String getGameplayTreeCode() {
		return this.gameplayTreeCode ;
	}
			
			
//游戏系统下注位置主键 GA_BET_PLACE_ID_
@Column(name="GA_BET_PLACE_ID_")
	private String gaBetPlaceId;
	
	public void setGaBetPlaceId(String gaBetPlaceId) {
		this.gaBetPlaceId=gaBetPlaceId;
	}
	public String getGaBetPlaceId() {
		return this.gaBetPlaceId ;
	}
			
			
//位置编码 PLACE_CODE_
@Column(name="PLACE_CODE_")
	private String placeCode;
	
	public void setPlaceCode(String placeCode) {
		this.placeCode=placeCode;
	}
	public String getPlaceCode() {
		return this.placeCode ;
	}
			
			
//游戏投注范围主键 GA_BET_RANGE_ID_
@Column(name="GA_BET_RANGE_ID_")
	private String gaBetRangeId;
	
	public void setGaBetRangeId(String gaBetRangeId) {
		this.gaBetRangeId=gaBetRangeId;
	}
	public String getGaBetRangeId() {
		return this.gaBetRangeId ;
	}
			
			
//范围编码RANGE_CODE_
@Column(name="RANGE_CODE_")
	private String rangeCode;
	
	public void setRangeCode(String rangeCode) {
		this.rangeCode=rangeCode;
	}
	public String getRangeCode() {
		return this.rangeCode ;
	}
			
			
//默认赔率ODDS_DEF_
@Column(name="ODDS_DEF_")
	private BigDecimal oddsDef;
	
	public void setOddsDef(BigDecimal oddsDef) {
		this.oddsDef=oddsDef;
	}
	public BigDecimal getOddsDef() {
		return this.oddsDef ;
	}
			
			
//玩家单注默认最低_PLAYER_BET_MIN_DEF_
@Column(name="PLAYER_BET_MIN_DEF_")
	private Integer playerBetMinDef;
	
	public void setPlayerBetMinDef(Integer playerBetMinDef) {
		this.playerBetMinDef=playerBetMinDef;
	}
	public Integer getPlayerBetMinDef() {
		return this.playerBetMinDef ;
	}
			
			
//玩家单注默认最高PLAYER_BET_MAX_DEF_
@Column(name="PLAYER_BET_MAX_DEF_")
	private Integer playerBetMaxDef;
	
	public void setPlayerBetMaxDef(Integer playerBetMaxDef) {
		this.playerBetMaxDef=playerBetMaxDef;
	}
	public Integer getPlayerBetMaxDef() {
		return this.playerBetMaxDef ;
	}
			
			
//房间默认最高ROOM_BET_MAX_DEF_
@Column(name="ROOM_BET_MAX_DEF_")
	private Integer roomBetMaxDef;
	
	public void setRoomBetMaxDef(Integer roomBetMaxDef) {
		this.roomBetMaxDef=roomBetMaxDef;
	}
	public Integer getRoomBetMaxDef() {
		return this.roomBetMaxDef ;
	}
			
			
//单项默认最高ITEM_BET_MAX_DEF_
@Column(name="ITEM_BET_MAX_DEF_")
	private Integer itemBetMaxDef;
	
	public void setItemBetMaxDef(Integer itemBetMaxDef) {
		this.itemBetMaxDef=itemBetMaxDef;
	}
	public Integer getItemBetMaxDef() {
		return this.itemBetMaxDef ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
			
//更新时间
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
			
			
//描述
@Column(name="DESC_")
	private String desc;
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

}