package com.demo.xmlparser;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class FileDownloader {

    public void downloadFile(String url, String target) throws Exception {
        try {
            InputStream in = new URL(url).openStream();
            Files.copy(in, Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new Exception("Error downloading file!");
        }
    }

    public void unzipFile(String source, String target) throws Exception{
        FileInputStream fileInputStream;
        byte[] buffer = new byte[1024];
        try {
            fileInputStream = new FileInputStream(source);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while(zipEntry != null){
                File newFile = new File(target);
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.close();
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.closeEntry();
            zipInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new Exception("Error unzipping file!");
        }
    }
}
