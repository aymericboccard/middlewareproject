package theatre.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import model.Event;

@Remote
public interface StatelessLocal {

	public String showEvent(@WebParam(name = "idevent") int idevent);
	public String showBookingBySeat(@WebParam(name = "seat") String seat);
	public String showAllEvent();

}
