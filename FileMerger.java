import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileMerger {
    public static void mergeFiles(String fileName, int parts) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName, true);

        for (int i = 0; i < parts; i++) {
            File partFile = new File(fileName + ".part" + i);
            FileInputStream fis = new FileInputStream(partFile);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fis.close();
            partFile.delete(); // Delete chunk after merging
        }

        fos.close();
        System.out.println("Merged file saved as: " + fileName);
    }
}
