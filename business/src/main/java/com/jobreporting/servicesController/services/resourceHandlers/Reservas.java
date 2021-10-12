/*
 Gestión de servicios sobre reservas
 */
package com.jobreporting.servicesController.services.resourceHandlers;
import com.jobreporting.classes.Reserva;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ReservaDao;
import java.util.ArrayList;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/Reservas")
public class Reservas {
    public static LoggerManager logger = GenericUtility.getLogger(Reservas.class.getName());
    static final String MSG_PROCESS_NOT_STARTED = "Proccess could not run.";
    static final String MSG_1 = "Success Operation.";  
    static final String MSG_2 = "No se han encontrado reservas en la base de datos.";    
    
    //servicio para listar todas las reservas
    @Path("/Listar")
    @POST    
    @Produces(MediaType.APPLICATION_JSON)    
    public Response Listar(){
        logger.debug("Reservas - Listar - Welcome");
        String msg = MSG_PROCESS_NOT_STARTED;
        int cod = -1; 
        JSONArray result = new JSONArray();
        ReservaDao reservaDao = new ReservaDao();
        ArrayList<Reserva> reservas = reservaDao.GetReservas();  
        
        if(reservas.size() > 0){
            for(Reserva reserva : reservas){
                JSONObject r = new JSONObject();
                r.put("id",reserva.getId());
                r.put("idCliente", reserva.getIdCliente());
                r.put("idHotel",reserva.getIdHotel());
                r.put("personas",reserva.getPersonas());
                r.put("entrada",reserva.getEntrada());
                r.put("salida",reserva.getSalida());
                result.put(r);
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