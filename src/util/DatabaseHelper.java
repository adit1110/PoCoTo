/**
 * Utility class for saving and loading Bear game data to and from a file.
 * Uses JSON format to store Bear object attributes such as hunger, health, sleep, and happiness.
 *
 * @author Bhavya Sharma
 */

 package util;

 import model.Bear;
 import com.google.gson.Gson;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 
 public class DatabaseHelper {
 
     private static final String SAVE_FILE = "savefile.json";
 
     /**
      * Writes the given Bear object to a file in JSON format.
      *
      * @param bear The Bear object to be saved.
      */
     public static void writeToFile(Bear bear) {
         try (FileWriter writer = new FileWriter(SAVE_FILE)) {
             Gson gson = new Gson();
             gson.toJson(bear, writer);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 
     /**
      * Reads and reconstructs a Bear object from the save file.
      *
      * @param bearType The specific Bear subclass type to reconstruct (Po, Co, or To).
      * @return The reconstructed Bear object or null if an error occurs.
      */
     public static Bear readFromFile(Class<? extends Bear> bearType) {
         try (FileReader reader = new FileReader(SAVE_FILE)) {
             Gson gson = new Gson();
             return gson.fromJson(reader, bearType);
         } catch (IOException e) {
             e.printStackTrace();
             return null;
         }
     }
 }