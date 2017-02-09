package com.ts.test.model;

/**
 * Minimal timeframe of 5 sec
 *Serves as a building block for larger time frames
 */
public class Tick {
	private double open;
	private double close;
	private double high;
	private double low;
	private long time;
	private long volume;
	private double wap;
	private int count;
	private String symbol;
	private String exchange;
	
	public Tick(Contract contr, long t, double o, double c, double h, double l, double w, long v, int cnt){
		open = o;
		close = c;
		high = h;
		low = l;
		time = t;
		volume = v;
		wap = w;
		symbol = contr.symbol();
		count = cnt;
	}

	public double open() {
		return open;
	}

	public void open(double open) {
		this.open = open;
	}

	public double close() {
		return close;
	}

	public void close(double close) {
		this.close = close;
	}

	public double high() {
		return high;
	}

	public void high(double high) {
		this.high = high;
	}

	public double low() {
		return low;
	}

	public void low(double low) {
		this.low = low;
	}

	public long time() {
		return time;
	}

	public void time(long time) {
		this.time = time;
	}

	public long volume() {
		return volume;
	}

	public void volume(long volume) {
		this.volume = volume;
	}

	public double wap() {
		return wap;
	}

	public void wap(double wap) {
		this.wap = wap;
	}

	public int count() {
		return count;
	}

	public void count(int count) {
		this.count = count;
	}

	public String symbol() {
		return symbol;
	}

	public void symbol(String symbol) {
		this.symbol = symbol;
	}

	public String exchange() {
		return exchange;
	}

	public void exchange(String exchange) {
		this.exchange = exchange;
	}
}
