/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones;

import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author a
 */
public class DBConectar {
    
    static String bd = "teams";
    static String login = "root";
    static String password = "mysql2019";
    static String url = "jdbc:mysql://localhost/"+bd;
    static String mensaje = "";
    
    Connection conexion = null;
    
    public DBConectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(url,login,password);
            if(conexion!=null){
                System.out.println("Connection to "+bd+ " established...");
            }
        }
        catch(HeadlessException | ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,"Failed at: Connect to "+bd+" ...","Warning",0);
            System.out.println(e);
        }
    }
    
    public static String getMensaje() {
        return mensaje;
    }
    
    public static void setMensaje(String mensaje) {
        DBConectar.mensaje = mensaje;
    }
    
    public Connection getConexion(){
        return conexion;
    }
    
    public void desconectar(){
        conexion = null;
    }

}
