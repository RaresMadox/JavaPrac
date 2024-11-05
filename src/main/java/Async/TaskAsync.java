package Async;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//Exercise 8: Combining Multiple Async Tasks with Dependencies
//Task: Create three asynchronous tasks where:
//The first task fetches a list of user IDs.
//The second task fetches details of the users based on the IDs
// (dependent on the first task).
//The third task sends a report of the user details
// (dependent on the second task).
//Difficulty: Hard
//Evaluation Criteria:
//Each task runs asynchronously in the correct order.
//The second and third tasks depend on the completion of the previous tasks.
//The correct results are printed at each step.
public class TaskAsync {
    static List<Integer> UserID = new ArrayList<>();
    static List<String> userDetailes = new ArrayList<>();

    public static void taskDependeciesChained()
    {





        CompletableFuture<List<Integer>> getIDs = CompletableFuture.supplyAsync(()->{
                List<Integer> userIdFetch = new ArrayList<>();

                for(var elem:UserID)
                {
                    userIdFetch.add(elem);
                }
                return userIdFetch;
        });

        CompletableFuture<Map<Integer,String>> getDetailes = getIDs.thenApplyAsync                (
                        result->{
                            Map<Integer,String> mapUserDetailes = new HashMap<>();

                            for(int elem:result)
                            {
                                mapUserDetailes.put(elem,userDetailes.get(elem));
                            }

                            return mapUserDetailes;
                        }
                );

        CompletableFuture<Void> sendMessage = getDetailes.thenAcceptAsync(
                result->{
                    for(int elem: result.keySet())
                    {
                        System.out.println("Message for: "+elem+" is sending:....");
                        System.out.println(result.get(elem));
                    }
                }
        );

        //Chained Version;
    }

    public static void taskDependecies() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Integer>> getIDs = CompletableFuture.supplyAsync(()->{
            List<Integer> userIdFetch = new ArrayList<>();

            for(var elem:UserID)
            {
                userIdFetch.add(elem);
            }
            return userIdFetch;
        });

        CompletableFuture<List<String>> fetchContent = CompletableFuture.supplyAsync(()->{
            List<String> detailContent = new ArrayList<>();

            for(var elem:userDetailes)
            {
                detailContent.add(elem);
            }
            return detailContent;
        });

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(getIDs, fetchContent);

        CompletableFuture<Map<Integer, String>> combinedResult = allTasks.thenApply(v -> {
            Map<Integer, String> results = new HashMap<>();
            List<Integer> resultIds = new ArrayList<>();
            List<String> resultContent = new ArrayList<>();
            try {
                resultIds = getIDs.get(); // Blocking call to get the result

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();}


            try {
                resultContent = fetchContent.get(); // Blocking call to get the result

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();}

            for(int elem: resultIds)
            {
                results.put(elem,resultContent.get(resultIds.indexOf(elem)));
                System.out.println("Sending to "+elem+" content: "+ resultContent.get(resultIds.indexOf(elem)));
            }
            return results;
        });


        Map<Integer, String> result = combinedResult.get();
        System.out.println("Combined Result: " + result);
    }

    public static void main(String args[]) throws ExecutionException, InterruptedException {

        UserID.add(1);
        UserID.add(2);
        UserID.add(3);

        userDetailes.add("Salutare Salutare");
        userDetailes.add("Mergem Mergem");
        userDetailes.add("Ne uitam la cer");

        taskDependecies();
    }
//    Key Points to Improve:
//    Avoid Blocking Calls:
//
//    The get() calls on getIDs and fetchContent are blocking, which goes against the non-blocking nature of CompletableFuture. Instead, you should chain these futures using thenCombine() or thenCompose() to avoid blocking.
//    Handling Result Mapping:
//
//    Instead of using indexOf within a loop, which is inefficient, iterate over the results directly to populate the map.
//    Use of Static Variables (e.g., UserID, userDetailes):
//
//    The code references variables UserID and userDetailes without initializing them here. Assuming they’re defined elsewhere as static variables, it’s best to initialize and reference them directly in the method for clarity.
//    Streamlining Code for Efficiency:
//
//    Simplifying the logic with thenCombine() can lead to more readable, concise, and non-blocking code.

    public class AsyncDependency {

        // Assuming these lists are initialized somewhere as part of the exercise
        private static final List<Integer> UserID = List.of(1, 2, 3);   // Sample IDs
        private static final List<String> userDetails = List.of("Content A", "Content B", "Content C");   // Sample details

        public static void taskDependencies() throws ExecutionException, InterruptedException {
            // First async task: fetch IDs
            CompletableFuture<List<Integer>> getIDs = CompletableFuture.supplyAsync(() -> new ArrayList<>(UserID));

            // Second async task: fetch details
            CompletableFuture<List<String>> fetchContent = CompletableFuture.supplyAsync(() -> new ArrayList<>(userDetails));

            // Combine both results non-blockingly
            CompletableFuture<Map<Integer, String>> combinedResult = getIDs.thenCombine(fetchContent, (ids, content) -> {
                Map<Integer, String> results = new HashMap<>();
                for (int i = 0; i < Math.min(ids.size(), content.size()); i++) {
                    results.put(ids.get(i), content.get(i));
                    System.out.println("Sending to " + ids.get(i) + " content: " + content.get(i));
                }
                return results;
            });

            // Retrieve and display the final combined result
            Map<Integer, String> result = combinedResult.get();  // Blocking here only for the final result
            System.out.println("Combined Result: " + result);
        }

        public static void main(String[] args) {
            try {
                taskDependencies();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
   }
//    Explanation of Improvements:
//    Non-Blocking Combination Using thenCombine():
//
//    Replaced CompletableFuture.allOf() with thenCombine() to combine getIDs and fetchContent without needing blocking calls (get()) for intermediate results.
//    Mapping Results Efficiently:
//
//    Replaced for loop with index-based iteration using Math.min(ids.size(), content.size()) to handle cases where ids and content lists may have different lengths, ensuring no IndexOutOfBoundsException.
//    Using List.of() for Static Variables:
//
//    Sample values are initialized using List.of() for clarity, assuming these are set up globally in your application.
//    Blocking Only on the Final Result:
//
//    Instead of using get() in intermediate steps, the only blocking call is combinedResult.get(), ensuring a non-blocking execution of the asynchronous chain up until the final result retrieval.
}
