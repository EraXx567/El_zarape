package com.tecnovision.zarape.db;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConexionMySQL {

    private Connection conn;

    public Connection open() throws Exception 
    {
        String ruta = "jdbc:mysql://localhost:3306/sakila";
        String usuario = "root";
        String password = "Banquito02";
        
        //Registramos el Driver de MySQL para que este disponible 
        //Y nos podamos conectar con MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        //Abrimos la conexión con MySQL
        conn = DriverManager.getConnection(ruta, usuario, password);
        
        //Devolvemos la conexion
        return conn;
    }

    public void close() throws Exception 
    {
        if(conn != null){
            conn.close();
        }
    }
}
