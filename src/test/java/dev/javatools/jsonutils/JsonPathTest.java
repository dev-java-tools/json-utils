package dev.javatools.jsonutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.javatools.jsonutils.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonPathTest {

    private ClassLoader classLoader = getClass().getClassLoader();
    private Person person;
    private String inputString;
    private File inputFile;
    private ArrayList<String> allPaths;
    private ArrayList<String> allUniquePaths;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {

        Path jsonSampleInputFilePath = Path.of(classLoader.getResource("jsonPath/sample-input.json").getPath());
        inputFile = new File(jsonSampleInputFilePath.toString());
        inputString = Files.readString(jsonSampleInputFilePath);
        person = objectMapper.readValue(inputString, Person.class);


        Path sampleOutputPropertiesFilePath = Path.of(classLoader.getResource("jsonPath/sample-output.txt").getPath());
        allPaths = new ArrayList<>(Files.readAllLines(sampleOutputPropertiesFilePath));

        Path sampleUniquePathsOutputPropertiesFilePath = Path.of(classLoader.getResource("jsonPath/sample-unique-paths-output.txt").getPath());
        allUniquePaths = new ArrayList<>(Files.readAllLines(sampleUniquePathsOutputPropertiesFilePath));
    }

    @Test
    void getPathsFromJsonString() {
        Set<String> paths = JsonPath.getAllPaths(inputString);
        assertTrue(paths.size() == allPaths.size());
        for (String path : paths) {
            assertTrue(allPaths.contains(path));
        }
    }

    @Test
    void getUniquePathsFromJsonString() {
        Set<String> paths = JsonPath.getAllUniquePaths(inputString);
        assertTrue(paths.size() == allUniquePaths.size());
        for (String path : paths) {
            assertTrue(allUniquePaths.contains(path));
        }
    }

    @Test
    void getPathsFromJsonFile() {
        Set<String> paths = JsonPath.getAllPaths(inputFile);
        assertTrue(paths.size() == allPaths.size());
        for (String path : paths) {
            assertTrue(allPaths.contains(path));
        }
    }

    @Test
    void getUniquePathsFromJsonFile() {
        Set<String> paths = JsonPath.getAllUniquePaths(inputFile);
        assertTrue(paths.size() == allUniquePaths.size());
        for (String path : paths) {
            assertTrue(allUniquePaths.contains(path));
        }
    }

    @Test
    void getPathsFromJsonObject() {
        Set<String> paths = JsonPath.getAllPaths(person);
        assertTrue(paths.size() == allPaths.size());
        for (String path : paths) {
            assertTrue(allPaths.contains(path));
        }
    }

    @Test
    void getUniquePathsFromJsonObject() {
        Set<String> paths = JsonPath.getAllUniquePaths(person);
        assertTrue(paths.size() == allUniquePaths.size());
        for (String path : paths) {
            assertTrue(allUniquePaths.contains(path));
        }
    }
}