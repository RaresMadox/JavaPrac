//Java program to demonstrate the use of the CompletableFuture
import java.io.*;
import java.util.concurrent.CompletableFuture;

public class GFG {

    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello, CompletableFuture!";
        });
        future.thenAccept(result -> System.out.println("The Result: " + result));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
