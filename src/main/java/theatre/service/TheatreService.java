package theatre.service;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import model.Event;
import model.Booking;

@Stateless
@WebService
public class TheatreService {
	@EJB(beanName = "BK")
	private StatelessLocal metier;
	
	//test de la table EVENTS
	@WebMethod
	public String showEvent(@WebParam(name = "idevent") int idevent){
		try {
			return metier.showEvent(idevent);
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}
	
	//test de la table BOOKING
	@WebMethod
	public String showBookingBySeat(@WebParam(name = "seat") String seat){
		try {
			return metier.showBookingBySeat(seat);
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}
}
