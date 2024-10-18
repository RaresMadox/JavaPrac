import java.util.concurrent.CompletableFuture;





public class FirstExercise {

    public static void  task1(){
        System.out.println("Task1 Running");
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Task1 Completed");
    }

    public static void task2(){
        System.out.println("Tasl2 Running");
        System.out.println("Task2 Completed");
    }
    public static void end(){
        System.out.println("All task are completed");
    }


    public static void SyncronousExample(){
        System.out.println("Syncronous Example");
        task1();
        task2();
        end();
        System.out.println("Syncronous Example ended");
    }

    public static void main(String args[])
    {
        SyncronousExample();
    }

}
