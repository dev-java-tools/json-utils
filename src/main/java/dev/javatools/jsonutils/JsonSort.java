package dev.javatools.jsonutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.javatools.maputils.MapCreator;
import dev.javatools.maputils.MapSort;
import dev.javatools.maputils.helpers.Format;

import java.io.File;
import java.util.Map;

/**
 * This class has all the APIs needs to sort a Json.
 * <pre>
 * How to handle objects inside a list? Answer to that question is listKeys.
 * If the Json has a list of objects and if you need to sort the list based on a
 * specific field of an object, this feature will help.
 * Here is how it works.
 * Take the below Map as an example
 * {
 *      "members": [
 *          {
 *              "name": "John",
 *              "age": 22
 *          },
 *          {
 *              "name": "Bob",
 *              "age": 18
 *          }
 *      ]
 *  }
 * }
 * </pre>
 * If you need the sort the above Map based on the member name. You need to pass
 * listKey.put("members[]", "name");
 * If you need the sort the above Map based on the member age. You need to pass
 * listKey.put("members[]", "age");
 * The key of this listKey map can be the json path until the list, and the value is the field in the object.
 */
public class JsonSort {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * @param inputJson String representation of the json that has to be sorted
     * @return sorted json String
     */
    public static String sort(String inputJson) {
        try {
            Map inputMap = MapCreator.create(inputJson, Format.JSON);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(MapSort.getSortedMap(inputMap));
        } catch (JsonProcessingException jsonProcessingException) {
            throw new JsonUtilsException(jsonProcessingException);
        }
    }

    /**
     * @param inputJson json to be sorted
     * @param listKeys  Please see the documentation at class level.
     * @return sorted json
     */
    public static String sort(String inputJson, Map<String, String> listKeys) {
        try {
            Map inputMap = MapCreator.create(inputJson, Format.JSON);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(MapSort.getSortedMap(inputMap, listKeys));
        } catch (JsonProcessingException jsonProcessingException) {
            throw new JsonUtilsException(jsonProcessingException);
        }
    }

    /**
     * @param inputJson File containing the String representation of the json that has to be sorted
     * @return sorted json String
     */
    public static String sort(File inputJson) {
        try {
            Map inputMap = MapCreator.create(inputJson, Format.JSON);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(MapSort.getSortedMap(inputMap));
        } catch (JsonProcessingException jsonProcessingException) {
            throw new JsonUtilsException(jsonProcessingException);
        }
    }

    /**
     * @param inputJson File containing the String representation of the json that has to be sorted
     * @param listKeys  Please see the documentation at class level.
     * @return sorted json String
     */
    public static String sort(File inputJson, Map<String, String> listKeys) {
        try {
            Map inputMap = MapCreator.create(inputJson, Format.JSON);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(MapSort.getSortedMap(inputMap, listKeys));
        } catch (JsonProcessingException jsonProcessingException) {
            throw new JsonUtilsException(jsonProcessingException);
        }
    }

    /**
     * @param inputJson Custom java model that needs to be converted to sorted json String.
     * @return sorted json String
     */
    public static String sort(Object inputJson) {
        try {
            Map inputMap = MapCreator.create(inputJson);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(MapSort.getSortedMap(inputMap));
        } catch (JsonProcessingException jsonProcessingException) {
            throw new JsonUtilsException(jsonProcessingException);
        }
    }

    /**
     * @param inputJson Custom java model that needs to be converted to sorted json String.
     * @param listKeys  Please see the documentation at class level.
     * @return sorted json String
     */
    public static String sort(Object inputJson, Map<String, String> listKeys) {
        try {
            Map inputMap = MapCreator.create(inputJson);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(MapSort.getSortedMap(inputMap, listKeys));
        } catch (JsonProcessingException jsonProcessingException) {
            throw new JsonUtilsException(jsonProcessingException);
        }
    }

}
