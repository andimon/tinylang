package tinylanglexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TinyLangProgramToString {
    private String program;

    public TinyLangProgramToString(String filePath) {
        program = convertToString(filePath);
    }

    // To read file content into the string
    // using BufferedReader and FileReader
    private static String convertToString(String filePath) {
        // Declaring object of StringBuilder class
        StringBuilder builder = new StringBuilder();
        // try block to check for exceptions where
        // object of BufferedReader class us created
        // to read filepath
        try (BufferedReader buffer = new BufferedReader(
                new FileReader(filePath))) {

            String str;

            // Condition check via buffer.readLine() method
            // holding true upto that the while loop runs
            while ((str = buffer.readLine()) != null) {

                builder.append(str).append("\n");
            }
        }
        // Catch block to handle the exceptions
        catch (IOException e) {
//			// Print the line number here exception occured
//			// using printStackTrace() method
//			e.printStackTrace();
            System.out.println("Error : File not found");
            System.exit(1);
        }
        // Returning a string
        return builder.toString();
    }

    public String getTinyLangProgramString() {
        return program;
    }
}