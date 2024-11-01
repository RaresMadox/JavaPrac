package Async;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
//Review and Suggested Improvements
//Retry Limit Check:
//
//The while(true) loop keeps running indefinitely until success or the retry
// limit is hit. A for loop up to the retry limit would make this clearer
// and avoid potentially infinite loops if the retry limit isn’t adjusted.
//Exception Handling and Termination:
//
//Instead of using throw e, handling the exception through
// completeExceptionally() is more idiomatic in CompletableFuture.
// This will ensure any chained tasks can detect and handle failure.
//Descriptive Logging:
//
//Clarifying the messages slightly to indicate each retry and the final outcome can improve readability.
public class AsynRetryLogicSOLUTION {

    public static void retryLogic() {
        CompletableFuture<Void> taskLogic = CompletableFuture.runAsync(() -> {
            Random rand = new Random();
            int maxRetries = 3;

            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    int number = rand.nextInt(2);  // Randomly 0 or 1
                    int result = 10 / number;
                    System.out.println("Result: " + result);
                    return;  // Exit on success
                } catch (ArithmeticException e) {
                    System.out.println("Attempt " + attempt + " failed (division by zero), retrying...");
                    if (attempt == maxRetries) {
                        System.err.println("Max retry attempts reached. Task failed.");
                        throw e;
                    }
                    try {
                        Thread.sleep(2000);  // Delay between retries
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Retry logic interrupted", ie);
                    }
                }
            }
        });

        taskLogic.join();  // Ensure the main thread waits for completion
    }

    public static void main(String[] args) {
        retryLogic();
    }
}

//        Explanation of Key Changes:
//        Using a for Loop:
//
//        Replaced while (true) with a for loop,
//        making the retry limit explicit and easy to adjust.
//        Improved Exception Handling:
//
//        Added a custom message "Max retry attempts
//        reached" to indicate when the retry limit has been hit.
//        Re-throws the exception at the end of the retry
//        limit to indicate final failure.
//        Thread Safety and Readability:
//
//        Thread.currentThread().interrupt() in the 
//        catch block for InterruptedException to properly
//        signal any interruptions in a concurrent context.
