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

    public static void main(String[] args) {
        // initialize the file
        String workingDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        List<String> fileNames = getTextFiles(workingDirectory);

        for (String fileName : fileNames) {
            File file = new File(workingDirectory + fileName);

            try {
                // Take contents of file and turn it into a string array
                String text = FileUtils.readFileToString(file, "UTF-8");
                String[] strList = text.split("\\s");
                int numberOfWords = getNumberOfWords(strList);
                int numOfUniqueWords = numberOfUniqueWords(strList);

                System.out.println("File: " + fileName);
                System.out.println("Out of " + numberOfWords + " words, " +
                        "There are " + numOfUniqueWords + " unique words in this file");
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
