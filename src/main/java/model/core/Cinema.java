package model.core;

import model.enums.*;
import model.espacios.Asiento;
import model.espacios.Sala;
import model.exceptions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Cinema {
    private String nombre;
    private List<Pelicula> listaPeliculas;
    private List<Sala> listaSalas;
    private List<Funcion> listaFunciones;
    private List<Venta> listaVentas;
    private List<Cliente> listaClientes;

    public Cinema(String nombre) {
        this.nombre = nombre;
        this.listaPeliculas = new ArrayList<Pelicula>();
        this.listaSalas = new ArrayList<>();
        this.listaFunciones = new ArrayList<>();
        this.listaVentas = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
    }

    //-------------------GESTIÓN DE PELÍCULAS----------------------
    public Pelicula buscarPeliculaPorCodigo(String codigo){
        for (Pelicula pelicula : listaPeliculas){
            if (pelicula.getCodigoPelicula().equals(codigo)){
                return pelicula;
            }
        }
        return null;
    }

    public void registrarPelicula(Pelicula pelicula) throws CodigoPeliculaDuplicadoException {
        if (buscarPeliculaPorCodigo(pelicula.getCodigoPelicula()) != null){
            throw new CodigoPeliculaDuplicadoException("Ya existe una pelicula con el código: " + pelicula.getCodigoPelicula());
        }
        listaPeliculas.add(pelicula);
    }

    public boolean deshabilitarPelicula(String codigo){
        Pelicula pelicula = buscarPeliculaPorCodigo(codigo);
        if (pelicula != null){
            pelicula.setEstado(EstadoPelicula.NO_DISPONIBLE);
            return true;
        }
        return false;
    }

    //------------------GESTIÓN DE SALAS----------------------
    public Sala buscarSalaPorCodigo(String codigo){
        for (Sala sala : listaSalas){
            if (sala.getCodigo().equals(codigo)){
                return sala;
            }
        }
        return null;
    }

    public void registrarSala(Sala sala) throws CodigoSalaDuplicadoException {
        if (buscarSalaPorCodigo(sala.getCodigo()) != null){
            throw new CodigoSalaDuplicadoException("Ya existe una sala con código: " + sala.getCodigo());
        }
        listaSalas.add(sala);
    }

    public boolean deshabilitarSala(String codigo){
        Sala sala = buscarSalaPorCodigo(codigo);
        if (sala != null){
            sala.setEstado(EstadoSala.FUERA_DE_SERVICIO);
            return true;
        }
        return false;
    }

    //------------------GESTIÓN CLIENTES---------------
    public Cliente buscarClientePorId(String identificacion){
        for (Cliente cliente : listaClientes){
            if (cliente.getIdentificacionCliente().equals(identificacion)){
                return cliente;
            }
        }
        return null;
    }

    public boolean registrarCliente(Cliente cliente){
        if (buscarClientePorId(cliente.getIdentificacionCliente()) !=null){
            return false;
        }
        listaClientes.add(cliente);
        return true;
    }

    //--------------------GESTIÓN FUNCIONES--------------
    public Funcion buscarFuncionPorCodigo(String codigo){
        for (Funcion funcion : listaFunciones){
            if (funcion.getCodigoFuncion().equals(codigo)){
                return funcion;
            }
        }
        return null;
    }

    public List<Funcion> funcionesPorFecha(LocalDate fecha){
        List<Funcion> resultado = new ArrayList<>();
        for (Funcion funcion : listaFunciones){
            if (funcion.getFecha().equals(fecha) && funcion.getEstado()== EstadoFuncion.PROGRAMADA){
                resultado.add(funcion);
            }
        }
        return resultado;
    }

    public void programarFuncion (Funcion funcion) throws SalaFueraDeServicioException {
        Sala sala = funcion.getSala();
        if (sala.getEstado()==EstadoSala.FUERA_DE_SERVICIO){
            throw new SalaFueraDeServicioException("No se puede programar una función en la sala "+sala.getCodigo()+", se encuentra FUERA DE SERVICIO.");
        }
        if (funcion.getPelicula().getEstado()== EstadoPelicula.NO_DISPONIBLE){
            throw new IllegalArgumentException("Película no disponible.");
        }
        listaFunciones.add(funcion);
    }

    //----------------GESTIÓN VENTAS-------------------
    public double calcularValorEntrada(Funcion funcion, TipoCliente tipoCliente){
        double valorBase= funcion.getValorBase();
        switch (tipoCliente){
            case ESTUDIANTE:
                return valorBase*0.8;

            case NINO:
                return valorBase*0.66;

            case ADULTO_MAYOR:
                return valorBase*0.73;

            case GENERAL:
            default:
                return valorBase;
        }
    }

    public Venta venderEntrada(String codigoVenta, String identificacionCliente, String codigoFuncion, String codigoAsiento) throws FuncionInexistenteException, AsientoOcupadoException {

        Funcion funcion= buscarFuncionPorCodigo(codigoFuncion);
        if (funcion==null){
            throw new FuncionInexistenteException("Funcion con código "+codigoFuncion+" no encontrado.");
        }

        Cliente cliente = buscarClientePorId(identificacionCliente);
        if (cliente==null){
            throw new IllegalArgumentException("Cliente con identificacion "+identificacionCliente+" no encontrado.");
        }

        Asiento asiento= funcion.getSala().buscarAsientoPorCodigo(codigoAsiento);
        if (asiento==null){
            throw new AsientoOcupadoException("El asiento "+codigoAsiento+" no existe.");
        }

        if (asiento.getEstado()== EstadoAsiento.OCUPADO){
            throw new AsientoOcupadoException("El asiento"+codigoAsiento+ " ya está ocupado.");
        }

        double valorPagado= calcularValorEntrada(funcion, cliente.getTipoCliente());

        Venta venta = new Venta (codigoVenta, cliente, funcion, asiento, LocalDateTime.now(), valorPagado, EstadoVenta.ACTIVA);

        asiento.ocuparAsiento();
        listaVentas.add(venta);

        return venta;
    }

    public Venta buscarVentaPorCodigo(String codigo){
        for (Venta venta : listaVentas){
            if (venta.getCodigoVenta().equals(codigo)){
                return venta;
            }
        }
        return null;
    }

    public void cancelarVenta(String codigoVenta) throws VentaInexistenteException {
        Venta venta= buscarVentaPorCodigo(codigoVenta);
        if (venta==null){
            throw new VentaInexistenteException("La venta con el código "+codigoVenta+" no existe.");
        }
        venta.getAsiento().liberarAsiento();
        venta.setEstadoVenta(EstadoVenta.CANCELADA);
    }

    public int totalEntradasVendidas(){
        int totalEntradas =0;
        for (Venta venta : listaVentas){
            if (venta.getEstadoVenta()==EstadoVenta.ACTIVA){
                totalEntradas++;
            }
        }
        return totalEntradas;
    }

    public double totalIngresos(){
        double totalIngresos=0;
        for (Venta venta : listaVentas){
            if (venta.getEstadoVenta()==EstadoVenta.ACTIVA){
                totalIngresos+=venta.getValorPagado();
            }
        }
        return totalIngresos;
    }

    public Pelicula peliculaMasVendida(){
        if (listaVentas.isEmpty()){
            return null;
        }
        Pelicula masVendida=null;
        int maxVentas=0;
        for (Pelicula pelicula : listaPeliculas){
            int contador=0;
            for (Venta venta : listaVentas){
                if (venta.getEstadoVenta()==EstadoVenta.ACTIVA){
                    if (venta.getFuncion().getPelicula().getCodigoPelicula().equals(pelicula.getCodigoPelicula())){
                        contador++;
                    }
                }
            }
            if (contador>maxVentas){
                maxVentas=contador;
                masVendida=pelicula;
            }
        }
        return masVendida;
    }

    public String ocupacionFuncion(String codigoFuncion) throws FuncionInexistenteException {
        Funcion funcion = buscarFuncionPorCodigo(codigoFuncion);
        if (funcion == null) {
            throw new FuncionInexistenteException("Función no encontrada: " + codigoFuncion);
        }
        Sala sala = funcion.getSala();
        int ocupados = sala.contarAsientosOcupados();
        int disponibles = sala.contarAsientosDisponibles();
        int capacidad = sala.getCapacidad();
        double porcentaje = 0.0;
        if (capacidad > 0) {
            porcentaje = (ocupados * 100.0) / capacidad;
        }
        String resultado = "Funcion -->" + codigoFuncion + "\nOcupados: " + ocupados + "\nDisponibles: " + disponibles + "\nCapacidad total: " + capacidad + "\nOcupación: " + porcentaje + "%";
        return resultado;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }
    public void setListaPeliculas(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    public List<Sala> getListaSalas() {
        return listaSalas;
    }
    public void setListaSalas(List<Sala> listaSalas) {
        this.listaSalas = listaSalas;
    }

    public List<Funcion> getListaFunciones() {
        return listaFunciones;
    }
    public void setListaFunciones(List<Funcion> listaFunciones) {
        this.listaFunciones = listaFunciones;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }
    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }
}