package com.example.test.model;
 
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
 
public class FileIOControl {
 
    public FileIOControl() {
    }

    public void WriteObjectToFile(Object serObj, String filePath) {
        try {
            String filepath = System.getProperty("user.dir")  +  filePath;
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            fileOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object ReadObjectFromFile(String filePath) {
        try {
            String filepath = System.getProperty("user.dir")  +  filePath;
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}