package theatre.service;

import javax.ejb.Remote;
import javax.jws.WebParam;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

@Remote
public interface StatelessLocal {

	public String showAllEvents();
	public String showEvent(@WebParam(name = "idevent") int idevent);
	public String showBookingBySeat(@WebParam(name = "seat") String seat);

	public String addBooking(@WebParam(name = "idevent") int idevent,@WebParam(name = "seat") String seat,@WebParam(name = "username") String username) throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, Exception;
	public boolean checkReservation(int idevent, String seat) throws Exception;
	public boolean checkAvailability(int idevent) throws Exception;
	public boolean checkAvailabilityBySection(int idevent, String section) throws Exception;

}
