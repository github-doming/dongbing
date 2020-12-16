package org.doming.example.thread.eg5.task;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @Description: 任务测试
 * @Author: Dongming
 * @Date: 2018-11-03 10:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */

public class SynchronizedListTaskTest {

	interface Constants {

		int MAX_BUFFER_SIZE = 10;
		int NUM_OF_PRODUCER = 2;
		int NUM_OF_CONSUMER = 3;

	}

	static class Task {
		/**
		 * 任务的编号
		 */
		private String id;

		public Task() {
			id = UUID.randomUUID().toString();
		}

		@Override public String toString() {
			return "Task{" + id + '}';
		}
	}

	static class Consumer implements Runnable {
		private List<Task> buffer;

		public Consumer(List<Task> buffer) {
			this.buffer = buffer;
		}

		@Override public void run() {

			while (true){
				synchronized (buffer){
					while (buffer.isEmpty()){
						try {
							buffer.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Task task = buffer.remove(0);
					buffer.notifyAll();
					System.out.println("消费者[" + Thread.currentThread().getName() + "] 消费 " + task);
				}
			}
		}
	}

	static class Producer implements Runnable {



		private List<Task> buffer;

		public Producer(List<Task> buffer) {
			this.buffer = buffer;
		}

		@Override public void run() {

			while (true) {
				synchronized (buffer) {
					while (buffer.size() >= Constants.MAX_BUFFER_SIZE) {
						try {
							buffer.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Task task = new Task();
					buffer.add(task);
					buffer.notifyAll();
					System.out.println("生产者[" + Thread.currentThread().getName() + "] 生产 " + task);
				}
			}
		}
	}


	public static void main(String[] args) {
		List<Task> buffer = new ArrayList<>(Constants.MAX_BUFFER_SIZE);

		ExecutorService service = Executors.newFixedThreadPool(Constants.NUM_OF_CONSUMER + Constants.NUM_OF_PRODUCER);

		for (int i = 1; i <= Constants.NUM_OF_PRODUCER; ++i) {
			service.execute(new Producer(buffer));
		}
		for (int i = 1; i <= Constants.NUM_OF_CONSUMER; ++i) {
			service.execute(new Consumer(buffer));
		}
	}
}

