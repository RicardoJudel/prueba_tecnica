/*
 Gestión de servicios sobre promociones
 */
package com.jobreporting.servicesController.services.resourceHandlers;
import com.jobreporting.classes.Reserva;
import com.jobreporting.classes.Promocion;
import com.jobreporting.generic.loggerManager.LoggerManager;
import com.jobreporting.generic.utilities.GenericUtility;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ReservaDao;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.ClienteDao;
import com.jobreporting.servicesBusiness.integration.dataAccess.dao.HotelDao;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/Promociones")
public class Promociones{
    public static LoggerManager logger = GenericUtility.getLogger(Promociones.class.getName());
    static final String MSG_PROCESS_NOT_STARTED = "Proccess could not run.";
    static final String MSG_1 = "Success Operation.";  
    static final String MSG_2 = "No se ha podido obtener el nombre del cliente.";
    static final String MSG_3 = "El cliente especificado no existe.";
    static final String MSG_4 = "No existen reservas para este cliente.";
    static final String MSG_5 = "No se han podido obtener las reservas del cliente.";
    static final String MSG_6 = "No se ha podido obtener la preferencia del cliente.";
    static final String MSG_7 = "No se ha podido obtener la id del hotel";
    static final String MSG_8 = "No se ha podido obtener la ciudad del hotel";
    static final String MSG_9 = "No se han podido obtener hoteles promocionales.";
    static final String MSG_10 = "No se ha podido obtener el nombre del hotel.";
    
    //servicio para enviar promociones a un cliente especificando su nombre
    @Path("/Enviar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response SendPromotion(String query){
        logger.debug("Promociones - Enviar - query: " + query);     
        
        //obtenemos el nombre del cliente
        JSONObject jsonObjectRequest = new JSONObject(query);       
        String nombre = jsonObjectRequest.get("nombre").toString();      
          
        String msg = MSG_PROCESS_NOT_STARTED;
        int cod = -1; 
        JSONArray result = new JSONArray();
        
        if(!nombre.equals("")){
            //obtenemos la id del cliente a través del nombre
            ClienteDao clienteDao = new ClienteDao();
            int idCliente = clienteDao.GetId(nombre);
            logger.debug("SendPromotion - idCustomer: "+idCliente);
            if(idCliente != -1){
                //comprobamos que el cliente tenga al menos una reserva
                boolean existeReserva = clienteDao.ExisteReserva(idCliente);
                if(existeReserva){
                    //obtenemos las reservas del cliente
                    ReservaDao reservaDao = new ReservaDao();
                    ArrayList<Reserva> reservas = reservaDao.GetReservasCliente(idCliente);
                    
                    if(reservas.size() > 0){
                        //obtenemos la preferencia de envío del cliente
                        String preferencia = clienteDao.GetPreferencia(idCliente);
                        if(!preferencia.equals("")){
                            //insertamos en el JSON el nombre del cliente y la preferencia de envío
                            JSONObject client = new JSONObject();
                            if (preferencia.equals("email")){
                                client.put("metodoenvio","EMAIL");
                            }else{
                                client.put("metodoenvio","SMS");
                            }
                            client.put("nombrecliente",nombre);
                            result.put(client);

                            HotelDao hotelDao = new HotelDao();
                            ArrayList<Promocion> hotelesPromocionables;
                            //para todas las reservas del cliente
                            for(Reserva reserva : reservas){
                                //obtenemos el id del hotel a través del id de la reserva
                                int idHotel = reserva.getIdHotel(); 
                                if(idHotel != -1){      
                                    //obtenemos la ciudad del hotel a través la id
                                    String ciudad = hotelDao.GetCiudad(idHotel);
                                    if(!ciudad.equals("")){
                                        //obtenemos los hoteles que se pueden promocionar al cliente en base a la ciudad de anteriores reservas
                                        hotelesPromocionables = hotelDao.GetHotelesPromocionables(idHotel,ciudad);
                                        JSONArray hoteles = new JSONArray();

                                        if(hotelesPromocionables.size() > 0){
                                            //obtenemos el nombre del hotel a través de su id
                                            String nombreHotel = hotelDao.GetNombre(idHotel);
                                            if(!nombreHotel.equals("")){
                                                //insertamos en el JSON el nombre del hotel ya reservado y la ciudad
                                                JSONObject infoHotelReservado = new JSONObject();
                                                infoHotelReservado.put("Hotel Ya Reservado",nombreHotel);
                                                infoHotelReservado.put("Ciudad",ciudad);                                        
                                                hoteles.put(infoHotelReservado);

                                                JSONArray promociones = new JSONArray();
                                                //para todos los hoteles promocionales
                                                for(Promocion hp : hotelesPromocionables){
                                                    //insertamos el nombre y plantilla del hotel a promocionar
                                                    JSONObject p = new JSONObject();
                                                    p.put("Nombre Hotel", hp.getNombreHotel());
                                                    p.put("Asunto",hp.getAsunto());
                                                    p.put("Cuerpo",hp.getCuerpo());
                                                    promociones.put(p);                                            
                                                }
                                                hoteles.put(promociones);
                                            }else{
                                                cod = 10;
                                                msg = MSG_10;
                                            }
                                        }else{
                                            cod = 9;
                                            msg = MSG_9;
                                        }
                                        result.put(hoteles);
                                    }else{
                                        cod = 8;
                                        msg = MSG_8;
                                    }
                                }else{
                                    cod = 7;
                                    msg = MSG_7;
                                }
                                cod = 1;
                                msg = MSG_1;
                            }
                        }else{
                            cod = 6;
                            msg = MSG_6;
                        }
                    }else{
                       cod = 5;
                       msg = MSG_5;
                    }               
                }else{
                    cod = 4;
                    msg = MSG_4;
                }               
            }else{
                cod = 3;
                msg = MSG_3;
            }     
        }else{
            if(nombre.equals("")){
                cod = 2;
                msg = MSG_2;
            }
        }        
        
        JSONObject response = new JSONObject();             
        response.put("msg",msg);
        response.put("cod",cod);
        response.put("result",result);
        return Response.status(200).entity(response.toString()).build(); 
    }    
  }