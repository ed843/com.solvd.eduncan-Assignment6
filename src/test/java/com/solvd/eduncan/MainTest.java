package com.solvd.eduncan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// I wanted to try out Test-Driven Development in Java

@SuppressWarnings("DataFlowIssue")
class MainTest {

    @Test
    void testNumberOfUniqueWords() {

        // Test case 1: Basic test with unique words
        String[] words1 = {"apple", "banana", "cherry"};
        assertEquals(3, Main.numberOfUniqueWords(words1));

        // Test case 2: Duplicate words
        String[] words2 = {"apple", "banana", "apple", "date", "banana"};
        assertEquals(3, Main.numberOfUniqueWords(words2));

        // Test case 3: Case insensitivity
        String[] words3 = {"Apple", "apple", "APPLE", "Banana", "banana"};
        assertEquals(2, Main.numberOfUniqueWords(words3));

        // Test case 4: Words with leading/trailing spaces
        String[] words4 = {" apple ", "  banana", "cherry  ", " date "};
        assertEquals(4, Main.numberOfUniqueWords(words4));

        // Test case 5: Empty array
        String[] words5 = {};
        assertEquals(0, Main.numberOfUniqueWords(words5));

        // Test case 6: Array with empty strings and spaces
        String[] words6 = {"", " ", "  ", "apple", ""};
        assertEquals(1, Main.numberOfUniqueWords(words6));

        // Test case 7: Null array
        assertThrows(NullPointerException.class, () -> Main.numberOfUniqueWords(null));

        // Test case 8: Array with null elements
        String[] words8 = {"apple", null, "banana", null};
        assertEquals(2, Main.numberOfUniqueWords(words8));
    }

    @Test
    void testGetNumberOfWords() {
        // Test case 1: Basic test with unique words
        String[] words1 = {"apple", "banana", "cherry"};
        assertEquals(3, Main.getNumberOfWords(words1));

        // Test case 2: Words with spaces
        String[] words2 = {"apple ", " banana", " cherry "};
        assertEquals(3, Main.getNumberOfWords(words2));

        // Test case 3: Empty strings and spaces
        String[] words3 = {"", " ", "  ", "apple", ""};
        assertEquals(1, Main.getNumberOfWords(words3));

        // Test case 4: Empty array
        String[] words4 = {};
        assertEquals(0, Main.getNumberOfWords(words4));

        // Test case 5: Array with only empty strings and spaces
        String[] words5 = {"", " ", "  "};
        assertEquals(0, Main.getNumberOfWords(words5));

        // Test case 6: Mix of words, empty strings, and spaces
        String[] words6 = {"apple", "", "banana", " ", "cherry", "  "};
        assertEquals(3, Main.getNumberOfWords(words6));

        // Test case 7: Words with special characters
        String[] words7 = {"hello!", "world,", "how's", "it", "going?"};
        assertEquals(5, Main.getNumberOfWords(words7));

        // Test case 8: Words with numbers
        String[] words8 = {"1st", "2nd", "3rd"};
        assertEquals(3, Main.getNumberOfWords(words8));

        // Test case 9: Single-character words
        String[] words9 = {"a", "b", "c"};
        assertEquals(3, Main.getNumberOfWords(words9));

        // Test case 10: Null array (assuming the method should handle null input)
        assertThrows(NullPointerException.class, () -> Main.getNumberOfWords(null));
    }

    @TempDir
    Path tempDir;

    @Test
    void testGetTextFiles() throws IOException {
        // Create test files
        File txtFile1 = new File(tempDir.toFile(), "file1.txt");
        File txtFile2 = new File(tempDir.toFile(), "file2.TXT");
        File nonTxtFile = new File(tempDir.toFile(), "file3.pdf");
        File subDir = new File(tempDir.toFile(), "subdir");
        File txtFileInSubDir = new File(subDir, "file4.txt");

        txtFile1.createNewFile();
        txtFile2.createNewFile();
        nonTxtFile.createNewFile();
        subDir.mkdir();
        txtFileInSubDir.createNewFile();

        // Test the getTextFiles method
        List<String> result = Main.getTextFiles(tempDir.toString());

        // Assert the results
        assertEquals(2, result.size());
        assertTrue(result.contains("file1.txt"));
        assertTrue(result.contains("file2.TXT"));
        assertFalse(result.contains("file3.pdf"));
        assertFalse(result.contains("file4.txt"));
    }

    @Test
    void testGetTextFilesEmptyDirectory() throws IOException {
        List<String> result = Main.getTextFiles(tempDir.toString());
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTextFilesNonExistentDirectory() {
        List<String> result = Main.getTextFiles("/non/existent/directory");
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTextFilesWithFile() throws IOException {
        File file = new File(tempDir.toFile(), "singlefile.txt");
        file.createNewFile();

        List<String> result = Main.getTextFiles(file.getAbsolutePath());
        assertTrue(result.isEmpty());
    }
}