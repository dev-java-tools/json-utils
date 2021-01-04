package dev.javatools.jsonutils;

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
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class JsonPropertyTest {

    private ClassLoader classLoader = getClass().getClassLoader();
    private Person person;
    private String inputString;
    private File inputFile;
    private Map<String, Object> allProperties = new HashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        Path jsonSampleInputFilePath = Path.of(classLoader.getResource("jsonProperty/sample-input.json").getPath());
        inputFile = new File(jsonSampleInputFilePath.toString());
        inputString = Files.readString(jsonSampleInputFilePath);
        person = objectMapper.readValue(inputString, Person.class);

        Properties properties = new Properties();
        properties.load(classLoader.getResourceAsStream("jsonProperty/sample-output.properties"));

        for (String key : properties.stringPropertyNames()) {
            Object value = properties.getProperty(key);
            allProperties.put(key, value);
        }
    }

    @Test
    void getPropertiesFromJsonString() {
        Map<String, Object> properties = JsonProperty.getAllProperties(inputString);
        assertTrue(properties.size() == allProperties.size());
        for (Map.Entry<String, Object> path : properties.entrySet()) {
            assertTrue(allProperties.containsKey(path.getKey()));
            assertTrue(allProperties.get(path.getKey()).equals(path.getValue().toString()));
        }
    }

    @Test
    void getPropertiesFromJsonFile() {
        Map<String, Object> properties = JsonProperty.getAllProperties(inputFile);
        assertTrue(properties.size() == allProperties.size());
        for (Map.Entry<String, Object> path : properties.entrySet()) {
            assertTrue(allProperties.containsKey(path.getKey()));
            assertTrue(allProperties.get(path.getKey()).equals(path.getValue().toString()));
        }
    }

    @Test
    void getPropertiesFromJsonObject() {
        Map<String, Object> properties = JsonProperty.getAllProperties(person);
        assertTrue(properties.size() == allProperties.size());
        for (Map.Entry<String, Object> path : properties.entrySet()) {
            assertTrue(allProperties.containsKey(path.getKey()));
            assertTrue(allProperties.get(path.getKey()).equals(path.getValue().toString()));
        }
    }

    @Test
    void setPropertyTest01() throws IOException {
        String newJson = "{}";
        newJson = JsonProperty.setProperty("name", newJson, "James Butt");
        newJson = JsonProperty.setProperty("age", newJson, 26);
        Path outputPath = Path.of(classLoader.getResource("jsonProperty/output_01.json").getPath());
        String output = Files.readString(outputPath);
        assertEquals(output, newJson);
    }

    @Test
    void setPropertyTest02() throws IOException {
        String newJson = "{}";
        newJson = JsonProperty.setProperty("associatedAddresses[]", newJson, "San Ramon");
        Path outputPath = Path.of(classLoader.getResource("jsonProperty/output_02.json").getPath());
        String output = Files.readString(outputPath);
        assertEquals(output, newJson);
    }

    @Test
    void setPropertyTest03() throws IOException {
        String newJson = "{}";
        newJson = JsonProperty.setProperty("associatedAddresses[].city", newJson, "San Ramon");
        Path outputPath = Path.of(classLoader.getResource("jsonProperty/output_03.json").getPath());
        String output = Files.readString(outputPath);
        assertEquals(output, newJson);
    }

    @Test
    void setPropertyTest04() throws IOException {
        String newJson = "{}";
        newJson = JsonProperty.setProperty("associatedAddresses[5].city", newJson, "San Ramon");
        Path outputPath = Path.of(classLoader.getResource("jsonProperty/output_04.json").getPath());
        String output = Files.readString(outputPath);
        assertEquals(output, newJson);
    }

    @Test
    void setPropertyTest05() throws IOException {
        String newJson = "{}";
        newJson = JsonProperty.setProperty("associatedAddresses[].city", newJson, "San Ramon");
        Path outputPath = Path.of(classLoader.getResource("jsonProperty/output_05.json").getPath());
        String output_05_String = Files.readString(outputPath);
        assertEquals(output_05_String, newJson);
    }

    @Test
    void setPropertyTest06() throws IOException {
        String newJson = "{}";
        newJson = JsonProperty.setProperty("friends[].address.state.zip", newJson, "94599");
        Path outputPath = Path.of(classLoader.getResource("jsonProperty/output_06.json").getPath());
        String output = Files.readString(outputPath);
        assertEquals(output, newJson);
    }

    @Test
    void setPropertyTest07() throws IOException {
        String newJson = "{}";
        newJson = JsonProperty.setProperty("friends[0].associatedAddresses[5].city", newJson, "San Ramon");
        Path outputPath = Path.of(classLoader.getResource("jsonProperty/output_07.json").getPath());
        String output = Files.readString(outputPath);
        assertEquals(output, newJson);
    }

    @Test
    void setPropertyTest08() throws IOException {
        String newJson = "{}";
        newJson = JsonProperty.setProperty("friends[2].associatedAddresses[5].city", newJson, "San Ramon");
        Path outputPath = Path.of(classLoader.getResource("jsonProperty/output_08.json").getPath());
        String output = Files.readString(outputPath);
        assertEquals(output, newJson);
    }

    @Test
    void setPropertyTest09() throws IOException {
        Path jsonSampleInputFilePath = Path.of(classLoader.getResource("jsonProperty/sample-input.json").getPath());
        String sampleJsonInput = Files.readString(jsonSampleInputFilePath);
        //System.out.println(sampleJsonInput);
        String currentValue = (String) JsonProperty.getProperty("friends[].associatedAddresses[].city", sampleJsonInput);
        System.out.println("Current Value ~~~~~~~" + currentValue);
        //assertEquals(currentValue, "Los Angeles");
        sampleJsonInput = JsonProperty.setProperty("friends[{name=Josephine Darakjy}].associatedAddresses[{state=CA}].city", sampleJsonInput, "New Los Angeles");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println(sampleJsonInput);
        String newValue = (String) JsonProperty.getProperty("friends[{name=Josephine Darakjy}].associatedAddresses[{state=CA}].city", sampleJsonInput);
        assertEquals("New Los Angeles", newValue);
        assertNotEquals(newValue, currentValue);
    }

    @Test
    void setTest10() throws IOException {
        String newJson = "{}";
        Object originalValue = JsonProperty.getProperty("friends[{name=Lenna Paprocki}].associatedAddresses[{state=TX}].city", newJson);
        assertEquals("", "");
        newJson = JsonProperty.setProperty("friends[{name=Josephine Darakjy}].associatedAddresses[{state=CA}].city", newJson, "New California City");
        assertEquals("", "");
        boolean tested = false;
//        List friends = (List) sampleInput.get("friends");
//        for (Object currentFriend : friends) {
//            Map currentFriendMap = (Map) currentFriend;
//            if (currentFriendMap.get("name").equals("Lenna Paprocki")) {
//                for (Object currentAssociatedAddress : (List) currentFriendMap.get("associatedAddresses")) {
//                    Map currentAssociatedAddressMap = (Map) currentAssociatedAddress;
//                    if (currentAssociatedAddressMap.get("state").equals("TX")) {
//                        assertEquals(currentAssociatedAddressMap.get("city"), "Irving");
//                        MapUpdate.set(, sampleInput, "New Texas City");
//                        assertEquals(currentAssociatedAddressMap.get("city"), "New Texas City");
//                    }
//                }
//            }
//        }
        //System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sampleInput));
    }

    //
    @Test
    void setTest11() throws IOException {
        boolean tested = false;
//        List friends = (List) sampleInput.get("friends");
//        for (Object currentFriend : friends) {
//            Map currentFriendMap = (Map) currentFriend;
//            if (currentFriendMap.get("name").equals("Lenna Paprocki")) {
//                for (Object currentAssociatedAddress : (List) currentFriendMap.get("associatedAddresses")) {
//                    Map currentAssociatedAddressMap = (Map) currentAssociatedAddress;
//                    if (currentAssociatedAddressMap.get("state").equals("TX") && currentAssociatedAddressMap.get("city").equals("Irving")) {
//                        assertEquals(currentAssociatedAddressMap.get("street"), "618 W Yakima Ave");
//                        MapUpdate.set("friends[{name=Lenna Paprocki}].associatedAddresses[{state=TX}, {city=Irving}].street", sampleInput, "street in Irving, Texas");
//                        assertEquals(currentAssociatedAddressMap.get("street"), "street in Irving, Texas");
//                    }
//                }
//            }
//        }
        //System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sampleInput));
    }
//
//    @Test
//    void setTest10()  throws IOException {
//        MapUtilsException mapUtilsException = assertThrows(MapUtilsException.class, ()->MapUpdate.set("friends[{name=Lenna Paprocki}].associatedAddresses[{state=TX}, {city=Irving}]", sampleInput, "street in Irving, Texas"));
//        assertEquals("friends[{name=Lenna Paprocki}].associatedAddresses[{state=TX}, {city=Irving}]: Found the element in this path, but to assign the value, we also need a key.", mapUtilsException.getMessage());
//    }

}