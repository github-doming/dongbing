package c.a.util.mq.rabbitmq;
import java.io.IOException;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import c.a.config.SysConfig;
import c.a.tools.mvc.exception.BizRuntimeException;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import c.a.util.mq.bean.MqClient;
import c.a.util.mq.bean.MqConfig;
import c.a.util.mq.bean.MqExchange;
import c.a.util.mq.bean.MqQueue;
import c.a.util.mq.bean.MqRouting;
import c.a.util.mq.bean.MqSendBean;
import c.a.util.mq.common.CommMq;
import c.a.util.mq.common.MQTextMessage;
import c.a.util.mq.common.MqXmlUtil;
/**
 * 
 * rabbitmq工具类
 * 
 * @Description:
 * @ClassName:
 * @date 2012年12月02日 上午11:21:51
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class RabbitMqUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private String logStr = null;
	// public static String configPath = "/config/mq/rabbitmq/rabbitmq.xml";
	private static MqConfig config = null;
	private static ConnectionFactory factory = null;
	private Connection connection = null;
	// 单例
	private volatile static RabbitMqUtil instance = null;
	private final static Object key = new Object();
	// 构造函数
	private RabbitMqUtil() {
	}
	public static RabbitMqUtil findInstance() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new RabbitMqUtil();
					// 初始化
					init();
				}
			}
		}
		return instance;
	}
	/**
	 * 初始化
	 * 
	 * @Title: init
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	private static void init() throws Exception {
		String configPath = SysConfig.findInstance().findMap().get("rabbitmq.local.xml").toString();
		String machine = SysConfig.findInstance().findMap().get("comm.local.machine").toString();
		machine = "[" + machine + "]";
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		MqXmlUtil xml = new MqXmlUtil();
		xml.build(pathUtil.findPath(configPath));
		config = xml.findMqConfig();
		// 定义连接工厂
		factory = new ConnectionFactory();
		// 设置服务地址
		factory.setHost(config.getIp());
		// 端口
		factory.setPort(Integer.parseInt(config.getPort()));
		// 设置账号信息，用户名、密码、vhost
		// 虚拟主机
		factory.setVirtualHost(config.getVirtualHost());
		// com.rabbitmq.client.AuthenticationFailureException: ACCESS_REFUSED -
		// Login was refused using authentication mechanism PLAIN. For details
		// see the broker logfile.
		factory.setUsername(config.getUserName());
		factory.setPassword(config.getPassword());
		// 通过工程获取连接
		config.setMachine(machine);
	}
	public Connection createConnection() throws Exception {
		connection = factory.newConnection();
		return connection;
	}
	public void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (IOException e) {
			log.error("系统异常:",e);
		}
	}
	/**
	 * 简单队列;
	 * 
	 * 一个生产者P发送消息到队列Q,一个消费者C接收;
	 * 
	 * @Title: sendQueueSimple
	 * @Description:
	 *
	 * 				参数说明
	 * @param queueCustomName
	 * @param connection
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendQueueSimple(String queueCustomName, Connection connection, MqSendBean bean) throws Exception {
		String queueFormat = config.getQueueFormat();
		String queueName = String.format(queueFormat, queueCustomName);
		// 获取通道
		Channel channel = connection.createChannel();
		// 声明队列
		channel.queueDeclare(queueName, true, false, false, null);
		String message = bean.getText();
		// 发送消息
		channel.basicPublish("", queueName, null, message.getBytes());
		// 关闭连接
		channel.close();
		this.closeConnection(connection);
	}
	/**
	 * 简单队列;
	 * 
	 * 一个生产者P发送消息到队列Q,一个消费者C接收;
	 * 
	 * @Title: sendQueueSimple
	 * @Description:
	 *
	 * 				参数说明
	 * @param queueCustomName
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendQueueSimple(String queueCustomName, MqSendBean bean) throws Exception {
		this.sendQueueSimple(queueCustomName, this.createConnection(), bean);
	}
	public void receiveQueueSimple(String queueCustomName, Connection connection) throws Exception {
		String queueFormat = config.getQueueFormat();
		// boolean durable 为true表示数据持久化，消息列队服务挂的时候数据不会丢失，连上的时候会从本地读取
		boolean durable = true;
		List<MqQueue> queueList = config.getQueueList();
		for (MqQueue mqQueue : queueList) {
			if (mqQueue.getName().equals(queueCustomName)) {
			} else {
				continue;
			}
			List<MqClient> clientList = mqQueue.getClientList();
			for (MqClient client : clientList) {
				Channel channel = connection.createChannel();
				String queueName = String.format(queueFormat, mqQueue.getName());
				String className = client.getClassName();
				channel.queueDeclare(queueName, durable, false, false, null);
				// 创建消费者
				Consumer consumer = new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
							byte[] body) throws IOException {
						String message = new String(body, "UTF-8");
						System.out.println("message =" + message);
						// try {
						// Thread.sleep(1000);
						// } catch (InterruptedException e) {
						//
						// e.printStackTrace();
						// }
						Class classObj = null;
						try {
							// log.trace("mqQueue.getClassName()=" +
							// className);
							classObj = Class.forName(className);
						} catch (Exception e) {
							logStr = "找不到类异常，加载MQ类出错,出错的MQ=" + className;
							log.error(logStr, e);
							return;
						}
						CommMq commMq = null;
						try {
							commMq = (CommMq) classObj.newInstance();
						} catch (InstantiationException e) {
							logStr = "实例化异常，实例化MQ类出错,出错的MQ=" + className;
							log.error(logStr, e);
							e.printStackTrace();
							return;
						} catch (IllegalAccessException e) {
							logStr = "不合法访问异常，实例化MQ类出错,出错的MQ=" + className;
							log.error(logStr, e);
							return;
						}
						// 初始化参数
						TextMessage textMessage = new MQTextMessage();
						try {
							textMessage.setText(message);
						} catch (JMSException e) {
							e.printStackTrace();
							log.error(e);
						}
						// 不要用反射,因为反射不能与业务异常bizException绑定起来
						if (true) {
							try {
								commMq.execute(textMessage);
							} catch (BizRuntimeException e1) {
								String logStr = "BizRuntimeException，业务出错,出错的MQ=" + className;
								log.error(logStr, e1);
								e1.printStackTrace();
								// 必须重新抛出异常给系统才能跳转
								throw e1;
							} catch (RuntimeException e2) {
								String logStr = "运行中RuntimeException，业务出错,出错的MQ=" + className;
								log.error(logStr, e2);
								e2.printStackTrace();
								// 必须重新抛出异常给系统才能跳转
								throw e2;
							} catch (Throwable t) {
								String logStr = "Throwable异常，业务出错,出错的MQ=" + className;
								log.error(logStr, t);
								t.printStackTrace();
								return;
							}
						}
					}
				};
				// basicConsume的第二个属性为true表示消息一旦被消费者接受消息列队里的消息就会被自动删除
				channel.basicConsume(queueName, true, consumer);
				// 客户端只能1个
				break;
			}
		}
	}
	/**
	 * 简单队列;
	 * 
	 * 一个生产者P发送消息到队列Q,一个消费者C接收;
	 * 
	 * 不要用junit来测试;
	 * 
	 * @Title: receiveSimple
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveQueueSimple(String queueCustomName) throws Exception {
		this.receiveQueueSimple(queueCustomName, this.createConnection());
	}
	/**
	 * 工作队列;
	 * 
	 * 一个生产者，多个消费者，每个消费者获取到的消息唯一，多个消费者只有一个队列;
	 * 
	 * @Title: sendQueueWork
	 * @Description:
	 *
	 * 				参数说明
	 * @param queueCustomName
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendQueueWork(String queueCustomName, MqSendBean bean) throws Exception {
		this.sendQueueSimple(queueCustomName, this.createConnection(), bean);
	}
	/**
	 * 
	 * 工作队列;
	 * 
	 * 一个生产者，多个消费者，每个消费者获取到的消息唯一，多个消费者只有一个队列;
	 * 
	 * 不要用junit来测试;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param connection
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveQueueWork(String queueCustomName, Connection connection) throws Exception {
		String queueFormat = config.getQueueFormat();
		// boolean durable 为true表示数据持久化，消息列队服务挂的时候数据不会丢失，连上的时候会从本地读取
		boolean durable = true;
		List<MqQueue> queueList = config.getQueueList();
		for (MqQueue mqQueue : queueList) {
			if (mqQueue.getName().equals(queueCustomName)) {
			} else {
				continue;
			}
			List<MqClient> clientList = mqQueue.getClientList();
			for (MqClient client : clientList) {
				Channel channel = connection.createChannel();
				String queueName = String.format(queueFormat, mqQueue.getName());
				String className = client.getClassName();
				channel.queueDeclare(queueName, durable, false, false, null);
				// 每个消费者发送确认消息之前，消息列队不发送下一个消息到消费者，一次只能处理一个消息
				// 限制消息列队发送给同一个消费者一次不得超过一个消息
				channel.basicQos(1);
				Consumer consumer = new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
							byte[] body) throws IOException {
						String message = new String(body, "UTF-8");
						Class classObj = null;
						try {
							// log.trace("mqQueue.getClassName()=" +
							// className);
							classObj = Class.forName(className);
						} catch (Exception e) {
							logStr = "找不到类异常，加载MQ类出错,出错的MQ=" + className;
							log.error(logStr, e);
							return;
						}
						CommMq commMq = null;
						try {
							commMq = (CommMq) classObj.newInstance();
						} catch (InstantiationException e) {
							logStr = "实例化异常，实例化MQ类出错,出错的MQ=" + className;
							log.error(logStr, e);
							e.printStackTrace();
							return;
						} catch (IllegalAccessException e) {
							logStr = "不合法访问异常，实例化MQ类出错,出错的MQ=" + className;
							log.error(logStr, e);
							return;
						}
						// 初始化参数
						TextMessage textMessage = new MQTextMessage();
						try {
							textMessage.setText(message);
						} catch (JMSException e) {
							e.printStackTrace();
							log.error(e);
						}
						// 不要用反射,因为反射不能与业务异常bizException绑定起来
						if (true) {
							try {
								commMq.execute(textMessage);
							} catch (BizRuntimeException e1) {
								String logStr = "BizRuntimeException，业务出错,出错的MQ=" + className;
								log.error(logStr, e1);
								e1.printStackTrace();
								// 必须重新抛出异常给系统才能跳转
								throw e1;
							} catch (RuntimeException e2) {
								String logStr = "运行中RuntimeException，业务出错,出错的MQ=" + className;
								log.error(logStr, e2);
								e2.printStackTrace();
								// 必须重新抛出异常给系统才能跳转
								throw e2;
							} catch (Throwable t) {
								String logStr = "Throwable异常，业务出错,出错的MQ=" + className;
								log.error(logStr, t);
								t.printStackTrace();
								return;
							}
						}
						// 手动回执
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
				};
				// 消息确认模式（false手动确认需要basicAck手动回执，true为自动模式，消费者一旦接受消息mq中的消息就会自动删除）
				boolean ack = false;
				channel.basicConsume(queueName, ack, consumer);
			}
		}
	}
	/**
	 * 工作队列;
	 * 
	 * 一个生产者，多个消费者，每个消费者获取到的消息唯一，多个消费者只有一个队列;
	 * 
	 * Round-robin（轮询分发）;
	 * 
	 * Fair dispatch（公平分发）;
	 * 
	 * 不要用junit来测试;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveQueueWork(String queueCustomName) throws Exception {
		this.receiveQueueWork(queueCustomName, this.createConnection());
	}
	/**
	 * 发布/订阅模式 Publish/Subscribe ;
	 * 
	 * 功能：一个生产者发送的消息会被多个消费者获取。一个生产者、一个交换机、多个队列、多个消费者;
	 * 
	 * @Title: sendExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param connection
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendExchangeTopic(String exchangeCustomName, Connection connection, MqSendBean bean) throws Exception {
		String exchangeFormat = config.getExchangeFormat();
		String exchangeName = String.format(exchangeFormat, exchangeCustomName);
		String queueFormat = config.getQueueFormat();
		// 生成访问通道
		Channel channel = connection.createChannel();
		// 通过通道声明交换机
		channel.exchangeDeclare(exchangeName, "topic");
		List<MqExchange> exchangeList = config.getExchangeList();
		for (MqExchange mqExchange : exchangeList) {
			if (exchangeCustomName.equals(mqExchange.getName())) {
				List<MqQueue> queueList = mqExchange.getQueueList();
				for (MqQueue mqQueue : queueList) {
					String queueNameCustom = mqQueue.getName();
					// 队列名称
					String queueName = String.format(queueFormat, queueNameCustom);
					// 声明队列
					channel.queueDeclare(queueName, true, false, false, null);
					// 绑定消息列队和交换机
					channel.queueBind(queueName, exchangeName, "");
				}
				break;
			}
		}
		// 消息内容
		String message = bean.getText();
		// 通过交换机发送
		channel.basicPublish(exchangeName, "", null, message.getBytes());
		channel.close();
		connection.close();
	}
	/**
	 * 发布/订阅模式 Publish/Subscribe ;
	 * 
	 * 功能：一个生产者发送的消息会被多个消费者获取。一个生产者、一个交换机、多个队列、多个消费者;
	 * 
	 * @Title: sendExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendExchangeTopic(String exchangeCustomName, MqSendBean bean) throws Exception {
		this.sendExchangeTopic(exchangeCustomName, this.createConnection(), bean);
	}
	/**
	 * 发布/订阅模式 Publish/Subscribe ;
	 * 
	 * 功能：一个生产者发送的消息会被多个消费者获取。一个生产者、一个交换机、多个队列、多个消费者;
	 * 
	 * @Title: receiveExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param connection
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveExchange(String exchangeCustomName, String queueCustomName, Connection connection)
			throws Exception {
		String machine = config.getMachine();
		String exchangeFormat = config.getExchangeFormat();
		String exchangeName = machine + String.format(exchangeFormat, exchangeCustomName);
		String queueFormat = config.getQueueFormat();
		// boolean durable 为true表示数据持久化，消息列队服务挂的时候数据不会丢失，连上的时候会从本地读取
		boolean durable = true;
		Channel channel = connection.createChannel();
		List<MqExchange> exchangeList = config.getExchangeList();
		for (MqExchange mqExchange : exchangeList) {
			if (exchangeCustomName.equals(mqExchange.getName())) {
				List<MqQueue> queueList = mqExchange.getQueueList();
				for (MqQueue mqQueue : queueList) {
					if (mqQueue.getName().equals(queueCustomName)) {
					} else {
						continue;
					}
					List<MqClient> clientList = mqQueue.getClientList();
					for (MqClient client : clientList) {
						// Channel channel = connection.createChannel();
						String queueName = machine + String.format(queueFormat, mqQueue.getName());
						// synchronized (key) {
						// log.trace("client className=" +
						// client.getClassName());
						// log.trace("queueName=" + queueName);
						// }
						String className = client.getClassName();
						// 声明队列
						channel.queueDeclare(queueName, durable, false, false, null);
						// 每个消费者发送确认消息之前，消息列队不发送下一个消息到消费者，一次只能处理一个消息
						// 限制消息列队发送给同一个消费者一次不得超过一个消息
						channel.basicQos(1);
						Consumer consumer = new DefaultConsumer(channel) {
							@Override
							public void handleDelivery(String consumerTag, Envelope envelope,
									AMQP.BasicProperties properties, byte[] body) throws IOException {
								String message = new String(body, "UTF-8");
								//System.out.print(" class=" + this.getClass().getName());
								//System.out.println(" message=" + message);
								Class classObj = null;
								try {
									// log.trace("mqQueue.getClassName()="
									// +
									// className);
									classObj = Class.forName(className);
								} catch (Exception e) {
									logStr = "找不到类异常，加载MQ类出错,出错的MQ=" + className;
									log.error(logStr, e);
									return;
								}
								CommMq commMq = null;
								try {
									commMq = (CommMq) classObj.newInstance();
								} catch (InstantiationException e) {
									logStr = "实例化异常，实例化MQ类出错,出错的MQ=" + className;
									log.error(logStr, e);
									e.printStackTrace();
									return;
								} catch (IllegalAccessException e) {
									logStr = "不合法访问异常，实例化MQ类出错,出错的MQ=" + className;
									log.error(logStr, e);

									return;
								}
								// 初始化参数
								TextMessage textMessage = new MQTextMessage();
								try {
									textMessage.setText(message);
								} catch (JMSException e) {
									e.printStackTrace();
									log.error(e);
								}
								// 不要用反射,因为反射不能与业务异常bizException绑定起来
								if (true) {
									try {
										commMq.onMessage(textMessage);
									} catch (BizRuntimeException e1) {
										String logStr = "BizRuntimeException，业务出错,出错的MQ=" + className;
										log.error(logStr, e1);
										e1.printStackTrace();
										// 必须重新抛出异常给系统才能跳转
										throw e1;
									} catch (RuntimeException e2) {
										String logStr = "运行中RuntimeException，业务出错,出错的MQ=" + className;
										log.error(logStr, e2);
										e2.printStackTrace();
										// 必须重新抛出异常给系统才能跳转
										throw e2;
									} catch (Throwable t) {
										String logStr = "Throwable异常，业务出错,出错的MQ=" + className;
										log.error(logStr, t);
										t.printStackTrace();
										return;
									}
								}
								// 手动回执
								channel.basicAck(envelope.getDeliveryTag(), false);
							}
						};
						// 消息确认模式（false手动确认需要basicAck手动回执，true为自动模式，消费者一旦接受消息mq中的消息就会自动删除）
						boolean ack = false;
						channel.basicConsume(queueName, ack, consumer);
					}
				}
				break;
			}
		}
	}
	/**
	 * 发布/订阅模式 Publish/Subscribe ;
	 * 
	 * 功能：一个生产者发送的消息会被多个消费者获取。一个生产者、一个交换机、多个队列、多个消费者;
	 * 
	 * @Title: receiveExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveExchange(String exchangeCustomName, String queueCustomName) throws Exception {
		this.receiveExchange(exchangeCustomName, queueCustomName, this.createConnection());
	}
	/**
	 * 直接路由;
	 * 
	 * 生产者发送消息到交换机并且要指定路由key，消费者将队列绑定到交换机时需要指定路由key;
	 * 
	 * @Title: sendRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param routingKey
	 * @param connection
	 * @param bean
	 * @param routingType
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRouting(String exchangeCustomName, String routingKey, Connection connection, MqSendBean bean,
			String routingType) throws Exception {
		String exchangeFormat = config.getExchangeFormat();
		String exchangeName = String.format(exchangeFormat, exchangeCustomName);
		String queueFormat = config.getQueueFormat();
		// 生成访问通道
		Channel channel = connection.createChannel();
		// 通过通道声明交换机（"direct"表示使用路由标签Key）
		channel.exchangeDeclare(exchangeName, routingType);
		List<MqExchange> exchangeList = config.getExchangeList();
		for (MqExchange mqExchange : exchangeList) {
			if (exchangeCustomName.equals(mqExchange.getName())) {
				List<MqQueue> queueList = mqExchange.getQueueList();
				for (MqQueue mqQueue : queueList) {
					String queueNameCustom = mqQueue.getName();
					// 队列名称
					String queueName = String.format(queueFormat, queueNameCustom);
					// 声明队列
					channel.queueDeclare(queueName, true, false, false, null);
					List<MqRouting> rountingList = mqQueue.getRoutingList();
					for (MqRouting mqRouting : rountingList) {
						// 绑定消息列队和交换机
						channel.queueBind(queueName, exchangeName, mqRouting.getKey());
					}
				}
				break;
			}
		}
		// 消息内容
		String message = bean.getText();
		// 通过交换机发送
		channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
		channel.close();
		connection.close();
	}
	/**
	 * 直接路由;
	 * 
	 * 生产者发送消息到交换机并且要指定路由key，消费者将队列绑定到交换机时需要指定路由key;
	 * 
	 * 类型交换器 Direct;
	 * 
	 * 
	 * @Title: sendRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param routingKey
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRouting(String exchangeCustomName, String routingKey, MqSendBean bean) throws Exception {
		this.sendRouting(exchangeCustomName, routingKey, this.createConnection(), bean, "direct");
	}
	/**
	 * 直接路由;
	 * 
	 * 生产者发送消息到交换机并且要指定路由key，消费者将队列绑定到交换机时需要指定路由key;
	 * 
	 * @Title: receiveRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param connection
	 * @param routingType
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveRouting(String exchangeCustomName, Connection connection, String routingType) throws Exception {
		String exchangeFormat = config.getExchangeFormat();
		String exchangeName = String.format(exchangeFormat, exchangeCustomName);
		String queueFormat = config.getQueueFormat();
		// boolean durable 为true表示数据持久化，消息列队服务挂的时候数据不会丢失，连上的时候会从本地读取
		boolean durable = true;
		List<MqExchange> exchangeList = config.getExchangeList();
		Channel channel = connection.createChannel();
		// 通过通道声明交换机（"direct"表示使用路由标签Key）
		channel.exchangeDeclare(exchangeName, routingType);
		for (MqExchange mqExchange : exchangeList) {
			if (exchangeCustomName.equals(mqExchange.getName())) {
				// 声明一个随机队列
				String queueName = channel.queueDeclare().getQueue();
				// 所有日志严重性级别
				List<MqQueue> queueList = mqExchange.getQueueList();
				for (MqQueue mqQueue : queueList) {
					List<MqRouting> rountingList = mqQueue.getRoutingList();
					for (MqRouting mqRouting : rountingList) {
						// 关注所有级别的日志（多重绑定）
						channel.queueBind(queueName, exchangeName, mqRouting.getKey());
					}
				}
				break;
			}
		}
		for (MqExchange mqExchange : exchangeList) {
			if (exchangeCustomName.equals(mqExchange.getName())) {
				List<MqQueue> queueList = mqExchange.getQueueList();
				for (MqQueue mqQueue : queueList) {
					List<MqClient> clientList = mqQueue.getClientList();
					for (MqClient client : clientList) {
						// Channel channel = connection.createChannel();
						String queueName = String.format(queueFormat, mqQueue.getName());
						// synchronized (key) {
						// log.trace("client className=" +
						// client.getClassName());
						// log.trace("queueName=" + queueName);
						// }
						String className = client.getClassName();
						channel.queueDeclare(queueName, durable, false, false, null);
						// 每个消费者发送确认消息之前，消息列队不发送下一个消息到消费者，一次只能处理一个消息
						// 限制消息列队发送给同一个消费者一次不得超过一个消息
						channel.basicQos(1);
						Consumer consumer = new DefaultConsumer(channel) {
							@Override
							public void handleDelivery(String consumerTag, Envelope envelope,
									AMQP.BasicProperties properties, byte[] body) throws IOException {
								String message = new String(body, "UTF-8");
								Class classObj = null;
								try {
									// log.trace("mqQueue.getClassName()="
									// +
									// className);
									classObj = Class.forName(className);
								} catch (Exception e) {
									logStr = "找不到类异常，加载MQ类出错,出错的MQ=" + className;
									log.error(logStr, e);
									return;
								}
								CommMq commMq = null;
								try {
									commMq = (CommMq) classObj.newInstance();
								} catch (InstantiationException e) {
									logStr = "实例化异常，实例化MQ类出错,出错的MQ=" + className;
									log.error(logStr, e);
									e.printStackTrace();
									return;
								} catch (IllegalAccessException e) {
									logStr = "不合法访问异常，实例化MQ类出错,出错的MQ=" + className;
									log.error(logStr, e);
									return;
								}
								// 初始化参数
								TextMessage textMessage = new MQTextMessage();
								try {
									textMessage.setText(message);
								} catch (JMSException e) {
									e.printStackTrace();
									log.error(e);
								}
								// 不要用反射,因为反射不能与业务异常bizException绑定起来
								if (true) {
									try {
										commMq.execute(textMessage);
									} catch (BizRuntimeException e1) {
										String logStr = "BizRuntimeException，业务出错,出错的MQ=" + className;
										log.error(logStr, e1);
										e1.printStackTrace();
										// 必须重新抛出异常给系统才能跳转
										throw e1;
									} catch (RuntimeException e2) {
										String logStr = "运行中RuntimeException，业务出错,出错的MQ=" + className;
										log.error(logStr, e2);
										e2.printStackTrace();
										// 必须重新抛出异常给系统才能跳转
										throw e2;
									} catch (Throwable t) {
										String logStr = "Throwable异常，业务出错,出错的MQ=" + className;
										log.error(logStr, t);
										t.printStackTrace();
										return;
									}
								}
								// 手动回执
								channel.basicAck(envelope.getDeliveryTag(), false);
							}
						};
						// 消息确认模式（false手动确认需要basicAck手动回执，true为自动模式，消费者一旦接受消息mq中的消息就会自动删除）
						boolean ack = false;
						channel.basicConsume(queueName, ack, consumer);
					}
				}
				break;
			}
		}
	}
	/**
	 * 直接路由;
	 * 
	 * 生产者发送消息到交换机并且要指定路由key，消费者将队列绑定到交换机时需要指定路由key;
	 * 
	 * @Title: receiveRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveRouting(String exchangeCustomName) throws Exception {
		this.receiveRouting(exchangeCustomName, this.createConnection(), "direct");
	}
	/**
	 * 模糊路由,路由匹配;
	 * 
	 * 生产者P发送消息到交换机X，type=topic，交换机根据绑定队列的routing key的值进行通配符匹配;
	 * 
	 * 类型交换器 Topic;
	 * 
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param routingKey
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingFuzzy(String exchangeCustomName, String routingKey, MqSendBean bean) throws Exception {
		this.sendRouting(exchangeCustomName, routingKey, this.createConnection(), bean, "topic");
	}
	/**
	 * 模糊路由,路由匹配;
	 * 
	 * 生产者P发送消息到交换机X，type=topic，交换机根据绑定队列的routing key的值进行通配符匹配;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveRoutingFuzzy(String exchangeCustomName) throws Exception {
		this.receiveRouting(exchangeCustomName, this.createConnection(), "topic");
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
	public void sendQueueWorkByName(String queueCustomName, Connection connection, MqSendBean bean) throws Exception {
		this.sendQueueSimple(queueCustomName, connection, bean);
	}
	public void sendExchangeByName(String exchangeCustomName, String queueNameCustom, Connection connection,
			MqSendBean bean) throws Exception {
		String exchangeFormat = config.getExchangeFormat();
		String exchangeName = String.format(exchangeFormat, exchangeCustomName);
		String queueFormat = config.getQueueFormat();
		// 生成访问通道
		Channel channel = connection.createChannel();
		// 通过通道声明交换机
		channel.exchangeDeclare(exchangeName, "topic");
		// 队列名称
		String queueName = String.format(queueFormat, queueNameCustom);
		// 声明队列
		channel.queueDeclare(queueName, true, false, false, null);
		// 绑定消息列队和交换机
		channel.queueBind(queueName, exchangeName, "");
		// 消息内容
		String message = bean.getText();
		// 通过交换机发送
		channel.basicPublish(exchangeName, "", null, message.getBytes());
		channel.close();
		connection.close();
	}
	public void sendExchangeByName(String exchangeCustomName, String queueNameCustom, MqSendBean bean)
			throws Exception {
		this.sendExchangeByName(exchangeCustomName, queueNameCustom, this.createConnection(), bean);
	}
	/**
	 * 直接路由;
	 * 
	 * @Title: sendRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param routingKey
	 * @param bean
	 * @param routingType
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingByName(String exchangeCustomName, String routingKey, MqSendBean bean, String routingType)
			throws Exception {
		this.sendRouting(exchangeCustomName, routingKey, this.createConnection(), bean, routingType);
	}
	/**
	 * 直接路由;
	 * 
	 * @Title: sendRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param routingKey
	 * @param connection
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingByName(String exchangeCustomName, String routingKey, Connection connection, MqSendBean bean)
			throws Exception {
		this.sendRouting(exchangeCustomName, routingKey, connection, bean, "direct");
	}
	/**
	 * 直接路由;
	 * 
	 * @Title: receiveRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param connection
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveRoutingByName(String exchangeCustomName, Connection connection) throws Exception {
		this.receiveRouting(exchangeCustomName, connection, "direct");
	}
	/**
	 * 直接路由;
	 * 
	 * @Title: receiveRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param routingType
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveRoutingByName(String exchangeCustomName, String routingType) throws Exception {
		this.receiveRouting(exchangeCustomName, this.createConnection(), routingType);
	}
	/**
	 * 直接路由;
	 * 
	 * 指定队列名称;
	 * 
	 * @Title: sendRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param queueNameCustom
	 * @param routingKey
	 * @param connection
	 * @param bean
	 * @param routingType
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingByQueueName(String exchangeCustomName, String queueNameCustom, String routingKey,
			Connection connection, MqSendBean bean, String routingType) throws Exception {
		String exchangeFormat = config.getExchangeFormat();
		String exchangeName = String.format(exchangeFormat, exchangeCustomName);
		String queueFormat = config.getQueueFormat();
		// 生成访问通道
		Channel channel = connection.createChannel();
		// 通过通道声明交换机（"direct"表示使用路由标签Key）
		channel.exchangeDeclare(exchangeName, routingType);
		// 队列名称
		String queueName = String.format(queueFormat, queueNameCustom);
		// 声明队列
		channel.queueDeclare(queueName, true, false, false, null);
		// 绑定消息列队和交换机
		channel.queueBind(queueName, exchangeName, routingKey);
		// 消息内容
		String message = bean.getText();
		// 通过交换机发送
		channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
		channel.close();
		connection.close();
	}
	/**
	 * 直接路由;
	 * 
	 * 指定队列名称;
	 * 
	 * @Title: sendRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param queueNameCustom
	 * @param routingKey
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingByQueueName(String exchangeCustomName, String queueNameCustom, String routingKey,
			MqSendBean bean) throws Exception {
		this.sendRoutingByQueueName(exchangeCustomName, queueNameCustom, routingKey, this.createConnection(), bean,
				"direct");
	}
	/**
	 * 直接路由;
	 * 
	 * 
	 * 指定队列名称;
	 * 
	 * @Title: sendRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param queueNameCustom
	 * @param routingKey
	 * @param connection
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingByQueueName(String exchangeCustomName, String queueNameCustom, String routingKey,
			Connection connection, MqSendBean bean) throws Exception {
		this.sendRoutingByQueueName(exchangeCustomName, queueNameCustom, routingKey, connection, bean, "direct");
	}
	/**
	 * 直接路由;
	 * 
	 * 
	 * 指定队列名称;
	 * 
	 * @Title: sendRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param queueNameCustom
	 * @param routingKey
	 * @param bean
	 * @param routingType
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingByQueueName(String exchangeCustomName, String queueNameCustom, String routingKey,
			MqSendBean bean, String routingType) throws Exception {
		this.sendRoutingByQueueName(exchangeCustomName, queueNameCustom, routingKey, this.createConnection(), bean,
				routingType);
	}
	/**
	 * 模糊路由,路由匹配;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param routingKey
	 * @param connection
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingFuzzyByName(String exchangeCustomName, String routingKey, Connection connection,
			MqSendBean bean) throws Exception {
		this.sendRouting(exchangeCustomName, routingKey, connection, bean, "topic");
	}
	/**
	 * 模糊路由,路由匹配;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param connection
	 * @throws Exception
	 *             返回类型 void
	 */
	public void receiveRoutingFuzzyByName(String exchangeCustomName, Connection connection) throws Exception {
		this.receiveRouting(exchangeCustomName, connection, "topic");
	}
	/**
	 * 模糊路由,路由匹配;
	 * 
	 * 指定队列名称;
	 * 
	 * @Title: sendRouting
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param queueNameCustom
	 * @param routingKey
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingFuzzyByQueueName(String exchangeCustomName, String queueNameCustom, String routingKey,
			MqSendBean bean) throws Exception {
		this.sendRoutingByQueueName(exchangeCustomName, queueNameCustom, routingKey, this.createConnection(), bean,
				"topic");
	}
	/**
	 * 模糊路由,路由匹配;
	 * 
	 * 
	 * 指定队列名称;
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param queueNameCustom
	 * @param routingKey
	 * @param connection
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendRoutingFuzzyByQueueName(String exchangeCustomName, String queueNameCustom, String routingKey,
			Connection connection, MqSendBean bean) throws Exception {
		this.sendRoutingByQueueName(exchangeCustomName, queueNameCustom, routingKey, connection, bean, "topic");
	}
	/**
	 * 三种类型交换器 Fanout,Direct,Topic;
	 * 
	 * @Title: sendExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param connection
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendExchangeByType(String type, String exchangeCustomName, Connection connection, MqSendBean bean)
			throws Exception {
		String machine = config.getMachine();
		String exchangeFormat = config.getExchangeFormat();
		String exchangeName = machine + String.format(exchangeFormat, exchangeCustomName);
		// 生成访问通道
		Channel channel = connection.createChannel();
		// 通过通道声明交换机
		channel.exchangeDeclare(exchangeName, type);
		// 消息内容
		String message = bean.getText();
		// 通过交换机发送
		channel.basicPublish(exchangeName, "", null, message.getBytes());
		channel.close();
		connection.close();
	}
	/**
	 * 绑定消息列队和交换机;
	 * 
	 * 三种类型交换器 Fanout;
	 * 
	 * 速度最快;
	 * 
	 * @Title: sendExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void doBindExchange(String exchangeCustomName) throws Exception {
		this.doBindExchangeByType("fanout", exchangeCustomName, this.createConnection());
	}
	/**
	 * 绑定消息列队和交换机;
	 * 
	 * 三种类型交换器 Fanout,Direct,Topic;
	 * 
	 * @Title: sendExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param connection
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void doBindExchangeByType(String type, String exchangeCustomName, Connection connection) throws Exception {
		String machine = config.getMachine();
		String exchangeFormat = config.getExchangeFormat();
		String exchangeName = machine + String.format(exchangeFormat, exchangeCustomName);
		String queueFormat = config.getQueueFormat();
		// 生成访问通道
		Channel channel = connection.createChannel();
		// 通过通道声明交换机
		channel.exchangeDeclare(exchangeName, type);
		List<MqExchange> exchangeList = config.getExchangeList();
		for (MqExchange mqExchange : exchangeList) {
			if (exchangeCustomName.equals(mqExchange.getName())) {
				List<MqQueue> queueList = mqExchange.getQueueList();
				for (MqQueue mqQueue : queueList) {
					String queueNameCustom = mqQueue.getName();
					// 队列名称
					String queueName = machine + String.format(queueFormat, queueNameCustom);
					// 声明队列
					channel.queueDeclare(queueName, true, false, false, null);
					// 绑定消息列队和交换机
					channel.queueBind(queueName, exchangeName, "");
				}
				break;
			}
		}
		channel.close();
		connection.close();
	}
	/**
	 * 三种类型交换器 Fanout,Direct,Topic;
	 * 
	 * @Title: sendExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendExchangeByType(String type, String exchangeCustomName, MqSendBean bean) throws Exception {
		this.sendExchangeByType(type, exchangeCustomName, this.createConnection(), bean);
	}
	/**
	 * 三种类型交换器 Fanout;
	 * 
	 * 速度最快;
	 * 
	 * @Title: sendExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param connection
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendExchange(String exchangeCustomName, Connection connection, MqSendBean bean) throws Exception {
		this.sendExchangeByType("fanout", exchangeCustomName, connection, bean);
	}
	/**
	 * 三种类型交换器 Fanout;
	 * 
	 * 速度最快;
	 * 
	 * @Title: sendExchange
	 * @Description:
	 *
	 * 				参数说明
	 * @param exchangeCustomName
	 * @param bean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendExchange(String exchangeCustomName, MqSendBean bean) throws Exception {
		this.sendExchangeByType("fanout", exchangeCustomName, this.createConnection(), bean);
	}
}