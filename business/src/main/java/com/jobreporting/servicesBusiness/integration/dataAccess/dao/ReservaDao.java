/*
 Gestión de reservas con la base de datos
 */
package com.jobreporting.servicesBusiness.integration.dataAccess.dao;
import com.jobreporting.classes.Reserva;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservaDao {
    public static LoggerManager logger = GenericUtility.getLogger(ReservaDao.class.getName());
    
    //método para obtener todas las reservas
    public ArrayList<Reserva> GetReservas(){
        logger.debug("ReservaDao - GetReservas - Welcome");
        ArrayList<Reserva> reservas = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM reservas";
            ps = con.prepareStatement(sql, 1);
            logger.debug("ReservaDao - GetReservas - query: "+sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Reserva r = new Reserva();
                r.setId(rs.getInt("id"));
                r.setIdCliente(rs.getInt("idcliente"));
                r.setIdHotel(rs.getInt("idhotel"));
                r.setPersonas(rs.getInt("personas"));
                r.setEntrada(rs.getString("entrada"));
                r.setSalida(rs.getString("salida"));
                reservas.add(r);
            }         
        }catch(SQLException sqlEx){
            logger.debug("ReservaDao - GetReservas - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ReservaDao - GetReservas - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("ReservaDao - GetReservas - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }        
        return reservas;        
    }
    
    //método para obtener todas las reservas de un cliente a través de la id
    public ArrayList<Reserva> GetReservasCliente(int idCliente){
        logger.debug("ReservaDao - GetReservasCliente - Welcome");
        logger.debug("ReservaDao - GetReservasCliente - idCliente: " + idCliente);        
        ArrayList<Reserva> reservas = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM reservas WHERE idcliente=?";
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idCliente);
            logger.debug("ReservaDao - GetReservasCliente - query: "+sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Reserva r = new Reserva();
                r.setId(rs.getInt("id"));
                r.setIdCliente(rs.getInt("idcliente"));
                r.setIdHotel(rs.getInt("idhotel"));
                r.setPersonas(rs.getInt("personas"));
                r.setEntrada(rs.getString("entrada"));
                r.setSalida(rs.getString("salida"));
                reservas.add(r);
            }
        }catch(SQLException sqlEx){
            logger.debug("ReservaDao - GetReservasCliente - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ReservaDao - GetReservasCliente - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("ReservaDao - GetReservasCliente - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }        
        return reservas;       
    }
}