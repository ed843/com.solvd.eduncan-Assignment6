package com.solvd.eduncan;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String WORKING_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    private static final String ANSWER_FILE = WORKING_DIRECTORY + "\\answers\\answer.txt";

    public static void main(String[] args) {
        // initialize the file
        List<String> fileNames = getTextFiles(WORKING_DIRECTORY);

        for (String fileName : fileNames) {
            try {
                File file = new File(WORKING_DIRECTORY + fileName);

                // Take contents of file and turn it into a string array
                String text = FileUtils.readFileToString(file, "UTF-8");
                String[] strList = text.split("\\s");

                int numberOfWords = getNumberOfWords(strList);
                int numOfUniqueWords = numberOfUniqueWords(strList);

                String output = "File: " + fileName + "\n" +
                        "Out of " + numberOfWords + " words," + " there are " + numOfUniqueWords +
                        " unique words in this file.\n";

                // Write the answers to the answer.txt file
                FileUtils.writeStringToFile(new File(ANSWER_FILE), output, true);
            } catch (IOException e) {
                logger.severe("An error occurred while reading the file: " + fileName);
                logger.severe(e.toString());
            }
        }
    }

    public static int numberOfUniqueWords(String[] words) {
        HashSet<String> hashSet = new HashSet<>();
        for (String word : words) {
            String trimmedWord = StringUtils.trim(word);
            if (StringUtils.isNotBlank(trimmedWord)) {
                hashSet.add(StringUtils.lowerCase(trimmedWord));
            }
        }
        return hashSet.size();
    }

    public static int getNumberOfWords(String[] words) {
        int count = 0;
        for (String word : words) {
            String trimWord = StringUtils.trim(word);
            if (StringUtils.isNotBlank(trimWord)) {
                count++;
            }
        }
        return count;
    }

    // Gets all text files in directoryPath
    public static List<String> getTextFiles(String directoryPath) {
        File directory = new File(directoryPath);
        List<String> textFiles = new ArrayList<>();

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                        textFiles.add(file.getName());
                    }
                }
            }
        }

        return textFiles;
    }
}
