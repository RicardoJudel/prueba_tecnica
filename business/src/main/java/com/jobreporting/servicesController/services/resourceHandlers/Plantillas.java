/*
 Gestión de servicios sobre plantillas
 */
package com.jobreporting.servicesController.services.resourceHandlers;
import com.jobreporting.classes.Plantilla;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.PlantillaDao;
import java.util.ArrayList;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/Plantillas")
public class Plantillas {
    public static LoggerManager logger = GenericUtility.getLogger(Plantillas.class.getName());
    static final String MSG_PROCESS_NOT_STARTED = "Proccess could not run.";
    static final String MSG_1 = "Success Operation.";  
    static final String MSG_2 = "No se han encontrado plantillas en la base de datos.";
    
    //servicio para listar todas las plantillas
    @Path("/Listar")
    @POST    
    @Produces(MediaType.APPLICATION_JSON)    
    public Response Listar(){
        logger.debug("Plantillas - Listar - Welcome");
        String msg = MSG_PROCESS_NOT_STARTED;        
        int cod = -1; 
        JSONArray result = new JSONArray();
        PlantillaDao plantillaDao = new PlantillaDao();
        ArrayList<Plantilla> plantillas = plantillaDao.GetPlantillas();
        
        if(plantillas.size() > 0){
            for(Plantilla plantilla : plantillas){
                JSONObject p = new JSONObject();
                p.put("id",plantilla.getId());
                p.put("nombre",plantilla.getNombre());
                p.put("asunto",plantilla.getAsunto());
                p.put("cuerpo",plantilla.getCuerpo());
                result.put(p);
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