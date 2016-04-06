package cn.leepon.demo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SY_Hign_TestCase {
	
	private final static int LOCK_COUNT = 1000;
	 
    //默认初始化LOCK_COUNT个锁对象
    private Object [] locks = new Object[LOCK_COUNT];
 
    private Random random = new Random();
 
    //构造时初始化对应的锁对象
    public SY_Hign_TestCase() {
       for(int i=0;i<LOCK_COUNT;++i)
           locks[i]=new Object();
    }
 
 
 
    abstract class Task implements Runnable{
 
       protected Object lock;
 
       public Task(int index) {
           this.lock= locks[index];
       }
       @Override
       public void run() {
           while(true){  //循环执行自己要做的事情
              doSth();
           }
       }
       //做类自己要做的事情
       public abstract void doSth();
    }
 
    //任务A 休眠自己的锁
    class TaskA extends Task{
 
       public TaskA(int index) {
           super(index);
       }
 
       @Override
       public void doSth() {
           synchronized (lock) {
              try {
                  lock.wait(random.nextInt(10));
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
           }
       }
 
    }
 
    //任务B 唤醒所有锁
    class TaskB extends Task{
      
       public TaskB(int index) {
           super(index);
        }
 
       @Override
       public void doSth() {
           try {
              synchronized (lock) {
                  lock.notifyAll();
                  Thread.sleep(random.nextInt(10));
              }
           } catch (InterruptedException e) {
              e.printStackTrace();
           }
       }
 
    }
    //启动函数
    public void start(){
       ExecutorService service = Executors.newCachedThreadPool();
       for(int i=0;i<LOCK_COUNT;++i){
           service.execute(new TaskA(i));
           service.execute(new TaskB(i));
       }
    }
    //主函数入口
    public static void main(String[] args) {
       new SY_Hign_TestCase().start();
    }


}
