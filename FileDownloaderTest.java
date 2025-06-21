import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URL;

import org.junit.Test;

public class FileDownloaderTest {

 
    @Test
    public void testCreateValidURL() throws Exception {
        URI uri = URI.create("https://example.com/file.zip");
        URL url = uri.toURL();
        
        assertNotNull(url, "URL should not be null");
        assertEquals("https://example.com/file.zip", url.toString(), "URL should match the input");
    }

   
    @Test
    public void testValidThreadCount() {
        int threadCount = 4;  
        assertTrue(threadCount >= 1 && threadCount <= 10, "Thread count should be between 1 and 10");
    }

    @Test
    public void testDownloadThreadCreation() {
        String fileURL = "https://example.com/file.zip";
        int startByte = 0;
        int endByte = 1000;
        int threadID = 1;
        String fileName = "file_part1";

        DownloadThread downloadThread = new DownloadThread(fileURL, startByte, endByte, threadID, fileName);
        
        assertNotNull(downloadThread, "DownloadThread should be created successfully");
        assertEquals(fileURL, downloadThread.getFileURL(), "File URL should match");
        assertEquals(startByte, downloadThread.getStartByte(), "Start byte should match");
    }
}

