import model.enums.EstadoAsiento;
import model.enums.EstadoSala;
import model.espacios.Asiento;
import model.espacios.Sala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalaTest {

    private Sala sala;

    @BeforeEach
    void setUp() {
        List<Asiento> asientos = new ArrayList<>();
        asientos.add(new Asiento("A1", "A", 1));
        asientos.add(new Asiento("A2", "A", 2));

        sala = new Sala("S01", "Sala 1", 12, EstadoSala.DISPONIBLE, asientos);
    }

    @Test
    void testDatosYAsientosDeSala() {
        assertEquals("S01", sala.getCodigo());
        assertEquals(EstadoSala.DISPONIBLE, sala.getEstado());
        assertEquals(2, sala.getListaAsientos().size());
        assertEquals("A1", sala.getListaAsientos().get(0).getCodigoAsiento());
    }

    @Test
    void testCambiarEstadoAsiento() {
        Asiento asiento = sala.getListaAsientos().get(0);
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());

        asiento.setEstado(EstadoAsiento.OCUPADO);
        assertEquals(EstadoAsiento.OCUPADO, asiento.getEstado());

        asiento.setEstado(EstadoAsiento.DISPONIBLE);
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }
}