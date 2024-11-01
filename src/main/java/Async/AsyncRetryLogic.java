package Async;

//Exercise 7: Asynchronous Retry Logic
//Task: Implement a task that may fail (e.g., throw an exception randomly).
// Use asynchronous programming to retry the task up to 3 times if it fails, with a 1-second delay between each attempt.
//        Difficulty: Hard
//Evaluation Criteria:
//The task retries correctly if it fails.
//After 3 retries, the final failure is handled gracefully.
//If the task succeeds, it stops retrying and returns the result.
import java.util.concurrent.CompletableFuture;
import java.util.Random;
public class AsyncRetryLogic {

    public static void retryLogic()
    {

        CompletableFuture<Void> taskLogic = CompletableFuture.runAsync(
                ()->{
                    int cnt = 0;
                    Random rand = new Random();
                    while(true){
                    int number = rand.nextInt(2);
                    try{
                        int result = 10 / number;
                        System.out.println("Result: "+result);
                        return;

                    } catch (ArithmeticException e)
                    {
                        ++cnt;
                        System.out.println("Attempt "+cnt+" failed, retrying...");
                        if(cnt == 3)
                            throw e;
                        try {
                            Thread.sleep(2000);  // Enough time for the "Hello, Async" to be printed
                        } catch (InterruptedException d) {
                            d.printStackTrace();
                        }

                    }
                }}
        );
    taskLogic.join();
    }

    public static void main(String args[])
    {

        retryLogic();

    }

}
