package Async;



//Exercise 3: Combining Multiple Asynchronous Tasks
//Task: Use CompletableFuture.allOf() to run three asynchronous tasks in parallel.
// Each task should return a result (e.g., "Task 1", "Task 2", "Task 3") after a delay.
// Combine the results of all tasks and print them.


//        Difficulty: Medium
//Evaluation Criteria:
//Tasks are run in parallel (non-blocking).
//Results are combined and printed in the correct format.
//Main thread continues without waiting for the tasks to finish.

import java.util.concurrent.CompletableFuture;

public class AsyncMultipleTask {


    public static void AsyncMultiTask(){
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(()->{System.out.println("Task1");});
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(()->{System.out.println("Task2");});
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(()->{System.out.println("Task3");});

        CompletableFuture.allOf(task1,task2,task3);

    }

    public static void main(String args[]){


        AsyncMultiTask();
    }

}
