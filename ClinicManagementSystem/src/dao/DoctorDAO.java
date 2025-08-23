package dao;

import adt.ListInterface;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

/**
 *
 *  @author Brian Ooi Shu Yi
 */

/**
 * A utility class for handling file input and output operations.
 * It provides generic methods to save and load lists of objects to/from text files.
 */
public class DoctorDAO {

    /**
     * Saves a list of objects to a specified file.
     * Each object is converted to a string using its toFileString() method.
     *
     * @param <T>      The generic type of the objects in the list.
     * @param fileName The name of the file to save the data to.
     * @param list     The list of objects to be saved.
     */
    public static <T> void saveToFile(String fileName, ListInterface<T> list) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("./data/" + fileName))) {
            for (int i = 1; i <= list.size(); i++) {
                T item = list.get(i);
                if (item instanceof entity.Doctor) {
                    writer.println(((entity.Doctor) item).toFileStringComplete());
                } else if (item instanceof entity.Schedule) {
                    writer.println(((entity.Schedule) item).toFileString());
                } else {
                    writer.println(item.toString());
                }
            }
            System.out.println("Data successfully saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + fileName + " - " + e.getMessage());
        }
    }

    /**
     * Loads data from a specified file into a list.
     *
     * @param <T>      The generic type of the objects to be created.
     * @param fileName The name of the file to load data from.
     * @param list     The list to populate with the loaded data.
     * @param parser   A function that takes a string array (from a split line) and returns an object of type T.
     */
    public static <T> ListInterface<T> loadFromFile(String fileName, ListInterface<T> list, Function<String[], T> parser) {
        try (BufferedReader reader = new BufferedReader(new FileReader("./data/" + fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    // The line is already in the correct format from toFileStringComplete()
                    // Just pass it to the parser which will handle the splitting
                    T object = parser.apply(line.split("\\|"));
                    if (object != null) {
                        list.add(object);
                    }
                }
            }
            System.out.println("Data successfully loaded from " + fileName);
            return list;
        } catch (IOException e) {
            System.err.println("Could not find or read file: " + fileName + ". A new file will be created on save.");
            return list;
        }
    }
}