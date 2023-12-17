package fr.swynn.persona.data;

import fr.swynn.persona.data.PersonaService;
import fr.swynn.persona.impl.SNPersonaService;
import fr.swynn.persona.impl.UnknownPerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaServiceTest {

    private PersonaService personaService;

    @BeforeEach
    void setUp() {
        personaService = new SNPersonaService();
    }

    @Test
    void getPersona_returnPersona_existingPersona() throws UnknownPerson {
        // GIVEN a persona service
        final var firstName = "John";
        final var lastName = "Doe";
        // WHEN we get a persona
        final var persona = personaService.getPersona(firstName, lastName);
        // THEN the persona is returned
        Assertions.assertNotNull(persona);
        Assertions.assertEquals(firstName, persona.firstName());
        Assertions.assertEquals(lastName, persona.lastName());
    }
}
