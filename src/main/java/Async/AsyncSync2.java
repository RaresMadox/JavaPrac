package Async;

import java.util.concurrent.CompletableFuture;

public class AsyncSync2 {

    public static void counterOdds(){

        for(int i = 1; i< 100;i=i+2){
            System.out.println(i);
        }
    }

    public static void counterEvens(){

        for(int i = 0; i< 100;i=i+2){
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {

        CompletableFuture<Void>future = CompletableFuture.runAsync(AsyncSync2::counterOdds);
        counterEvens();
        future.join();
        System.out.println("Ended counter");

    }


}
