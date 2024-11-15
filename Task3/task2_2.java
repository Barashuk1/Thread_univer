import java.io.File;
import java.util.concurrent.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class task2_2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        File directory = new File("C:/Users/toppl/Downloads");
        String extension = ".pdf";

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Queue<Future<Integer>> results = new ConcurrentLinkedQueue<>();

        long startTime = System.currentTimeMillis();
        processDirectory(directory, extension, executor, results);

        int count = 0;
        for (Future<Integer> result : results) {
            count += result.get(); // Чекаємо результат
        }
        long endTime = System.currentTimeMillis();

        executor.shutdown();

        System.out.println("Number of files: " + count);
        System.out.println("Time: " + (endTime - startTime) + " ms");
    }

    private static void processDirectory(File dir, String ext, ExecutorService executor, Queue<Future<Integer>> results) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                results.add(executor.submit(() -> {
                    processDirectory(file, ext, executor, results); // Рекурсивний обхід підкаталогів
                    return 0;
                }));
            } else if (file.getName().endsWith(ext)) {
                results.add(CompletableFuture.completedFuture(1)); // Додаємо знайдений файл
            }
        }
    }
}
