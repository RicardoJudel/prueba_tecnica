/*
 Gestión de hoteles con la base de datos
 */
package com.jobreporting.servicesBusiness.integration.dataAccess.dao;
import com.jobreporting.classes.Hotel;
import com.jobreporting.classes.Promocion;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDao {
    public static LoggerManager logger = GenericUtility.getLogger(HotelDao.class.getName());
     
    //método para obtener todos los hoteles
    public ArrayList<Hotel> GetHoteles(){
        logger.debug("HotelDao - GetHoteles - Welcome"); 
        ArrayList<Hotel> hotels = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM hoteles";
            ps = con.prepareStatement(sql, 1);
            logger.debug("HotelDao - GetHoteles - query: "+sql);
            rs = ps.executeQuery();
            while(rs.next()){
               Hotel hotel = new Hotel();
               hotel.setId(rs.getInt("id"));
               hotel.setIdPlantilla(rs.getInt("idplantilla"));
               hotel.setNombre(rs.getString("nombre"));
               hotel.setCiudad(rs.getString("ciudad"));
               hotels.add(hotel);
            }            
        }catch(SQLException sqlEx){
            logger.debug("HotelDao - GetHoteles - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("HotelDao - GetHoteles - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("HotelDao - GetHoteles - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }
        return hotels;
    }
    
    //método para obtener el nombre de un hotel a través de la id
    public String GetNombre(int idHotel){
        logger.debug("HotelDao - GetNombre - Welcome"); 
        logger.debug("HotelDao - GetNombre - idHotel: " + idHotel); 
        String nombre = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT nombre FROM hoteles WHERE id=?";
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idHotel);
            logger.debug("HotelDao - GetNombre - query: "+sql);
            rs = ps.executeQuery();
            if(rs.next()){
                nombre = rs.getString("nombre");
            }  
        }catch(SQLException sqlEx){
            logger.debug("HotelDao - GetNombre - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("HotelDao - GetNombre - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("HotelDao - GetNombre - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }        
        return nombre;      
    }
     
    //método para obtener la ciudad de un hotel a través de la id
    public String GetCiudad(int idHotel){
        logger.debug("HotelDao - GetCiudad - Welcome"); 
        logger.debug("HotelDao - GetCiudad - idHotel: " + idHotel); 
        String ciudad = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT ciudad FROM hoteles WHERE id=?";
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idHotel);
            logger.debug("HotelDao - GetCiudad - query: "+sql);
            rs = ps.executeQuery();
            if(rs.next()){
                ciudad = rs.getString("ciudad");
            }  
        }catch(SQLException sqlEx){
            logger.debug("HotelDao - GetCiudad - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("HotelDao - GetCiudad - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("HotelDao - GetCiudad - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }        
        return ciudad;      
    }
    
    //método para obtener los hoteles promocionables a traves de la id y ciudad de un hotel
    public ArrayList<Promocion> GetHotelesPromocionables(int idHotel, String ciudad){
        logger.debug("HotelDao - GetHotelesPromocionables - Welcome"); 
        logger.debug("HotelDao - GetHotelesPromocionables - idHotel: " + idHotel); 
        logger.debug("HotelDao - GetHotelesPromocionables - ciudad: " + ciudad); 
        ArrayList<Promocion> hoteles = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT hoteles.nombre,plantillas.asunto,plantillas.cuerpo FROM hoteles INNER JOIN plantillas ON hoteles.idplantilla = plantillas.id WHERE hoteles.ciudad=? AND hoteles.id !=?";
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, ciudad);
            ps.setInt(2, idHotel);
            logger.debug("HotelDao - GetHotelesPromocionables - query: "+sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Promocion p = new Promocion();
                p.setNombreHotel(rs.getString("nombre"));
                p.setAsunto(rs.getString("asunto"));
                p.setCuerpo(rs.getString("cuerpo"));
                hoteles.add(p);
            }        
        }catch(SQLException sqlEx){
            logger.debug("HotelDao - GetCiudad - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("HotelDao - GetCiudad - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("HotelDao - GetCiudad - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }
        return hoteles; 
    }   
}