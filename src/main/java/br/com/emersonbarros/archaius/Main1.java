package br.com.emersonbarros.archaius;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;

public class Main1 implements Runnable {

	static Thread myThread;

	public static void main(String[] args) {
		System.out.println("Good morning");

		myThread = new Thread(new Main1());
		myThread.run();
		while (true) {
			try {
				Thread.sleep(5000);
				
				DynamicLongProperty timeToWait = DynamicPropertyFactory.getInstance().getLongProperty("lock.waitTime", 1000);

				ReentrantLock lock = new ReentrantLock();

				try {
					lock.tryLock(timeToWait.get(), TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	@Override
	public void run() {

		

	}

}
