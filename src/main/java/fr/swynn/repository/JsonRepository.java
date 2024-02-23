package fr.swynn.repository;

import com.fasterxml.jackson.databind.node.ArrayNode;

public interface JsonRepository {

    ArrayNode getJsonData(String key);
}
