package com.tecnovision.zarape.controller;

import java.util.List;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.tecnovision.zarape.db.ConexionMySQL;
import com.tecnovision.zarape.model.Alimento;
import com.tecnovision.zarape.model.Categoria;
import com.tecnovision.zarape.model.Producto;

public class ControllerAlimento
{
    public int insert(Alimento a) throws Exception
    {
        // Se define la consulta SQL:
        String sql = "{CALL insertarAlimento(?, ?, ?, ?, ?, ?, ?)}";
        
        // Abrimos la conexion con la BD:
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Generamos un CallableStatement para invocar al Stored Procedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        // Colocamos los valores de los parametros de entrada que requiere
        // el Stored Procedure:
        cstmt.setString(1, a.getProducto().getNombre());
        cstmt.setString(2, a.getProducto().getDescripcion());
        cstmt.setString(3, a.getProducto().getFoto());
        cstmt.setDouble(4, a.getProducto().getPrecio());
        cstmt.setInt   (5, a.getProducto().getCategoria().getId());
        
        // Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
        
        // Recuperamos el ID de Producto y de Alimento generados:
        a.getProducto().setId(cstmt.getInt(6));
        a.setId(cstmt.getInt(7));
        
        //Cerramos los objetos de conexion:
        cstmt.close();
        connMySQL.close();
        
        // Devolvemos el ID de Alimento que se genero:
        return a.getId();
    }
    
    public void update(Alimento a) throws Exception
    {
        // Se define la consulta SQL:
        String sql = "{CALL actualizarAlimento(?, ?, ?, ?, ?, ?)}";
        
        // Abrimos la conexion con la BD:
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        
        // Generamos un CallableStatement para invocar al Stored Procedure:
        CallableStatement cstmt = conn.prepareCall(sql);
        
        // Colocamos los valores de los parametros de entrada que requiere
        // el Stored Procedure:
        cstmt.setString(1, a.getProducto().getNombre());
        cstmt.setString(2, a.getProducto().getDescripcion());
        cstmt.setString(3, a.getProducto().getFoto());
        cstmt.setDouble(4, a.getProducto().getPrecio());
        cstmt.setInt   (5, a.getProducto().getCategoria().getId());
        cstmt.setInt   (6, a.getProducto().getId());
        
        // Ejecutamos el Stored Procedure:
        cstmt.executeUpdate();
                
        //Cerramos los objetos de conexion:
        cstmt.close();
        connMySQL.close();
    }
    
    public void delete(int id) throws Exception
    {
        // Se define la consulta SQL:
        String sql = "UPDATE producto SET activo=0 WHERE idProducto=?";
        
        // Abrimos la conexion con la BD:
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Llenamos los datos del PreparedStatement:
        pstmt.setInt(1, id);
        
        // Ejecutamos la consulta:
        pstmt.executeUpdate();
        
        // Cerramos los objetos de conexion:
        pstmt.close();
        connMySQL.close();
    }
    
    public List<Alimento> getAll(String filtro) throws Exception
    {
        List<Alimento> alimentos = new ArrayList<>();
        // Se define la consulta SQL:
        String sql = "SELECT * FROM v_alimento";
        
        // Abrimos la conexion con la BD:
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        
        Alimento alim = null;
        
        // Recorremos cada registro devuelto por la consulta:
        while(rs.next())
        {
            alim = fill(rs);
            alimentos.add(alim);
        }
        
        rs.close();
        pstmt.close();
        connMySQL.close();
        
        return alimentos;
    }
    
    private Alimento fill(ResultSet rs) throws Exception
    {
        Categoria c = new Categoria();
        Producto  p = new Producto();
        Alimento  a = new Alimento();
        
        p.setCategoria(c);
        a.setProducto(p);
        
        // Establecemos los valores de cada atributo de
        // los objetos relacionados, extraidos de cada
        // campo del ResultSet:
        
        a.setId(rs.getInt("idAlimento"));
        
        p.setActivo(rs.getInt("productoActivo"));
        p.setDescripcion(rs.getString("descripcionProducto"));
        p.setFoto(rs.getString("foto"));
        p.setId(rs.getInt("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getDouble("precio"));
        
        c.setActivo(rs.getInt("categoriaActiva"));
        p.setDescripcion(rs.getString("descripcionCategoria"));
        c.setId(rs.getInt("idCategoria"));
        c.setTipo(rs.getString("tipo"));
        
        return a;
    }
}