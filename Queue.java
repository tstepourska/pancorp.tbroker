package com.ts.test.run;

import java.util.LinkedList;

import com.ts.test.data.DataFactory;


public class Queue<Tick> extends Thread { //LinkedList<Bar> implements Runnable {
	
	private LinkedList<Tick> queue;
	private DataFactory dataFactory;
	private boolean working = true;
	
	public Queue(){
		this.queue = new LinkedList<Tick>();
	}
	
	/**
	 * Adds new tick to the head of the queue
	 * @param c
	 */
	public synchronized void addFirst(Tick c){
		queue.addFirst(c);
	}
	
	public synchronized void removeLast(){
		if(!this.queue.isEmpty())
			queue.removeLast();
	}

	@Override
	public void run() {
		while(working){
			
		}
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}
}
