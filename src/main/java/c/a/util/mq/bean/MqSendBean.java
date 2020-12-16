package c.a.util.mq.bean;
import java.util.ArrayList;
import java.util.List;
public class MqSendBean {
	private String text = null;

	private List<MqSendProperty> propertyList = new ArrayList<MqSendProperty>();
	// -- 下面的方法不再更新 --//

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<MqSendProperty> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<MqSendProperty> propertyList) {
		this.propertyList = propertyList;
	}

	
	// -- 上面的方法不再更新 --//
}
