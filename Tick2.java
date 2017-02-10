package com.ts.test.model;

/**
 * Minimal timeframe of 5 sec
 *Serves as a building block for larger time frames
 */
public class Tick extends Bar{
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
		super(  t, o, c,h, l, w, v,cnt);
		/*open = o;
		close = c;
		high = h;
		low = l;
		time = t;
		volume = v;
		wap = w;*/
		symbol = contr.symbol();
		count = cnt;
	}

	@Override
	public double open() {
		return open;
	}

	@Override
	public void open(double open) {
		this.open = open;
	}

	@Override
	public double close() {
		return close;
	}

	@Override
	public void close(double close) {
		this.close = close;
	}

	@Override
	public double high() {
		return high;
	}

	@Override
	public void high(double high) {
		this.high = high;
	}

	@Override
	public double low() {
		return low;
	}

	@Override
	public void low(double low) {
		this.low = low;
	}

	@Override
	public long time() {
		return time;
	}

	@Override
	public void time(long time) {
		this.time = time;
	}

	@Override
	public long volume() {
		return volume;
	}

	@Override
	public void volume(long volume) {
		this.volume = volume;
	}

	@Override
	public double wap() {
		return wap;
	}

	@Override
	public void wap(double wap) {
		this.wap = wap;
	}

	@Override
	public int count() {
		return count;
	}

	@Override
	public void count(int count) {
		this.count = count;
	}

	@Override
	public String symbol() {
		return symbol;
	}

	@Override
	public void symbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String exchange() {
		return exchange;
	}

	@Override
	public void exchange(String exchange) {
		this.exchange = exchange;
	}

}
