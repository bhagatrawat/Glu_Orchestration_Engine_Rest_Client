package com.github.bhagatsingh.glu.concurrency.process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.bhagatsingh.concurrency.process.ProcessIdWatchService;
import com.github.bhagatsingh.concurrency.process.RemovePidCallBack;

public class ProcessIdWatchServiceTester{
    private static List<Integer> pidList = new ArrayList<Integer>();
    public static void main(String ...arg){
            System.out.println("Pids for Watch Before: "+ pidList);
            ProcessIdWatchService watcher = ProcessIdWatchService.newInstance();
            watcher.registerCallBack(new RemovePidCallBack() {
                @Override
                public void processResult(List<Integer> pids) {
                    pidList.removeAll(pids) ;
                    System.out.println("Pids for after: "+ pidList);
                }
            });
            try {
                pidList.add(1234);
                pidList.add(5678);
                pidList.add(22331);
                pidList.add(23121);
                watcher.startPIDWatcher(0,10,TimeUnit.SECONDS);
                watcher.addPidToWatcherService(pidList);
                System.out.println("Wating for PID Watcher Service.");
                Thread.currentThread().sleep(100000);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
   
}
