import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class task {
    public static void main(String[] args) {
        int minRange = -100;
        int maxRange = 100;
        int arraySize = new Random().nextInt(21) + 40; 
        int multiplier = 5; 
        int chunkSize = 20; 

        List<Integer> array = new CopyOnWriteArrayList<>();
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array.add(random.nextInt(maxRange - minRange + 1) + minRange);
        }

        List<Future<List<Integer>>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < array.size(); i += chunkSize) {
            List<Integer> sublist = array.subList(i, Math.min(i + chunkSize, array.size()));
            Future<List<Integer>> future = executor.submit(new ArrayProcessor(sublist, multiplier));
            futures.add(future);
        }
        
        int i = 0;
        for (Future<List<Integer>> future : futures) {
            try {
                i += 1;
                if (!future.isCancelled()) {
                    List<Integer> result = future.get();
                    System.out.println("Результат " + i + " частини: " + result);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("Час роботи програми: " + (endTime - startTime) + " мс");
    }
}

class ArrayProcessor implements Callable<List<Integer>> {
    private final List<Integer> subArray;
    private final int multiplier;

    public ArrayProcessor(List<Integer> subArray, int multiplier) {
        this.subArray = new ArrayList<>(subArray); 
        this.multiplier = multiplier;
    }

    @Override
    public List<Integer> call() {
        List<Integer> result = new ArrayList<>();
        for (int num : subArray) {
            result.add(num * multiplier); 
        }
        return result;
    }
}
