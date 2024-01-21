/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexionBD {
    
    private Connection conexion;
    private Statement statement;
    
    public ConexionBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            conexion = DriverManager.getConnection("jdbc:mysql://192.168.1.50:3306/registro?autoReconnect=true&useSSL=false", "idear", "idear2019");
            statement = (Statement) conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            //statement = (Statement) conexion.createStatement();
        } catch (SQLException e) {
            System.err.println("Error al conectar");
            System.err.println(e.getMessage());
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet consultar(String sql)throws SQLException{
        return statement.executeQuery(sql);
    }
    
    public int guardar(String sql) throws SQLException{
        int n = statement.executeUpdate(sql);
        return n;
    }
    
    public void cerrar(){
        try {
            System.out.println("CONEXION CERRADA");
            conexion.close();
        } catch (SQLException e) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
}
