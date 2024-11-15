import java.util.concurrent.*;
import java.util.*;

public class task1_1 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int size = 100; 
        int[] array = new Random().ints(size, 1, 100).toArray(); // Генерація масиву
        int numThreads = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Long>> results = new ArrayList<>();

        int chunkSize = size / numThreads;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? size : (i + 1) * chunkSize;
            results.add(executor.submit(() -> {
                long sum = 0;
                for (int j = start; j < end - 1; j++) {
                    sum += array[j] + array[j + 1];
                }
                return sum;
            }));
        }

        long result = 0;
        for (Future<Long> future : results) {
            result += future.get();
        }
        long endTime = System.currentTimeMillis();

        executor.shutdown();

        System.out.println("Generated array: " + Arrays.toString(array));

        System.out.println("Result: " + result);
        System.out.println("Time: " + (endTime - startTime) + " ms");
    }
}
