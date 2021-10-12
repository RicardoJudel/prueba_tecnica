/*
 Gestión de clientes con la base de datos
 */
package com.jobreporting.servicesBusiness.integration.dataAccess.dao;
import com.jobreporting.classes.Cliente;
import com.jobreporting.generic.database.DatabaseConnectionManager;
import com.jobreporting.generic.exception.DatabaseConnectionManagerException;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDao{
    public static LoggerManager logger = GenericUtility.getLogger(ClienteDao.class.getName());
    
    //método para obtener todos los clientes
    public ArrayList<Cliente> GetClientes(){
        logger.debug("ClienteDao - GetClientes - Welcome");
        ArrayList<Cliente> clientes = new ArrayList<>(); 
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM clientes";
            ps = con.prepareStatement(sql, 1);
            logger.debug("ClienteDao - GetClientes - query: "+sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                cliente.setPreferencia(rs.getString("preferencia"));
                clientes.add(cliente);
            }             
        }catch(SQLException sqlEx){
            logger.debug("ClienteDao - GetClientes - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - GetClientes - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("ClienteDao - GetClientes - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }        
        return clientes;
    }
    
    //método para obtener la id de un cliente a través del nombre
    public int GetId(String nombre){
        logger.debug("ClienteDao - GetId - Welcome");
        logger.debug("ClienteDao - GetId - nombre: " + nombre);
        int idCliente = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT id FROM clientes WHERE nombre=?";
            ps = con.prepareStatement(sql, 1);
            ps.setString(1, nombre);
            logger.debug("ClienteDao - GetId - query: "+sql);
            rs = ps.executeQuery();
            if(rs.next()){
                idCliente = rs.getInt("id");
            }            
        }catch(SQLException sqlEx){
            logger.debug("ClienteDao - GetId - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - GetId - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("ClienteDao - GetId - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }        
        return idCliente;        
    }
    
    //método para obtener la preferencia de un cliente a través de la id
    public String GetPreferencia(int idCliente){
        logger.debug("ClienteDao - GetPreferencia - Welcome");
        logger.debug("ClienteDao - GetPreferencia - idCliente: " + idCliente);
        String preferencia = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT preferencia FROM clientes WHERE id=?";
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idCliente);
            logger.debug("ClienteDao - GetPreferencia - query: "+sql);
            rs = ps.executeQuery();
            if(rs.next()){
                preferencia = rs.getString("preferencia");
            }            
        }catch(SQLException sqlEx){
            logger.debug("ClienteDao - GetPreferencia - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - GetPreferencia - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("ClienteDao - GetPreferencia - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }        
        return preferencia;        
    }
    
    //método para conocer si un cliente tiene alguna reserva
    public boolean ExisteReserva(int idCliente){
        logger.debug("ClienteDao - ExisteReserva - Welcome");
        logger.debug("ClienteDao - ExisteReserva - idCliente: " + idCliente);
        boolean existeReserva = false;        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DatabaseConnectionManager.getConnection();
            String sql = "SELECT id FROM reservas WHERE idcliente=?";
            ps = con.prepareStatement(sql, 1);
            ps.setInt(1, idCliente);
            logger.debug("ClienteDao - ExisteReserva - query: "+sql);
            rs = ps.executeQuery();
            if(rs.next()){
                existeReserva = true;
            }
        }catch(SQLException sqlEx){
            logger.debug("ClienteDao - ExisteReserva - SQLException: "+sqlEx.getMessage());
        }catch(Exception ex){
            logger.debug("ClienteDao - ExisteReserva - Exception: "+ex.getMessage());
        }finally{
            try{
                DatabaseConnectionManager.returnConnection(con);
                DatabaseConnectionManager.clearResources(ps, rs);
            }catch(DatabaseConnectionManagerException ex){
                logger.debug("ClienteDao - ExisteReserva - DatabaseConnectionManagerException: "+ex.getMessage());
            }
        }        
        return existeReserva;
        } 
}