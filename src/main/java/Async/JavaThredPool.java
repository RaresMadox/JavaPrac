package Async;


//Task: Use ExecutorService to create a custom thread pool.
// Run multiple asynchronous tasks using the custom thread pool and monitor how threads are reused. Ensure proper shutdown of the pool.
//Difficulty: Hard
//Evaluation Criteria:
//A custom thread pool is created and used for task execution.
//Tasks are executed in parallel and thread reuse is observed.
//The thread pool is properly shut down after task completion.
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JavaThredPool {


    public static void thredPool()
    {
        ExecutorService fixedFirstThreadPool = Executors.newFixedThreadPool(1);
        ExecutorService fixedSecondThreadPool = Executors.newFixedThreadPool(2);

        CompletableFuture<Void> futureTask = CompletableFuture.runAsync(() -> {
            System.out.println("Running in custom thread pool: " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, fixedFirstThreadPool);

        CompletableFuture<Void> futureTask2 = CompletableFuture.runAsync(() -> {
            System.out.println("Running in custom thread pool: " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, fixedSecondThreadPool);

        CompletableFuture<Void> futureTask3 = CompletableFuture.runAsync(() -> {
            System.out.println("Running in custom thread pool: " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, fixedFirstThreadPool);


        futureTask.thenRun(() -> System.out.println("Task1 completed!"))
                .join();

        futureTask2.thenRun(() -> System.out.println("Task2 completed!"))
                .join();

        futureTask3.thenRun(() -> System.out.println("Task3 completed!"))
                .join();

        // Shut down the executor
        fixedFirstThreadPool.shutdown();
        fixedSecondThreadPool.shutdown();

    }
    public static void main(String args[])
    {
        thredPool();
    }


}
