package Async;

import java.util.concurrent.CompletableFuture;

public class AsyncSync {



    public static void SyncronousExample(){
        System.out.println("Syncronous Example");
        task1();
        task2();
        end();
        System.out.println("Syncronous Example ended");
    }


    public static void AsyncExample(){
        System.out.println("Asyn Example");

        CompletableFuture<Void> future = CompletableFuture.runAsync(()->{task1();});

        task2();
        future.join();// Blocks the main thread until task completes
        end();

        System.out.println("Async Example ended");
    }

    public static void main(String args[])
    {
        SyncronousExample();
        AsyncExample();
    }
    public static void  task1(){
        System.out.println("Task1 Running");
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Task1 Completed");
    }

    public static void task2(){
        System.out.println("Tasl2 Running");
        System.out.println("Task2 Completed");
    }
    public static void end(){
        System.out.println("All task are completed");
    }

}
