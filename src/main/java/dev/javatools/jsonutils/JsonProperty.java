package dev.javatools.jsonutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.javatools.maputils.MapCreator;
import dev.javatools.maputils.MapProperties;
import dev.javatools.maputils.MapProperty;
import dev.javatools.maputils.MapUpdate;
import dev.javatools.maputils.helpers.Format;

import java.io.File;
import java.util.Map;

/**
 * Get the sorted list of all the properties (full json path and value) in a given Json
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
 *          age=25
 *          dateOfBirth=1995-05-15
 *          friends[0].age=22
 *          friends[0].associatedAddresses[0].city=Los Angeles
 *          friends[0].associatedAddresses[0].state=CA
 *          friends[0].associatedAddresses[0].street=25 E 75th St #69
 *          friends[0].associatedAddresses[0].zip=90034
 *          friends[0].dateOfBirth=1997-01-24
 *          friends[0].name=Josephine Darakjy
 *          name=James Butt
 *          primaryAddress.city=New Orleans
 *          primaryAddress.state=LA
 *          primaryAddress.street=6649 N Blue Gum St
 *          primaryAddress.zip=70116
 *
 * Path format examples
 *
 * Possible values for destination path
 *
 * 1. name - field at the root level
 * 2. primaryAddress.street - field in an inner object
 * 3. associatedAddresses[] - add an (any)object to a list, object will be added at the end of the list
 * 4. associatedAddresses[1] - will add associated object at location 1 in the list,
 *    if there is an object that is already existing at that location in that list, then it will be overwritten}
 * 5. associatedAddresses[{state=CA}].street street - address will be updated with the value where associatedAddresses state is california
 * 5. associatedAddresses[{state:CA}, {zip:94599}].street - street address will be updated with the value where associatedAddresses state is california and zip is 94599
 * 6. friends[{name=Art Venere}].age - updates the age of a friend whos name is "Art Venere"
 * 7. friends[{name=Art Venere}].primaryAddress.street
 * 8. friends[{name=Art Venere}].associatedAddresses[] {associated address of the friend should be added at the end of the list, if exists, override}
 * 9. friends[{name=Art Venere}].associatedAddresses[{state=CA}].street
 * 10. friends[{name=Art Venere}].associatedAddresses[{state=CA},{city:Irving}].street
 *
 * Current implementation will assume that the map has 1. map or 2. list or any terminal object (String/Integer/..) as the value for any given key.
 *
 * </pre>
 */
public class JsonProperty {

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * @param jsonString valid Json structure in String object
     * @return Map containing all the properties in the input Json
     */
    public static Map<String, Object> getAllProperties(String jsonString) {
        Map inputJson = MapCreator.create(jsonString, Format.JSON);
        return MapProperties.getProperties(inputJson);
    }

    /**
     * @param jsonFile valid Json structure in the File
     * @return Map containing all the properties in the input Json
     */
    public static Map<String, Object> getAllProperties(File jsonFile) {
        Map inputJson = MapCreator.create(jsonFile, Format.JSON);
        return MapProperties.getProperties(inputJson);
    }

    /**
     * @param customJavaModel custom java model
     * @return Map containing all the properties in the input Json
     */
    public static Map<String, Object> getAllProperties(Object customJavaModel) {
        Map inputJson = MapCreator.create(customJavaModel);
        return MapProperties.getProperties(inputJson);
    }

    /**
     * See the class level documentation for property path details
     *
     * @param jsonPath   path of the field
     * @param jsonString json String to process
     * @return Value in the json path
     */
    public static Object getProperty(String jsonPath, String jsonString) {
        Map inputJson = MapCreator.create(jsonString, Format.JSON);
        return MapProperty.get(jsonPath, inputJson);
    }

    /**
     * See the class level documentation for property path details
     *
     * @param jsonPath path of the field
     * @param jsonFile File containing the json String to process
     * @return value of the field in path
     */
    public static Object getProperty(String jsonPath, File jsonFile) {
        Map inputJson = MapCreator.create(jsonFile, Format.JSON);
        return MapProperty.get(jsonPath, inputJson);
    }

    /**
     * See the class level documentation for property path details
     *
     * @param jsonPath        path of the field
     * @param customJavaModel Custom java model
     * @return value of the field in the path
     */
    public static Object getProperty(String jsonPath, Object customJavaModel) {
        Map inputJson = MapCreator.create(customJavaModel);
        return MapProperty.get(jsonPath, inputJson);
    }

    /**
     * You can add, update, delete any element in the json by specifying the path in the following format.
     * <pre>{@code
     * Example 1:
     *  person.address.zip - will perform operations on the element at that location in the map.
     *  Map
     *      person: address
     *          address : zip
     *              zip : <value> -- Performs the operation this field
     * Example 2:
     *  person.address[2].zip - will perform operations on the element at that location in the map.
     *  Map
     *      person: address[]
     *          address[1]: .....
     *          address[2] : zip
     *              zip : <value> -- Performs the operation this field
     *
     *
     * Here are some sample paths
     * 1. name - field at the root level
     * 2. primaryAddress.street - field in an inner object
     * 3. associatedAddresses[] - add an (any)object to a list, object will be added at the end of the list
     * 4. associatedAddresses[1] - will add associated object at location 1 in the list,
     *    if there is an object that is already existing at that location in that list, then it will be overwritten}
     * 5. associatedAddresses[{state=CA}].street - street address will be updated with the value where associatedAddresses state is CA
     * 5. associatedAddresses[{state:CA}, {zip:94599}].street - street address will be updated with the value where associatedAddresses state is CA and zip is 94599
     * 6. friends[{name=Art Venere}].age - updates the age of a friend who's name is "Art Venere"
     * 7. friends[{name=Art Venere}].primaryAddress.street
     * 8. friends[{name=Art Venere}].associatedAddresses[] {associated address of the friend should be added at the end of the list, if exists, override}
     * 9. friends[{name=Art Venere}].associatedAddresses[{state=CA}].street
     * 10. friends[{name=Art Venere}].associatedAddresses[{state=CA},{city:Irving}].street
     * }</pre>
     *
     * @param jsonPath   Json path of the field
     * @param jsonString the Json that needs to be updated
     * @param value      the value that needs to be updated
     * @return String representation of the updated Json.
     */
    public static String setProperty(final String jsonPath, final String jsonString, final Object value) {
        try {
            if (null != jsonPath && null != jsonString) {
                Map inputMap = MapCreator.create(jsonString, Format.JSON);
                MapUpdate.set(jsonPath, inputMap, value);
                return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(inputMap);
            } else {
                throw new JsonUtilsException("Not a valid input, Json Path and Input Json are mandatory fields.");
            }
        } catch (JsonProcessingException jsonProcessingException) {
            throw new JsonUtilsException(jsonProcessingException);
        }
    }

}
