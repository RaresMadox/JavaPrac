package Async;


//Task: Chain two asynchronous tasks where the second task depends on the result of the first one.
// The first task returns a number, and the second task processes that number (e.g., multiply by 2).
//Difficulty: Easy
//Evaluation Criteria:
//The tasks are executed asynchronously.
//The second task correctly processes the result of the first task.
//No blocking of the main thread.

import java.util.concurrent.CompletableFuture;

public class AsyncChaining {

    public static void ChainFunction()
    {
        CompletableFuture<Integer> getNumber = CompletableFuture.supplyAsync(()-> {return 10;});
        CompletableFuture<Void> multiplyNumber = getNumber.thenAcceptAsync(result->{


            System.out.println("Secondary thread continues...");


            try {
                Thread.sleep(2000);  // Enough time for the "Hello, Async" to be printed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           System.out.println("Result after multiplication " + (result * 2));

        });


    }
    public static void main(String args[])
    {

        ChainFunction();
        System.out.println("Main thread continues...");


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
