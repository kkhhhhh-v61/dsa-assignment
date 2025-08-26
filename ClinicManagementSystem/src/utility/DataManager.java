package utility;

import java.io.*;

public class DataManager {

    public static void saveToFile(Serializable object, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
        } catch (IOException e) {
            System.err.println("Error saving data to " + filePath + ": " + e.getMessage());
        }
    }

    public static Object readFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath + ". A new file will be created on save.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading data from " + filePath + ": " + e.getMessage());
            return null;
        }
    }
}
