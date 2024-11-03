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


}
