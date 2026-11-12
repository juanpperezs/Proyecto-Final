import model.enums.EstadoAsiento;
import model.espacios.Asiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsientoTest {

    private Asiento asientoDePrueba;

    @BeforeEach
    void prepararDatosIniciales() {
        asientoDePrueba = new Asiento("A1", "A", 1);
    }

    @Test
    void verificarDatosInicialesDelAsiento() {
        assertEquals("A1", asientoDePrueba.getCodigoAsiento());
        assertEquals("A", asientoDePrueba.getFila());
        assertEquals(1, asientoDePrueba.getNumero());
        assertEquals(EstadoAsiento.DISPONIBLE, asientoDePrueba.getEstado());
    }

    @Test
    void verificarCambioDeEstadoAlOcuparYLiberarAsiento() {
        assertEquals(EstadoAsiento.DISPONIBLE, asientoDePrueba.getEstado());

        asientoDePrueba.ocuparAsiento();
        assertEquals(EstadoAsiento.OCUPADO, asientoDePrueba.getEstado());

        asientoDePrueba.liberarAsiento();
        assertEquals(EstadoAsiento.DISPONIBLE, asientoDePrueba.getEstado());
    }
}