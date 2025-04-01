import java.io.*;
import java.net.*;
import java.util.*;

public class FileDownloader {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Step 1: Get file URL
        System.out.println("Enter the URL of the file to download:");
        String fileURL = input.nextLine();

        // Step 2: Get number of threads
        System.out.println("How many threads would you like to use? (Max: 10)");
        int threadCount = input.nextInt();
        input.nextLine(); // Consume newline
        
        if (threadCount < 1 || threadCount > 10) {
            System.out.println("Invalid thread count! Using default (4 threads).");
            threadCount = 4;
        }
        input.close();

        // Step 3: Get the file name from the URL
        String fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
        int fileSize = getFileSize(fileURL);

        if (fileSize <= 0) {
            System.out.println("Failed to get file size. Exiting...");
            return;
        }

        System.out.println("File Size: " + fileSize + " bytes");

        // Step 4: Create threads for downloading
        int partSize = fileSize / threadCount;
        ArrayList<DownloadThread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            int startByte = i * partSize;
            int endByte = (i == threadCount - 1) ? fileSize : (startByte + partSize - 1);
            String partFileName = fileName + ".part" + i;

            DownloadThread thread = new DownloadThread(fileURL, startByte, endByte, partFileName);
            threads.add(thread);
            thread.start(); // Start downloading
        }

        // Step 5: Wait for all threads to finish
        for (DownloadThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Step 6: Merge files
        try {
            FileMerger.mergeFiles(fileName, threadCount);
            System.out.println("Download Complete!");
        } catch (IOException e) {
            System.out.println("Error merging files: " + e.getMessage());
        }

        input.close();
    }

    // Helper method to get file size
    private static int getFileSize(String fileURL) {
        try {
            URL url = new URL(fileURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            int length = conn.getContentLength();
            conn.disconnect();
            return length;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
