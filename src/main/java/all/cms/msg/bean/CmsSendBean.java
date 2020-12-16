package all.cms.msg.bean;
import java.util.Date;
public class CmsSendBean {
	// 扩展表名EXT_TABLE_NAME_
	private String extTableName;
	// 扩展表的主键EXT_TABLE_ID_
	private String extTableId;
	// 扩展表描述EXT_TABLE_DESC_
	private String extTableDesc;
	// 查看地址
	private String urlView;
	private String type;
	private Date date;
	private String title;
	private String content;
	private String sendUser;
	private String receiveUserList;
	private String topicTableName;
	private String msgTableName;
	public String getExtTableName() {
		return extTableName;
	}
	public void setExtTableName(String extTableName) {
		this.extTableName = extTableName;
	}
	public String getExtTableId() {
		return extTableId;
	}
	public void setExtTableId(String extTableId) {
		this.extTableId = extTableId;
	}
	public String getExtTableDesc() {
		return extTableDesc;
	}
	public void setExtTableDesc(String extTableDesc) {
		this.extTableDesc = extTableDesc;
	}
	public String getUrlView() {
		return urlView;
	}
	public void setUrlView(String urlView) {
		this.urlView = urlView;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getReceiveUserList() {
		return receiveUserList;
	}
	public void setReceiveUserList(String receiveUserList) {
		this.receiveUserList = receiveUserList;
	}
	public String getTopicTableName() {
		return topicTableName;
	}
	public void setTopicTableName(String topicTableName) {
		this.topicTableName = topicTableName;
	}
	public String getMsgTableName() {
		return msgTableName;
	}
	public void setMsgTableName(String msgTableName) {
		this.msgTableName = msgTableName;
	}
	
	
}
