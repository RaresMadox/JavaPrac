package Async;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.nio.channels.AsynchronousFileChannel;
import java.util.concurrent.Future;

//Task: Use AsynchronousFileChannel to read the contents of a file asynchronously. Print the contents of the file once the read operation is complete.
//Difficulty: Medium
//Evaluation Criteria:
//File is read asynchronously without blocking the main thread.
//The file contents are printed correctly.
//Ensure that proper error handling is in place if the file doesn’t exist.
public class AsyncFileRead {

//    Issues and Improvements
//    Avoiding the Future with Non-Blocking Callback:
//
//    Using Future in this context introduces blocking in the while (!result.isDone()) loop. Instead, we can use the non-blocking CompletionHandler to handle the reading operation's completion.
//    Error Handling:
//
//    The code lacks exception handling for cases like FileNotFoundException. It’s best to include error handling directly within the asynchronous task.
//    Resource Management:
//
//    You’re correctly closing channel after reading, which is good, but make sure this happens even if there’s an error.

//    CompletionHandler for Non-Blocking:
//
//    The CompletionHandler replaces the Future object, removing the need for the while (!result.isDone()) loop.
//    The completed method is called automatically when the asynchronous read operation finishes, so there’s no blocking.
//    Improved Error Handling:
//
//    Added a failed method within the CompletionHandler to handle any errors encountered during the file reading process.
//    Catching and handling IOException and InterruptedException in the try-with-resources block for better resource management.
//    Main Thread Continuation:
//
//    After starting the asynchronous read, the main thread can perform other tasks or be freed up.
    public static void readFine() throws IOException {
        String path = "D:\\Java\\Execise\\src\\main\\java\\Assets\\notes.txt";
        Path pathFile = Paths.get(path);
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(pathFile, StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(400);
        Future<Integer> result = channel.read(buffer, 0);

        while(!result.isDone())
        {
            System.out.println("In progress...[ASYNC]");
        }

        buffer.flip();
        System.out.print("Buffer contents: ");

        while (buffer.hasRemaining()) {

            System.out.print((char)buffer.get());
        }

        System.out.println(" ");

        // Closing the channels using close() method
        buffer.clear();
        channel.close();

    }

    public static void main(String args[]) throws IOException {
        readFine();
    }

}
