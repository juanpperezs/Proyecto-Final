import model.core.Cliente;
import model.enums.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTest {

    private Cliente clienteDePrueba;

    @BeforeEach
    void prepararDatosIniciales() {
        clienteDePrueba = new Cliente("1094888999", "Camila Restrepo", TipoCliente.ESTUDIANTE);
    }

    @Test
    void verificarDatosInicialesDelCliente() {
        assertEquals("1094888999", clienteDePrueba.getIdentificacion());
        assertEquals("Camila Restrepo", clienteDePrueba.getNombre());
        assertEquals(TipoCliente.ESTUDIANTE, clienteDePrueba.getTipoCliente());
    }

    @Test
    void verificarActualizacionDeDatosDelCliente() {
        clienteDePrueba.setNombre("Camila Restrepo Gomez");
        clienteDePrueba.setTipoCliente(TipoCliente.GENERAL);

        assertEquals("Camila Restrepo Gomez", clienteDePrueba.getNombre());
        assertEquals(TipoCliente.GENERAL, clienteDePrueba.getTipoCliente());
    }
}