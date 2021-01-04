package dev.javatools.jsonutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.javatools.jsonutils.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonSortTest {

    private ClassLoader classLoader = getClass().getClassLoader();
    private ObjectMapper objectMapper = new ObjectMapper();
    private String sampleInputString;
    private File sampleInputFile;
    private Person sampleInputObject;
    private String simpleExpectedOutputString;
    private String complexExpectedOutputString;

    private Map<String, String> listFilters;

    @BeforeEach
    void setUp() throws IOException {
        Path sampleInputFilePath = Path.of(classLoader.getResource("jsonSort/sample-input.json").getPath());
        sampleInputString = Files.readString(sampleInputFilePath);
        sampleInputObject = objectMapper.readValue(sampleInputString, Person.class);
        sampleInputFile = new File(sampleInputFilePath.toString());

        Path simpleExpectedOutputFilePath = Path.of(classLoader.getResource("jsonSort/simple-expected-output.json").getPath());
        simpleExpectedOutputString = Files.readString(simpleExpectedOutputFilePath);

        Path complexExpectedOutputFilePath = Path.of(classLoader.getResource("jsonSort/complex-expected-output.json").getPath());
        complexExpectedOutputString = Files.readString(complexExpectedOutputFilePath);

        listFilters = new HashMap<>();
        listFilters.put("associatedAddresses[]", "city");
        listFilters.put("friends[]", "name");
        listFilters.put("friends[].associatedAddresses[]", "city");
    }

    @Test
    public void getSortedJsonFromString() throws JsonProcessingException {
        String sortedJson = JsonSort.sort(sampleInputString);
        assertEquals(sortedJson, simpleExpectedOutputString);
    }

    @Test
    public void getComplexSortedJsonFromString() throws JsonProcessingException {
        String sortedJson = JsonSort.sort(sampleInputString, listFilters);
        assertEquals(sortedJson, complexExpectedOutputString);
    }

    @Test
    public void getSortedJsonFromFile() throws JsonProcessingException {
        String sortedJson = JsonSort.sort(sampleInputFile);
        assertEquals(sortedJson, simpleExpectedOutputString);
    }

    @Test
    public void getComplexSortedJsonFromFile() throws JsonProcessingException {
        String sortedJson = JsonSort.sort(sampleInputFile, listFilters);
        assertEquals(sortedJson, complexExpectedOutputString);
    }

    @Test
    public void getSortedJsonFromObject() throws JsonProcessingException {
        String sortedJson = JsonSort.sort(sampleInputObject);
        assertEquals(sortedJson, simpleExpectedOutputString);
    }

    @Test
    public void getComplexSortedJsonFromObject() throws JsonProcessingException {
        String sortedJson = JsonSort.sort(sampleInputObject, listFilters);
        assertEquals(sortedJson, complexExpectedOutputString);
    }


}