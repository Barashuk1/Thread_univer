import java.io.File;
import java.util.concurrent.RecursiveTask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class task2 {
    static class FileSearchTask extends RecursiveTask<Integer> {
        private final File directory;
        private final String extension;

        public FileSearchTask(File directory, String extension) {
            this.directory = directory;
            this.extension = extension;
        }

        @Override
        protected Integer compute() {
            File[] files = directory.listFiles();
            if (files == null) return 0;

            int count = 0;
            List<FileSearchTask> subTasks = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    FileSearchTask subTask = new FileSearchTask(file, extension);
                    subTasks.add(subTask);
                    subTask.fork();
                } else if (file.getName().endsWith(extension)) {
                    count++;
                }
            }

            for (FileSearchTask subTask : subTasks) {
                count += subTask.join();
            }

            return count;
        }
    }

    public static void main(String[] args) {
        File directory = new File("C:/Users/toppl/Downloads");
        String extension = ".pdf";

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        int result = pool.invoke(new FileSearchTask(directory, extension));
        long endTime = System.currentTimeMillis();

        System.out.println("Number of files: " + result);
        System.out.println("Time: " + (endTime - startTime) + " ms");
    }
}
