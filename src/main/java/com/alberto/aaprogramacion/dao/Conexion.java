package com.alberto.aaprogramacion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection conexion;
    private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL_CONEXION = "jdbc:oracle:thin:@localhost:1521:XE";// pones SID en mayusculas
    private final String USUARIO = "PARQUES";
    private final String CONTRASENA = "1234";

    public Conexion() {

    }

    public Connection getConexion() {
        return conexion;
    }

    public void conectar() {
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL_CONEXION, USUARIO, CONTRASENA);
            if(conexion!=null){
                System.out.println("Conexion exitosa a esquema Parques");
            }else{
                System.out.println("Conexion fallida");
            }
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void desconectar() {
        try {
            conexion.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
