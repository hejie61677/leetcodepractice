package multithreading;

import java.util.concurrent.CountDownLatch;

public class Foo {
	
	private CountDownLatch second = new CountDownLatch(1);
	private CountDownLatch third = new CountDownLatch(1);

	public static void main(String[] args) {
		Foo foo = new Foo();
		
		Thread thread1 = new Thread(() -> {
			try {
				foo.first(foo::one);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread thread2 = new Thread(() -> {
			try {
				foo.second(foo::two);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread thread3 = new Thread(() -> {
			try {
				foo.third(foo::three);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread1.start();
		thread2.start();
		thread3.start();
	}
	
	public Foo() {
	        
	}

	public void first(Runnable printFirst) throws InterruptedException {
			
		// printFirst.run() outputs "first". Do not change or remove this line.
		printFirst.run();
		second.countDown();
	}

	public void second(Runnable printSecond) throws InterruptedException {
		
		second.await();
		// printSecond.run() outputs "second". Do not change or remove this line.
		printSecond.run();
		third.countDown();
	}

	public void third(Runnable printThird) throws InterruptedException {
		
		third.await();
		// printThird.run() outputs "third". Do not change or remove this line.
		printThird.run();
	}
	
	public void one() { 
		System.out.print("one"); 
	}
	
	public void two() {
		System.out.print("two"); 
	}
	
	public void three() { 
		System.out.print("three"); 
	}

}
