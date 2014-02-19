package com.github.bhagatsingh.concurrency.process;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The ProcessIdWatchService class is being used to watch process Id(s) life cycle.
 * @author Bhagat Singh
 *
 */
public final class ProcessIdWatchService{
    private static ProcessIdWatchService pidWatchService;
    private static final Lock createPidWatchServiceLock = new ReentrantLock();
    private static final Lock pidOperationLock = new ReentrantLock();
    private final List<Integer> pidList = new ArrayList<Integer>();
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> handle;
    private RemovePidCallBack removeCallBack;
    
    private ProcessIdWatchService(){};
    
    public void registerCallBack(RemovePidCallBack callBack){
        this.removeCallBack = callBack;
    }
    /**
     * The newInstance method is to create an instance of ProcessIdWatchService
     * @param removeCallBack
     * @return
     */
    public static ProcessIdWatchService newInstance(){
        createPidWatchServiceLock.lock();
        try{
            if(pidWatchService == null){
                pidWatchService = new ProcessIdWatchService();
            }
          return pidWatchService;      
        }catch(Exception exp){
            throw new RuntimeException("Exception while creating an instance of ProcessIdWatchService."+ exp);
        }finally{
            createPidWatchServiceLock.unlock();
        }
    }
    
    
  /**
   * The startPIDWatcher method starts process id watcher scheduler
   * @param initialDelay
   * @param delay
   * @param unit
   */
    public void startPIDWatcher(long initialDelay, long delay, TimeUnit unit ) {
       try{
            System.out.println("Starting PID Watcher Scheduler .............");
            scheduler = Executors.newScheduledThreadPool(1);
            handle = scheduler.scheduleWithFixedDelay(new ProcessIdWatchProcessor(), initialDelay, delay, unit);
            System.out.println("Watcher is running in every "+ delay +" Seconds");
       }catch(Exception exp){
           clean();
           throw new RuntimeException(exp);
       }
    }
    
    
    /**
     * The resetPidList method clears active pid list 
     * @return
     * @throws UnsupportedOperationException
     */
    public boolean resetPidList() throws UnsupportedOperationException{
        if(pidList != null)
            pidList.clear();
        return true;
    }
    
    
    /**
     * The ProcessIdWatchProcessor class is a thread which runs every 60 seconds to check if process id is active or died. 
     * @author bsingh
     *
     */
    public class ProcessIdWatchProcessor implements Runnable {
        /**
        * The run method runs in every 60 seconds to watch process id(s) to check whether pid is died or alive.
        * If PID is died then it will remove that PID from ZooKeeper. 
        */
        public void run(){
            List<Integer> pidforRemove = new ArrayList<Integer>();
            if(!pidList.isEmpty()){
                Iterator<Integer> iterator = pidList.iterator();
                while (iterator.hasNext()) {
                    Integer pid = iterator.next().intValue();
                    if (!isPidExist(pid)) {
                        System.out.println("PID: " + pid + " is no more active");
                        pidOperationLock.lock();
                        try{
                            iterator.remove();
                            pidforRemove.add(pid);
                        }finally{
                            pidOperationLock.unlock();
                        }
                    }
                }
                if(!pidforRemove.isEmpty()){
                   try{
                       if(removeCallBack != null){
                           removeCallBack.processResult(pidforRemove);
                       }
                   }finally{
                       pidforRemove.clear();
                   }
                }
            }else{
                System.out.println("No PID for watch.");
            }
        }
        
        /**
         * 
         * @param pid
         * @return
         */
        public boolean isPidExist(Integer pid) {
            try {
                File file = new File("/proc/" + pid);
                if (file != null && file.exists()) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception exp) {
                return false;
            }
        }
    }//ProcessIdWatchProcessor
    
   /**
    * 
    * @param pid
    * @return
    */
    public boolean addPidToWatcherService(Integer pid){
        pidOperationLock.lock();
        try{
            pidList.add(pid);
        }catch(Exception exp){
            throw new RuntimeException("Exception while adding pid to PIDWatcher Service:"+exp);
        }finally{
            pidOperationLock.unlock();
        }
        return true;
    }
    
    /**
     * The addPidToWatcherService method adds pids to pidList. 
     * @param pid
     * @return
     */
     public boolean addPidToWatcherService(List<Integer> pids){
         pidOperationLock.lock();
         try{
             pidList.addAll(pids);
         }catch(Exception exp){
             throw new RuntimeException("Exception while adding pid to PIDWatcher Service:"+exp);
         }finally{
             pidOperationLock.unlock();
         }
         return true;
     }
     
    /**
     * The removePidFromWatcherService method remove pid from pid list
     * @param pid
     * @return
     */
    public boolean removePidFromWatcherService(Integer pid){
        pidOperationLock.lock();
        try{
            pidList.remove(pid);
            return true;
        }catch(Exception exp){
            throw new RuntimeException("Exception while removing pid from PID Watcher Service:"+exp);
        }finally{
            pidOperationLock.unlock();
        }
    }
    
    
    /**
     *  The removePidFromWatcherService method remove pidList from pid's list
     * @param pidList
     * @return
     */
    public boolean removePidFromWatcherService(List<Integer> pids){
        pidOperationLock.lock();
        try{
            pidList.removeAll(pids);
            return true;
        }catch(Exception exp){
            throw new RuntimeException("Exception while removing pid from PID Watcher Service:"+exp);
        }finally{
            pidOperationLock.unlock();
        }
    }
    
    /**
     * 
     * @return
     */
    public boolean cancelPidWatcherScheduler(){
        if(handle!=null){
            System.out.println("Cancelling Scheduler............");
            handle.cancel(true);
        }
        return true;
    }
    
    public void clean(){
        System.out.println("Cleaning Process PID Watch Service...");
        cancelPidWatcherScheduler();
        if(pidList != null){pidList.clear();};
        scheduler = null;
        handle = null;
        pidWatchService = null;
    }
    
    public List<Integer> list(){
        return pidList;
    }
}

