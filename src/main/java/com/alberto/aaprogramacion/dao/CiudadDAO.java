package com.alberto.aaprogramacion.dao;

import com.alberto.aaprogramacion.domain.Ciudad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CiudadDAO {

    private Conexion conexion;
    private final Scanner teclado = new Scanner(System.in);

    public CiudadDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Busca entre todas las ciudades
     * @param extension recibe como parámetro una extensión a partir de cual el usuario quiere buscar
     * Devuelve todas las ciudades que tengan parques cuya suma de su extensión
     * supere la introducida por el usuario.
     */
    public ArrayList<Ciudad> listadoCiudadesUsuario(int extension) throws SQLException {
        String sql = "SELECT ci.n_ciudad, ci.id_ciudad " +
                "FROM PARQUES PA INNER JOIN CIUDADES CI ON ci.id_ciudad=pa.id_ciudad " +
                "GROUP BY ci.n_ciudad, ci.id_ciudad HAVING SUM(EXTENSION)> (?)";
        ArrayList<Ciudad> ciudades = new ArrayList<>();

        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setInt(1, extension);
        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            Ciudad ciudad = new Ciudad();
            ciudad.setnCiudad(resultado.getString(1));

            ciudades.add(ciudad);
        }

        return ciudades;
    }

    /**
     * Busca una ciudad concreta
     * @param ciudad recibe como parámetro el nombre de la ciudad
     * Devuelve un objeto ciudad con todos los datos de la misma.
     */
    public Ciudad buscarCiudad(String ciudad)throws SQLException{
        String sql = "SELECT ID_CIUDAD, N_CIUDAD, CCAA FROM CIUDADES " +
                "WHERE UPPER(N_CIUDAD) LIKE UPPER(?)";


        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, ciudad);
        ResultSet resultado = sentencia.executeQuery();
        Ciudad ciudadbus = new Ciudad();
        while (resultado.next()) {

            ciudadbus.setIdCiudad(resultado.getString(1));
            ciudadbus.setnCiudad(resultado.getString(2));
            ciudadbus.setCcaa(resultado.getString(3));
        }

        return ciudadbus;
    }
    /**
     * Busca una ciudad concreta
     * @param idciudad recibe como parámetro el id de la ciudad
     * Devuelve un objeto ciudad con todos los datos de la misma.
     */
    public Ciudad buscarCiudadId(String idciudad)throws SQLException{
        String sql = "SELECT ID_CIUDAD, N_CIUDAD, CCAA FROM CIUDADES " +
                "WHERE ID_CIUDAD = (?)";


        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, idciudad);
        ResultSet resultado = sentencia.executeQuery();
        Ciudad ciudadbus = new Ciudad();
        while (resultado.next()) {

            ciudadbus.setIdCiudad(resultado.getString(1));
            ciudadbus.setnCiudad(resultado.getString(2));
            ciudadbus.setCcaa(resultado.getString(3));
        }

        return ciudadbus;
    }
}
