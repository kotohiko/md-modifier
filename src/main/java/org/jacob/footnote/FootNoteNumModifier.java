package org.jacob.footnote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A utility program to modify text in a file by incrementing numbers
 * in the pattern "[^0-number]" within a specified range.
 * <p>
 * The program reads a file, searches for matches of the specified pattern,
 * increments the numeric portion of matches within the range `[start, end]` by a given `increment`,
 * and writes the updated content back to the file.
 * <p>
 * Parameters:
 * - filePath: The path to the input file.
 * - start: The starting number of the range to process.
 * - end: The ending number of the range to process.
 * - increment: The value by which to increment the matched numbers.
 */
public class FootNoteNumModifier {
    public static void main(String[] args) {
        // File path of the input text file
        String filePath = "input.txt";

        // Range of numbers to modify
        int start = 105; // Start of the range
        int end = 179;   // End of the range

        // Increment value for matched numbers
        int increment = 1;

        try {
            // Read the file content into a string
            Path path = Paths.get(filePath);
            String content = new String(Files.readAllBytes(path));

            // Regular expression to match patterns like [^0-number]
            String regex = "\\[\\^0-(\\d+)]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);

            // StringBuilder to construct the updated content
            StringBuilder updatedContent = new StringBuilder();

            // Process each match found by the regex
            while (matcher.find()) {
                // Extract the numeric part of the match
                int number = Integer.parseInt(matcher.group(1));

                // Check if the number is within the specified range
                if (number >= start && number <= end) {
                    // Increment the number and create the replacement string
                    int newNumber = number + increment;
                    String replacement = "[^0-" + newNumber + "]";

                    // Append the modified content
                    matcher.appendReplacement(updatedContent, replacement);
                }
            }

            // Append any remaining content after the last match
            matcher.appendTail(updatedContent);

            // Write the updated content back to the file
            Files.write(path, updatedContent.toString().getBytes());
            System.out.println("File successfully updated!");
        } catch (IOException e) {
            System.err.println("Error reading or writing the file: " + e.getMessage());
        }
    }
}