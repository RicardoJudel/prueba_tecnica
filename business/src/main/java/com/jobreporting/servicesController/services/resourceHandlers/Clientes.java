/*
 Gestión de servicios sobre clientes
 */
package com.jobreporting.servicesController.services.resourceHandlers;
import com.jobreporting.classes.Cliente;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ClienteDao;
import java.util.ArrayList;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/Clientes")
public class Clientes {
    public static LoggerManager logger = GenericUtility.getLogger(Clientes.class.getName());
    static final String MSG_PROCESS_NOT_STARTED = "Proccess could not run.";
    static final String MSG_1 = "Success Operation.";  
    static final String MSG_2 = "No se han encontrado clientes en la base de datos.";
    
    //servicio para listar todos los clientes
    @Path("/Listar")
    @POST    
    @Produces(MediaType.APPLICATION_JSON)    
    public Response Listar(){
        logger.debug("Clientes - Listar - Welcome");
        String msg = MSG_PROCESS_NOT_STARTED;
        int cod = -1; 
        JSONArray result = new JSONArray();
        ClienteDao clienteDao = new ClienteDao();
        ArrayList<Cliente> clientes = clienteDao.GetClientes();        
        
        if(clientes.size() > 0){
            for(Cliente cliente : clientes){
                JSONObject c = new JSONObject();
                c.put("id",cliente.getId());
                c.put("nombre", cliente.getNombre());
                c.put("telefono",cliente.getTelefono());
                c.put("email",cliente.getEmail());
                c.put("preferencia",cliente.getPreferencia());
                result.put(c);
            }
            cod = 1;
            msg = MSG_1;
        }else{
            cod = 2;
            msg = MSG_2;
        }       
         
        JSONObject response = new JSONObject();             
        response.put("msg",msg);
        response.put("cod",cod);
        response.put("result",result);
        return Response.status(200).entity(response.toString()).build();
    }
}