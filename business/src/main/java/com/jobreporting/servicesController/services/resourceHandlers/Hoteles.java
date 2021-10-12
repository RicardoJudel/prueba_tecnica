/*
 Gestión de servicios sobre hoteles
 */
package com.jobreporting.servicesController.services.resourceHandlers;
import com.jobreporting.classes.Hotel;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.HotelDao;
import java.util.ArrayList;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/Hoteles")
public class Hoteles {
    public static LoggerManager logger = GenericUtility.getLogger(Hoteles.class.getName());
    static final String MSG_PROCESS_NOT_STARTED = "Proccess could not run.";
    static final String MSG_1 = "Success Operation.";  
    static final String MSG_2 = "No se han encontrado hoteles en la base de datos.";
    
    //servicio para listar todos los hoteles
    @Path("/Listar")
    @POST    
    @Produces(MediaType.APPLICATION_JSON)    
    public Response Listar(){
        logger.debug("Hoteles - Listar - Welcome");
        String msg = MSG_PROCESS_NOT_STARTED;        
        int cod = -1; 
        JSONArray result = new JSONArray();
        HotelDao hotelDao = new HotelDao();
        ArrayList<Hotel> hoteles = hotelDao.GetHoteles();
        
        if(hoteles.size() > 0){
            for(Hotel hotel : hoteles){
                JSONObject h = new JSONObject();
                h.put("id",hotel.getId());
                h.put("idPlantilla",hotel.getIdPlantilla());
                h.put("nombre",hotel.getNombre());
                h.put("ciudad",hotel.getCiudad());
                result.put(h);
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