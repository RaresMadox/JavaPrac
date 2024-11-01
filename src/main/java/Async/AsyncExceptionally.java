package Async;
//Asynchronous Tasks
//Task: Create an asynchronous task that randomly throws an exception (e.g., if a random number is greater than 0.5). Use exceptionally() to handle the exception and provide a fallback value.
//Difficulty: Medium
//Evaluation Criteria:
//Exception is handled correctly when it occurs.
//The fallback value is provided when an exception is thrown.
//When no exception occurs, the correct result is printed.
import java.util.concurrent.CompletableFuture;
import java.util.Random;

public class AsyncExceptionally {

    public static void asyncExceptional(){

            CompletableFuture<Void> taskWithException = CompletableFuture.runAsync(() -> {
                try {
                    int result = 10 / 2;  // Deliberate division by zero
                    System.out.println("Result: " + result);
                } catch (ArithmeticException e) {
                    throw new RuntimeException("Error during calculation: Division by zero");
                }
            });

        CompletableFuture<Void> handleException = taskWithException.exceptionally(ex -> {
            System.out.println("Caught exception: " + ex.getMessage());
            return null;  // Returning null as it's a Void CompletableFuture
        });

        // Ensure program waits for completion of async tasks for demonstration purposes
        handleException.join();

    }


    public static void main(String args[])
    {
        asyncExceptional();
    }

}
