package fr.swynn.database.data;

import com.fasterxml.jackson.databind.node.ArrayNode;

public interface JsonRepository {

    ArrayNode getJsonData(String key);
}
