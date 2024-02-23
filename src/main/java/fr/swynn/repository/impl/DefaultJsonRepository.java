package fr.swynn.repository.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.swynn.repository.JsonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class DefaultJsonRepository implements JsonRepository {

    private static final String FILENAME = "data.json";

    private static final Logger LOGGER;
    private static final String FILE_NOT_FOUND;
    private static final ObjectMapper MAPPER;

    private final JsonNode json;

    static {
        LOGGER = LoggerFactory.getLogger(DefaultJsonRepository.class);
        FILE_NOT_FOUND = "Unable to find the data file.";
        MAPPER = new ObjectMapper();
    }

    public DefaultJsonRepository() {
        json = getJsonFile(FILENAME);
    }

    private JsonNode getJsonFile(final String fileName) {
        try {
            final var inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            final var content = getFileContent(inputStream);
            return MAPPER.readTree(content);
        } catch (final Exception e) {
            LOGGER.error(FILE_NOT_FOUND);
            return null;
        }
    }

    private String getFileContent(final InputStream stream) {
        try {
            final var content = new String(stream.readAllBytes());
            stream.close();
            return content;
        } catch (final Exception e) {
            LOGGER.error(FILE_NOT_FOUND);
            return null;
        }
    }

    @Override
    public ArrayNode getJsonData(final String key) {
        try {
            return (ArrayNode) json.get(key);
        } catch (final Exception e) {
            LOGGER.error(FILE_NOT_FOUND);
            return null;
        }
    }
}
