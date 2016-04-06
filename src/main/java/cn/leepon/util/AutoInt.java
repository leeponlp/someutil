package cn.leepon.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: 原子自增索引 
 * @Description 自动<行的>索引  <默认>从0开始
 */
public class AutoInt {
	
	private AtomicInteger intV;//自动行索引
	
	private AutoInt(){
		this.intV = new AtomicInteger(0);
	}
	
	private AutoInt(int initV){
		this.intV = new AtomicInteger(initV);
	}
	
	
	/**
	 * 获取一个 <自动索引> 实例对象
	 * @return
	 */
	public static AutoInt getAutoInt(){
		return new AutoInt();
	}
	
	/**
	 * 获取一个给定初始值的 <自动索引> 实例对象
	 * @param initV
	 * @return
	 */
	public static AutoInt getAutoInt(int initV){
		return new AutoInt(initV);
	}
	
	
	/**
	 * Row自增1 =>添加索引, 并返回row值
	 * @return
	 */
	public int addInt(){
		intV.getAndIncrement();
		return get();
	}
	
	/**
	 * Row自增delta值 =>添加索引, 并返回row值
	 * @param delta
	 * @return
	 */
	public int addInt(int delta){
		intV.getAndAdd(delta);
		return get();
	}
	
	/**
	 * Row减1,删除标识, 并返回Key值
	 * @return
	 */
	public int missInt(){
		if(get() == 0){//若已是索引的初始值0，则变为自增1
			return addInt();
		}else{
			intV.getAndDecrement();
			return get();
		}
	}
	
	/**
	 * 获取当前的row数值
	 * @return
	 */
	public int get(){
		return intV.get();
	}
	
	/**
	 *  重置  并  返回重置前的值
	 * @param newV
	 * @return
	 */
	public int reset(int newV){
		int preV = intV.get();
		intV.set( newV );
		return preV;
	}	
	
	/**
	 *  <清除>索引, row置0
	 */
	public void clear(){
		intV.set(0);
	}
	
	//Get-Set方法
	public AtomicInteger getIntV() {
		return intV;
	}
	public void setIntV(AtomicInteger row) {
		this.intV = row;
	}

	
	@Override
	public String toString() {
		return "AutoInt [intV=" + intV.get() + "]";
	}
	
	
}

