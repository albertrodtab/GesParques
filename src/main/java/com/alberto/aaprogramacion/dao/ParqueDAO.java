package com.alberto.aaprogramacion.dao;

import com.alberto.aaprogramacion.domain.Parque;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParqueDAO {

    private Conexion conexion;
    private final Scanner teclado = new Scanner(System.in);


    public ParqueDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    /**
    * Introduce un nuevo parque en la base de datos.
    * @param parque recibe como parámetro un parque del cual queremos grabar los datos.
    */
    public void registrarParqueDAO(Parque parque)throws SQLException {
        String sql = "INSERT INTO PARQUES.PARQUES (ID_CIUDAD, N_PARQUE, EXTENSION) VALUES(?, ?, ?)";

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, parque.getIdCiudad());
        sentencia.setString(2, parque.getNparque());
        sentencia.setInt(3, parque.getExtension());
        sentencia.executeUpdate();

    }

    /**
     * Selecciona un parque de la base de datos
     * @param nParque recibe como parámeto el nombre de un parque el cual queremos
     * consultar para luego hacer una actualización de datos.
     */
    public ArrayList<Parque> seleccionaParqueDAO(String nParque)throws SQLException {
        //Al usar UPPER(campo) LIKE UPPER(?) el programa transforma las cadena de texto a mayúsculas para realizar
        //una búsqueda más efectiva al no discriminar entre mayúsculas y minúsculas.
        String sql = "SELECT N_PARQUE, ID_CIUDAD, EXTENSION " +
                "FROM PARQUES WHERE UPPER(N_PARQUE) LIKE UPPER(?)";
        ArrayList<Parque> parques = new ArrayList<>();

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1,nParque);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Parque parque = new Parque();
            parque.setNparque(resultado.getString(1));
            parque.setIdCiudad(resultado.getString(2));
            parque.setExtension(resultado.getInt(3));

            parques.add(parque);
        }

        return parques;
    }

    /**
     * Actualiza la información de un parque ya registrado
     * @param parque recibe como parámeto un parque del cual queremos actualizar sus datos.
     */
    public void actualizarParqueDAO(Parque parque)throws SQLException{
        String sql = "UPDATE PARQUES.PARQUES SET ID_CIUDAD = (?), N_PARQUE = (?), EXTENSION = (?) WHERE ID_PARQUE = (?)";

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, parque.getIdCiudad());
        sentencia.setString(2, parque.getNparque());
        sentencia.setInt(3, parque.getExtension());
        sentencia.setString(4, parque.getIdParque());
        sentencia.executeUpdate();

    }

    /**
     * Elimina los parques de una determinada ciudad
     * @param ciudad recibe como parámetro el nombre de la ciudad de la cual queremos eliminar los parques.
     */
    public void eliminarParqueDao(String ciudad)throws SQLException {
        String sql = "DELETE FROM PARQUES WHERE ID_CIUDAD = (SELECT ID_CIUDAD FROM CIUDADES WHERE UPPER(N_CIUDAD) LIKE UPPER(?))";

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, ciudad);
        sentencia.executeUpdate();
    }

    /**
     * Busca todos los parques de la base de datos.
     * Devuelve un listado de todos los parques que están registrados en la base de datos.
     * Muestra nombre parque y extensión.
     */
    public ArrayList<Parque> obtenerParques() throws SQLException {
        String sql = "SELECT N_PARQUE, EXTENSION FROM PARQUES";
        ArrayList<Parque> parques = new ArrayList<>();

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Parque parque = new Parque();
            parque.setNparque(resultado.getString(1));
            parque.setExtension(resultado.getInt(2));

            parques.add(parque);
        }

        return parques;
    }

    /**
     * Busca entre todos los parques de la base de datos.
     * @param ciudad recibe como parámetro la ciudad de la cual queremos consultar los parques.
     * Devuelve un listado de todos los parques que están registrados en la base de datos, y que
     * pertenecen a una determinada ciudad. Muestra nombre parque y extensión.
     */
    public ArrayList<Parque> obtenerParquesCiudad(String ciudad)throws SQLException{
        String sql = "SELECT N_PARQUE, EXTENSION FROM PARQUES " +
           "WHERE ID_CIUDAD = (SELECT ID_CIUDAD FROM CIUDADES WHERE UPPER(N_CIUDAD) LIKE UPPER(?))";
        ArrayList<Parque> parques = new ArrayList<>();

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, ciudad);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Parque parque = new Parque();
            parque.setNparque(resultado.getString(1));
            parque.setExtension(resultado.getInt(2));

            parques.add(parque);
        }

        return parques;
    }
    /**
     * Busca entre todos los parques de la base de datos.
     * @param ccaa recibe como parámetro la CCAA de la cual queremos consultar lo parques
     * Devuelve un listado de todos los parques que están registrados en la base de datos y que pertenecen a la CCAA.
     * Muestra nombre parque y extensión.
     */
    public ArrayList<Parque> obtenerParquesCcaa(String ccaa)throws SQLException{
        String sql = "SELECT PA.N_PARQUE, PA.EXTENSION" +
        " FROM CIUDADES CI INNER JOIN PARQUES PA" +
        " ON CI.ID_CIUDAD = PA.ID_CIUDAD" +
        " WHERE UPPER(CI.CCAA) LIKE UPPER(?)";
        ArrayList<Parque> parques = new ArrayList<>();

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, ccaa);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()) {
            Parque parque = new Parque();
            parque.setNparque(resultado.getString(1));
            parque.setExtension(resultado.getInt(2));

            parques.add(parque);
        }

        return parques;
    }

    /**
     *Busca entre todos los parques de la base de datos.
     * @param cadena recibe como parámetro una cadena de texto.
     * Devuelve un listado de todos los parques que están registrados en la base de datos
     * cuyo nombre coincide con la cadena te texto insertada.
     * Muestra nombre parque y extensión.
     */
    public ArrayList<Parque> obtenerParquesNombre(String cadena)throws SQLException{
        String sql = "SELECT N_PARQUE, EXTENSION" +
                " FROM PARQUES" +
                " WHERE UPPER(N_PARQUE) LIKE UPPER(?)";
        ArrayList<Parque> parques = new ArrayList<>();

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, "%"+ cadena +"%");
        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            Parque parque = new Parque();
            parque.setNparque(resultado.getString(1));
            parque.setExtension(resultado.getInt(2));

            parques.add(parque);
        }

        return parques;
    }

    /**
     * Busca entre todos los parques de la base de datos.
     * @param nParque recibe como parámetro el nombre de un parque
     * Devuelve un objeto Parque con toda la información del mismo.
     */
    public Parque buscarParqueId(String nParque)throws SQLException {
        String sql = "SELECT ID_PARQUE, ID_CIUDAD, N_PARQUE, EXTENSION FROM PARQUES " +
                "WHERE UPPER(N_PARQUE) LIKE UPPER(?)";


        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, nParque);
        ResultSet resultado = sentencia.executeQuery();
        Parque parquebus = new Parque();
        while (resultado.next()) {

            parquebus.setIdParque(resultado.getString(1));
            parquebus.setIdCiudad(resultado.getString(2));
            parquebus.setNparque(resultado.getString(3));
            parquebus.setExtension(resultado.getInt(4));
        }
        return parquebus;
    }

    /**
     *Busca entre todos los parques de la base de datos.
     * @param ciudad recibe como parámetro el nombre de una ciudad en la cual centralizar la búsqueda
     * @param extension recibe como parámetro una extensión de parque a partir de la cual queremos ver los resultados.
     * Devuelve un sumatorio del número de parques de la ciudad indicada
     * y cuya extensión individual de los parques es mayor al número indicado.
     */
    public int listadoParquesUsuario(String ciudad, int extension)throws SQLException {
        String sql = "SELECT COUNT(ID_PARQUE) FROM PARQUES" +
                " where id_ciudad =(SELECT id_ciudad FROM ciudades WHERE UPPER(n_ciudad) like UPPER(?)) AND extension > (?)";

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, ciudad);
        sentencia.setInt(2, extension);

        ResultSet resultado = sentencia.executeQuery();
        int count = 0;
        if (resultado.next()) {
            count = resultado.getInt(1);
        }
        return count;
    }

}
