/*
 Gestión de plantillas con la base de datos
 */
package com.jobreporting.servicesBusiness.integration.dataAccess.dao;
import com.jobreporting.classes.Plantilla;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlantillaDao {
    public static LoggerManager logger = GenericUtility.getLogger(PlantillaDao.class.getName());
    
    //método para obtener todas las plantillas
    public ArrayList<Plantilla> GetPlantillas(){
        logger.debug("PlantillaDao - GetPlantillas - Welcome"); 
        ArrayList<Plantilla> plantillas = new ArrayList<>();
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM plantillas";
            ps = con.prepareStatement(sql, 1);            
            logger.debug("PlantillaDao - GetPlantillas - query: "+sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Plantilla p = new Plantilla();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setAsunto(rs.getString("asunto"));
                p.setCuerpo(rs.getString("cuerpo"));
                plantillas.add(p);
            }  
        }catch(SQLException sqlEx){
            logger.debug("PlantillaDao - GetPlantillas - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("PlantillaDao - GetPlantillas - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("PlantillaDao - GetPlantillas - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }        
        return plantillas; 
    }    
}