package dev.javatools.jsonutils;

import dev.javatools.maputils.MapCreator;
import dev.javatools.maputils.MapPaths;
import dev.javatools.maputils.helpers.Format;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Get the sorted list of all the paths (full json path) in a given Json
 *
 * <pre>
 *     Example:
 *          {
 *              "name": "James Butt",
 *              "age": 25,
 *              "dateOfBirth": "1995-05-15",
 *              "friends": [
 *                  {
 *                      "name": "Josephine Darakjy",
 *                      "age": 22,
 *                      "dateOfBirth": "1997-01-24",
 *                      "associatedAddresses": [
 *                          {
 *                              "street": "25 E 75th St #69",
 *                              "city": "Los Angeles",
 *                              "state": "CA",
 *                              "zip": "90034"
 *                          }
 *                      ]
 *                  }
 *              ],
 *              "primaryAddress": {
 *                  "street": "6649 N Blue Gum St",
 *                  "city": "New Orleans",
 *                  "state": "LA",
 *                  "zip": "70116"
 *              }
 *          }
 *
 *      The above Json will generate the below properties
 *
 *          age
 *          dateOfBirth
 *          friends[0].age
 *          friends[0].associatedAddresses[0].city
 *          friends[0].associatedAddresses[0].state
 *          friends[0].associatedAddresses[0].street
 *          friends[0].associatedAddresses[0].zip
 *          friends[0].dateOfBirth
 *          friends[0].name
 *          name
 *          primaryAddress.city
 *          primaryAddress.state
 *          primaryAddress.street
 *          primaryAddress.zip
 * </pre>
 */
public class JsonPath {

    /**
     * @param jsonString json String to process
     * @return Sorted set of all the paths in the Json
     * In this the paths repeat with the position number if there are multiple objects within the list/set/array.
     * To get unique paths, use getUniquePaths.
     */
    public static Set<String> getAllPaths(String jsonString) {
        Map inputJson = MapCreator.create(jsonString, Format.JSON);
        return MapPaths.getPaths(inputJson);
    }

    /**
     * @param jsonFile File containing the json String to process
     * @return Sorted set of all the paths in the Json
     * In this the paths repeat with the position number if there are multiple objects within the list/set/array.
     * To get unique paths, use getUniquePaths.
     */
    public static Set<String> getAllPaths(File jsonFile) {
        Map inputJson = MapCreator.create(jsonFile, Format.JSON);
        return MapPaths.getPaths(inputJson);
    }

    /**
     * @param customJavaModel Custom java model that will be converted to Json String and process
     * @return Sorted set of all the paths in the Json
     * In this the paths repeat with the position number if there are multiple objects within the list/set/array.
     * To get unique paths, use getUniquePaths.
     */
    public static Set<String> getAllPaths(Object customJavaModel) {
        Map inputJson = MapCreator.create(customJavaModel);
        return MapPaths.getPaths(inputJson);
    }

    /**
     * @param jsonString json String to process
     * @return Sorted set of all the paths in the Json
     */
    public static Set<String> getAllUniquePaths(String jsonString) {
        Map inputJson = MapCreator.create(jsonString, Format.JSON);
        return MapPaths.getUniquePaths(inputJson);
    }

    /**
     * @param jsonFile File containing the json String to process
     * @return Sorted set of all the paths in the Json
     */
    public static Set<String> getAllUniquePaths(File jsonFile) {
        Map inputJson = MapCreator.create(jsonFile, Format.JSON);
        return MapPaths.getUniquePaths(inputJson);
    }

    /**
     * @param customJavaModel Custom java model that will be converted to Json String and process
     * @return Sorted set of all the paths in the Json
     */
    public static Set<String> getAllUniquePaths(Object customJavaModel) {
        Map inputJson = MapCreator.create(customJavaModel);
        return MapPaths.getUniquePaths(inputJson);
    }

}
