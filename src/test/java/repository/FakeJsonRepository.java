package repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.swynn.core.JsonRepository;

public class FakeJsonRepository implements JsonRepository {

    private final JsonNode NODE;

    public FakeJsonRepository() {
        NODE = getNodeData();
    }

    private JsonNode getNodeData() {
        final var mapper = new ObjectMapper();
        final var json = """
              {
                  "persons": [
                      {
                          "firstName": "John",
                          "lastName": "Boyd",
                          "address": "1509 Culver St",
                          "city": "Culver",
                          "zip": "97451",
                          "phone": "841-874-6512",
                          "email": "jaboyd@email.com"
                      },
                      {
                          "firstName": "Jacob",
                          "lastName": "Boyd",
                          "address": "1509 Culver St",
                          "city": "Culver",
                          "zip": "97451",
                          "phone": "841-874-6513",
                          "email": "drk@email.com"
                      },
                      {
                          "firstName": "Tenley",
                          "lastName": "Boyd",
                          "address": "1509 Culver St",
                          "city": "Culver",
                          "zip": "97451",
                          "phone": "841-874-6512",
                          "email": "tenz@email.com"
                      }
                  ],
                  "firestations": [
                      {
                        "address": "1509 Culver St",
                        "station": "3"
                      },
                      {
                        "address": "29 15th St",
                        "station": "2"
                      },
                      {
                        "address": "834 Binoc Ave",
                        "station": "3"
                      }
                  ],
                  "medicalrecords": [
                      {
                          "firstName": "John",
                          "lastName": "Boyd",
                          "birthdate": "03/06/1984",
                          "medications": [
                                "aznol:350mg",
                                "hydrapermazol:100mg"
                          ],
                          "allergies": [
                                "nillacilan"
                          ]
                      },
                      {
                          "firstName": "Jacob",
                          "lastName": "Boyd",
                          "birthdate": "03/06/1989",
                          "medications": [
                                "pharmacol:5000mg",
                                "terazine:10mg",
                                "noznazol:250mg"
                          ],
                          "allergies": []
                      },
                      {
                          "firstName": "Tenley",
                          "lastName": "Boyd",
                          "birthdate": "02/18/2012",
                          "medications": [],
                          "allergies": [
                                "peanut"
                          ]
                      }
                  ]
              }
              """;
        try {
            return mapper.readTree(json);
        } catch (final Exception e) {
            System.out.println("Error while parsing json: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayNode getJsonData(final String key) {
        return (ArrayNode) NODE.get(key);
    }
}
