import java.io.*;
import java.net.*;

public class DownloadThread extends Thread {
    private String fileURL;
    private int startByte;
    private int endByte;
    private String partFileName;

    public DownloadThread(String fileURL, int startByte, int endByte, String partFileName) {
        this.fileURL = fileURL;
        this.startByte = startByte;
        this.endByte = endByte;
        this.partFileName = partFileName;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(fileURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);
            conn.connect();

            InputStream inputStream = conn.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(partFileName);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
            conn.disconnect();

            System.out.println("Downloaded part: " + partFileName);
        } catch (IOException e) {
            System.out.println("Download failed for: " + partFileName);
            e.printStackTrace();
        }
    }
}
