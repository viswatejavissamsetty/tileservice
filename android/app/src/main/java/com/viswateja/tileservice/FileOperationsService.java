package com.viswateja.tileservice;

import android.content.res.Resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileOperationsService {
    private final String fileBasePath = Resources.getSystem().getString(R.string.documents_base_path);

    FileOperationsService () {
        System.out.println(fileBasePath);
    }

    public void writeIntoFile(String fileName, String content) {
        try {
            FileWriter f = new FileWriter(fileBasePath + fileName);
            f.write(content);
            f.close();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    public String readFromFile(String fileName) {
        System.out.println(fileBasePath + fileName);
        try {
            File f = new File(fileBasePath + fileName);
            Scanner dataReader = new Scanner(f);
            String fileData = "";
            while (dataReader.hasNextLine()) {
                fileData += dataReader.nextLine();
                System.out.println(fileData);
            }
            dataReader.close();
            return fileData;
        } catch (FileNotFoundException e) {
            System.out.println("Unexcpected error occurred!");
            e.printStackTrace();
            return null;
        }
    }

    public Boolean hadFile(String fileName) {
        File f0 = new File(fileBasePath + fileName);
        return f0.exists();
    }
}
