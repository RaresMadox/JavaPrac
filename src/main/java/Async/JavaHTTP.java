package Async;

//Task: Use a library like HttpClient to send an HTTP GET request asynchronously. Print the response once the request is completed.
//Difficulty: Medium
//Evaluation Criteria:
//HTTP request is made asynchronously.
//The main thread is not blocked while waiting for the response.
//The response is printed correctly.
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JavaHTTP {

    public static void requestHTTP()
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                        .GET().
                        build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println("Status Code: " + response.statusCode());
                    // Print response body
                    System.out.println("Response Body: " + response.body());
                    return response;
                })
                .join();
    }

    public static void main(String args[])
    {
        requestHTTP();

    }


}
