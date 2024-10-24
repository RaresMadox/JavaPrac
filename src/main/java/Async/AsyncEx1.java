package Async;
import java.util.concurrent.CompletableFuture;
import java.io.*;

import static java.lang.Thread.sleep;
//Exercise 1: Basic Asynchronous Task Execution
//Task: Create a simple asynchronous task using CompletableFuture.supplyAsync(). The task should return a string (e.g., "Hello, Async!") after a delay of 2 seconds.
//        Difficulty: Easy
//Evaluation Criteria:
//Task executes asynchronously without blocking the main thread.
//The output is printed after the delay.
//The main thread should continue to execute without waiting for the result.

public class AsyncEx1 {


    public static void AsycAgain(){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                sleep(2000);  // Simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello, Async");
        });


        System.out.println("Main thread continues...");


        try {
            Thread.sleep(3000);  // Enough time for the "Hello, Async" to be printed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[])
    {
        AsycAgain();
    }

}
