package model.core;

import java.time.LocalDate;
import java.util.List;
import model.enums.TipoCliente;
import model.espacios.Sala;
import model.exceptions.AsientoOcupadoException;
import model.exceptions.CodigoPeliculaDuplicadoException;
import model.exceptions.CodigoSalaDuplicadoException;
import model.exceptions.FuncionInexistenteException;
import model.exceptions.SalaFueraDeServicioException;
import model.exceptions.VentaInexistenteException;

public interface ICinema {

    // Gestión de películas
    Pelicula buscarPeliculaPorCodigo(String codigo);
    void registrarPelicula(Pelicula pelicula) throws CodigoPeliculaDuplicadoException;
    boolean deshabilitarPelicula(String codigo);
    List<Pelicula> getListaPeliculas();

    // Gestión de salas
    Sala buscarSalaPorCodigo(String codigo);
    void registrarSala(Sala sala) throws CodigoSalaDuplicadoException;
    boolean deshabilitarSala(String codigo);
    List<Sala> getListaSalas();

    // Gestión de clientes
    Cliente buscarClientePorId(String identificacion);
    boolean registrarCliente(Cliente cliente);
    List<Cliente> getListaClientes();

    // Gestión de funciones y tarifas
    Funcion buscarFuncionPorCodigo(String codigo);
    List<Funcion> funcionesPorFecha(LocalDate fecha);
    void programarFuncion(Funcion funcion) throws SalaFueraDeServicioException;
    List<Funcion> getListaFunciones();
    double calcularValorEntrada(Funcion funcion, TipoCliente tipoCliente);

    // Gestión de ventas
    Venta venderEntrada(String codigoVenta, String identificacionCliente, String codigoFuncion, String codigoAsiento)
            throws FuncionInexistenteException, AsientoOcupadoException;
    Venta buscarVentaPorCodigo(String codigo);
    void cancelarVenta(String codigoVenta) throws VentaInexistenteException;
    List<Venta> getListaVentas();

    // Reportes
    int totalEntradasVendidas();
    double totalIngresos();
    Pelicula peliculaMasVendida();
    String ocupacionFuncion(String codigoFuncion) throws FuncionInexistenteException;
}