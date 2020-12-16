package org.doming.example.thread.eg5.task;
/**
 * @Description: 生产者-消费者
 * @Author: Dongming
 * @Date: 2018-11-03 11:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 公共常量
 *
 * @author 骆昊
 */

public class BlockingQueueTaskTest {
	class Constants {
		public static final int MAX_BUFFER_SIZE = 10;
		public static final int NUM_OF_PRODUCER = 2;
		public static final int NUM_OF_CONSUMER = 3;
	}

	/**
	 * 工作任务
	 *
	 * @author 骆昊
	 */
	static class Task {
		/**
		 * 任务的编号
		 */
		private String id;

		public Task() {
			id = UUID.randomUUID().toString();
		}

		@Override public String toString() {
			return "Task[" + id + "]";
		}
	}

	static class Consumer implements Runnable {

		private BlockingQueue<Task> buffer;

		public Consumer(BlockingQueue<Task> buffer) {
			this.buffer = buffer;
		}

		@Override public void run() {
			while (true) {
				try {
					Task task = buffer.take();
					System.out.println("消费者[" + Thread.currentThread().getName() + "] 消费 " + task);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class Produce implements Runnable {
		private BlockingQueue<Task> buffer;
		public Produce(BlockingQueue<Task> buffer) {
			this.buffer = buffer;
		}

		@Override public void run() {

			while (true) {
				try {
					Task task = new Task();
					buffer.put(task);
					System.out.println("生产者[" + Thread.currentThread().getName() + "] 生产 " + task);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		BlockingQueue<Task> buffer = new LinkedBlockingQueue<>(Constants.MAX_BUFFER_SIZE);
		ExecutorService service = Executors.newFixedThreadPool(Constants.NUM_OF_CONSUMER + Constants.NUM_OF_PRODUCER);

		for(int i = 1; i <= Constants.NUM_OF_PRODUCER ; ++i) {
			service.execute(new Produce(buffer));
		}
		for(int i = 1; i <= Constants.NUM_OF_CONSUMER ; ++i) {
			service.execute(new Consumer(buffer));
		}

	}

}
