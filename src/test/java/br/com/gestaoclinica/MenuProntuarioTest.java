package br.com.gestaoclinica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuProntuarioTest {

    @Test
    void deveExistirClasseMenuProntuario() {
        assertDoesNotThrow(
                () -> Class.forName("br.com.gestaoclinica.MenuProntuario"));
    }
}