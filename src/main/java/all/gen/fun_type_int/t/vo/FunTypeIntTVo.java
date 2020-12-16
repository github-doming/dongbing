package all.gen.fun_type_int.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table fun_type_int 
 * vo类
 */

public class FunTypeIntTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//ID
	private String id;
	public void setId(String id) {
		this.id=id;
	}
	public String getId() {
		return this.id ;
	}
	
//名称
	private String name;
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return this.name ;
	}
	
//DATE
	private String d;
	public void setD(String d) {
		this.d=d;
	}
	public String getD() {
		return this.d ;
	}
	
//DATETIME
	private String dt;
	public void setDt(String dt) {
		this.dt=dt;
	}
	public String getDt() {
		return this.dt ;
	}
	
//TIME
	private String t;
	public void setT(String t) {
		this.t=t;
	}
	public String getT() {
		return this.t ;
	}
	
//TIMESTAMP
	private String ts;
	public void setTs(String ts) {
		this.ts=ts;
	}
	public String getTs() {
		return this.ts ;
	}
	
//C_DECIMAL
	private String cDecimal;
	public void setCDecimal(String cDecimal) {
		this.cDecimal=cDecimal;
	}
	public String getCDecimal() {
		return this.cDecimal ;
	}
	
//C_NUMERIC
	private String cNumeric;
	public void setCNumeric(String cNumeric) {
		this.cNumeric=cNumeric;
	}
	public String getCNumeric() {
		return this.cNumeric ;
	}
	
//删除标志
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}


}