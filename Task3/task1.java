import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;
import java.util.Random;

public class task1 {
    static class PairwiseSumTask extends RecursiveTask<Long> {
        private final int[] array;
        private final int start, end;

        public PairwiseSumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= 10) {
                long sum = 0;
                for (int i = start; i < end - 1; i++) {
                    sum += array[i] + array[i + 1];
                }
                return sum;
            }
            int mid = (start + end) / 2;
            PairwiseSumTask leftTask = new PairwiseSumTask(array, start, mid);
            PairwiseSumTask rightTask = new PairwiseSumTask(array, mid, end);

            leftTask.fork(); 
            long rightResult = rightTask.compute(); 
            long leftResult = leftTask.join(); 

            return leftResult + rightResult;
        }
    }

    public static void main(String[] args) {
        int size = 100; 
        int[] array = new Random().ints(size, 1, 100).toArray();
        ForkJoinPool pool = new ForkJoinPool();

        System.out.println("Generated array: " + Arrays.toString(array));

        long startTime = System.currentTimeMillis();
        long result = pool.invoke(new PairwiseSumTask(array, 0, array.length));
        long endTime = System.currentTimeMillis();

        System.out.println("Pairwise sum result: " + result);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}
