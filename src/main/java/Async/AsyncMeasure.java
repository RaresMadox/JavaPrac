package Async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsyncMeasure {



    public static String fetchData(String source) throws InterruptedException{
        System.out.println("Fetching data from "+ source);
        TimeUnit.SECONDS.sleep(4);
        return "Data from "+source;

    }

    public static void fetchSynchronously() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        // Fetch data from multiple sources synchronously
        String data1 = fetchData("Source 1");
        String data2 = fetchData("Source 2");
        String data3 = fetchData("Source 3");

        long endTime = System.currentTimeMillis();
        System.out.println("Synchronous fetching took: " + (endTime - startTime) + " ms");
    }


    public static void fetchAsynchronously() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        // Fetch data from multiple sources asynchronously using CompletableFuture
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                return fetchData("Source 1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                return fetchData("Source 2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Rares

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                return fetchData("Source 3");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Wait for all futures to complete and get their results
        CompletableFuture.allOf(future1, future2, future3).join();

        long endTime = System.currentTimeMillis();
        System.out.println("Asynchronous fetching took: " + (endTime - startTime) + " ms");
    }
    public static void main(String args[]) throws InterruptedException {
        // Run synchronous fetch
        fetchSynchronously();

        System.out.println("--------------------");

        // Run asynchronous fetch
        fetchAsynchronously();
    }

}
