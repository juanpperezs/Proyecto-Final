import model.core.Pelicula;
import model.enums.Clasificacion;
import model.enums.EstadoPelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeliculaTest {

    private Pelicula peliculaDePrueba;

    @BeforeEach
    void prepararDatosIniciales() {
        peliculaDePrueba = new Pelicula("PEL-01", "Interestelar", "Ciencia Ficcion", 169, Clasificacion.MAYORES_12);
    }

    @Test
    void verificarDatosInicialesDeLaPelicula() {
        assertEquals("PEL-01", peliculaDePrueba.getCodigoPelicula());
        assertEquals("Interestelar", peliculaDePrueba.getNombrePelicula());
        assertEquals("Ciencia Ficcion", peliculaDePrueba.getGenero());
        assertEquals(169, peliculaDePrueba.getDuracionMinutos());
        assertEquals(Clasificacion.MAYORES_12, peliculaDePrueba.getClasificacion());
        assertEquals(EstadoPelicula.DISPONIBLE, peliculaDePrueba.getEstado());
    }

    @Test
    void verificarActualizacionDeEstadoYDatosDeLaPelicula() {
        peliculaDePrueba.setEstado(EstadoPelicula.NO_DISPONIBLE);
        peliculaDePrueba.setDuracionMinutos(175);

        assertEquals(EstadoPelicula.NO_DISPONIBLE, peliculaDePrueba.getEstado());
        assertEquals(175, peliculaDePrueba.getDuracionMinutos());
    }
}