package fr.swynn.core;

import com.fasterxml.jackson.databind.node.ArrayNode;

public interface JsonRepository {

    ArrayNode getJsonData(String key);
}
