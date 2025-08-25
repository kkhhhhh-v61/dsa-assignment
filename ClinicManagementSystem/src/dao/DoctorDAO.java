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