/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author estudiantes
 */
public class DBConsultas {//Consulta equipos
        
    private DBConectar cn;
    
    public DBConsultas(){
        this.cn = new DBConectar();
    }
    
    public ResultSet getTeamByString(String name)throws SQLException{
        PreparedStatement p = cn.getConexion().prepareStatement("select team_def from lvlteams where team_name = ?");
        p.setString(1, name);
        ResultSet r = p.executeQuery();
        return r;
    }
    
    public ResultSet getTeams()throws SQLException{
        PreparedStatement pg = cn.getConexion().prepareStatement("SELECT *"
                + "FROM lvlteams");
        
        ResultSet rg = pg.executeQuery();
        return rg;
    }
    
    
    public void DeleteAllPart(){
        try{
            PreparedStatement pda = cn.getConexion().prepareStatement("TRUNCATE TABLE"
                    + " lvlteams");
            
            pda.executeUpdate();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Failed at: Delete all teams data action","Warning",0);
        }
    }
    
    

    
}
