package c.a.util.mq.common;
import javax.jms.Destination;
import javax.jms.TextMessage;
import java.util.Enumeration;
public class MQTextMessage implements TextMessage {
	protected String text;
	public String getText()  {
		return this.text;
	}
	public void setText(String textInput)  {
		this.text = textInput;
	}
	public String getJMSMessageID()  {
		return null;
	}
	public void setJMSMessageID(String id)  {

	}
	public long getJMSTimestamp()  {
		return 0;
	}
	public void setJMSTimestamp(long timestamp)  {

	}
	public byte[] getJMSCorrelationIDAsBytes()  {
		return new byte[0];
	}
	public void setJMSCorrelationIDAsBytes(byte[] correlationID)  {

	}
	public void setJMSCorrelationID(String correlationID)  {

	}
	public String getJMSCorrelationID()  {
		return null;
	}
	public Destination getJMSReplyTo()  {
		return null;
	}
	public void setJMSReplyTo(Destination replyTo)  {

	}
	public Destination getJMSDestination()  {
		return null;
	}
	public void setJMSDestination(Destination destination)  {

	}
	public int getJMSDeliveryMode()  {
		return 0;
	}
	public void setJMSDeliveryMode(int deliveryMode)  {

	}
	public boolean getJMSRedelivered()  {
		return false;
	}
	public void setJMSRedelivered(boolean redelivered)  {

	}
	public String getJMSType()  {
		return null;
	}
	public void setJMSType(String type)  {

	}
	public long getJMSExpiration()  {
		return 0;
	}
	public void setJMSExpiration(long expiration)  {

	}
	public long getJMSDeliveryTime() {
		return 0;
	}
	public void setJMSDeliveryTime(long deliveryTime)  {

	}
	public int getJMSPriority()  {
		return 0;
	}
	public void setJMSPriority(int priority)  {

	}
	public void clearProperties()  {

	}
	public boolean propertyExists(String name)  {
		return false;
	}
	public boolean getBooleanProperty(String name)  {
		return false;
	}
	public byte getByteProperty(String name)  {
		return 0;
	}
	public short getShortProperty(String name)  {
		return 0;
	}
	public int getIntProperty(String name)  {
		return 0;
	}
	public long getLongProperty(String name)  {
		return 0;
	}
	public float getFloatProperty(String name)  {
		return 0;
	}
	public double getDoubleProperty(String name)  {
		return 0;
	}
	public String getStringProperty(String name)  {
		return null;
	}
	public Object getObjectProperty(String name)  {
		return null;
	}
	public Enumeration getPropertyNames()  {
		return null;
	}
	public void setBooleanProperty(String name, boolean value)  {

	}
	public void setByteProperty(String name, byte value)  {

	}
	public void setShortProperty(String name, short value)  {

	}
	public void setIntProperty(String name, int value)  {

	}
	public void setLongProperty(String name, long value)  {

	}
	public void setFloatProperty(String name, float value)  {

	}
	public void setDoubleProperty(String name, double value)  {

	}
	public void setStringProperty(String name, String value)  {

	}
	public void setObjectProperty(String name, Object value)  {

	}
	public void acknowledge()  {

	}
	public void clearBody()  {

	}
	public <T> T getBody(Class<T> c) {
		return null;
	}
	public boolean isBodyAssignableTo(Class c) {
		return false;
	}
}
