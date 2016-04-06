package cn.leepon.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Title: 原子自增索引
 * @Description 自动索引 <默认>从0开始
 */
public class AutoLong {

	// 自动索引
	private AtomicLong longV;

	private AutoLong() {
		AtomicLong idx = new AtomicLong(0);
		this.longV = idx;
	}

	private AutoLong(long initV) {
		this.longV = new AtomicLong(initV);
	}

	/**
	 * 获取一个 <自动索引> 实例对象
	 * @return
	 */
	public static AutoLong getAutoLong() {
		return new AutoLong();
	}

	/**
	 * 获取一个给定初始值的 <自动索引> 实例对象
	 * @param initV
	 * @return
	 */
	public static AutoLong getAutoLong(long initV) {
		return new AutoLong(initV);
	}

	/**
	 * Index自增1 =>添加索引, 并返回Index值
	 * @return
	 */
	public long addLong() {
		longV.getAndIncrement();
		return get();
	}

	/**
	 * Index自增delta值 =>添加索引, 并返回Index值
	 * @param delta
	 * @return
	 */
	public long addLong(long delta) {
		longV.getAndAdd(delta);
		return get();
	}

	/**
	 * Index减1,删除标识, 并返回Key值
	 * @return
	 */
	public long missLong() {
		if (get() == 0) {// 若已是索引的初始值0，则变为自增1
			return addLong();
		} else {
			longV.getAndDecrement();
			return get();
		}
	}

	/**
	 * 获取当前的Index数值
	 * @return
	 */
	public long get() {
		return longV.get();
	}

	/**
	 *  重置 并 返回重置前的值
	 * @param newV
	 * @return
	 */
	public long reset(long newV) {
		long preV = longV.get();
		longV.set(newV);
		return preV;
	}

	/**
	 *  <清除>索引, Index置0
	 */
	public void clear() {
		longV.set(0);
	}

	// Get-Set方法
	public AtomicLong getLongV() {
		return longV;
	}

	public void setLongV(AtomicLong index) {
		this.longV = index;
	}

	@Override
	public String toString() {
		return "AutoLong [longV=" + longV.get() + "]";
	}

}
