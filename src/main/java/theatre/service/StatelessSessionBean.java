/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package theatre.service;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import model.Event;
import model.Booking;

@Stateless(name = "BK")
@TransactionManagement(TransactionManagementType.BEAN)
public class StatelessSessionBean implements StatelessLocal {

	//private static final char A = 'A';
	@Resource
	private EJBContext context;
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager em;	
	
	
	// pour tester le fonctionnement de la table EVENTS
	@SuppressWarnings("unchecked")
	@Override
	public String showAllEvents() {
		Query query = em.createNamedQuery("Event.getAllEvents");

		List<Event> events = (List<Event>) query.getResultList();

		return events.toString();
	}
	
	// pour tester le fonctionnement de la table EVENTS
	@Override

	public String showEvent(int idevent) {
		Query query = em.createNamedQuery("Event.getEvent");
		query.setParameter("idevent", idevent);

		Event event = (Event) query.getSingleResult();

		return event.toString();
	}
	@Override
	public String addBooking(int idevent,String seat,String username) throws Exception{
		//return ""+checkAvailability2(idevent,seat);
		
		//return checkReservation(idevent, seat);
		try {
			if(checkAvailability(idevent)){
				
				if ( checkAvailability2(idevent, seat)){
					if(checkReservation(idevent, seat)){
				
			
						UserTransaction utx = context.getUserTransaction();
				
						utx.begin();
					
						Booking booking = new Booking();
						booking.setIdEvent(idevent);
						booking.setSeat(seat);
						booking.setUserName(username);
					
						em.persist(booking);
						utx.commit();
						return booking.toString();
					}
					else {
						return "seat already reserved";
					}
				}
				else {
					return "tickets sold out,try other sections";
				}
			}
			else {
				return "all tickets for all sections sold out, maybe next time!";
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
				
			//}
			//else {
			//	return "booking failed2";
			//}
		/*} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "booking failed1";
		}
		*/
}

	
	// pour tester le fonctionnement de la table BOOKING
		@Override
	public String showBookingBySeat(String seat) {
		Query query = em.createNamedQuery("Booking.getBookingBySeat");
		query.setParameter("seat", seat);

		List<Booking> Bookings = (List<Booking>) query.getResultList();

		return Bookings.toString();
	};
	public boolean checkReservation(int idevent, String seat) throws Exception{
				
		try {
			Query query = em.createNamedQuery("Booking.getBookingByIdEventandSeat");
			query.setParameter("idEvent",idevent);
			query.setParameter("seat",seat);
			int number = query.getResultList().size();
			if (number>0){
				return false;
			}
			else{
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean checkAvailability(int idevent) throws Exception {
		try{
		Query query = em.createNamedQuery("Booking.getSeatsoccupiednumber");
		query.setParameter("idEvent",idevent);
		long number = (long) query.getSingleResult();
		//return number;
		if (number < 670){
			return true;
		}
		else {return false;}
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}

	}
	public boolean checkAvailability2(int idevent, String seat)throws Exception{
		try{
			char firstletter = seat.charAt(0);
			switch (firstletter){
			case 'A':
				Query query1 = em.createNamedQuery("Booking.getBookingByEventandsectionA");
				query1.setParameter("idEvent",idevent);
				//query1.setParameter("seat",seat);
				if ((long) query1.getSingleResult() < 25){
					return true;
				}
				else {return false;}
			case 'B':
				Query query2 = em.createNamedQuery("Booking.getBookingByEventandsectionB");
				query2.setParameter("idEvent",idevent);
				//query2.setParameter("seat",seat);
				if ((long) query2.getSingleResult() < 45){
					return true;
				}
				else {return false;}
			
			case 'C':
				Query query3 = em.createNamedQuery("Booking.getBookingByEventandsectionC");
				query3.setParameter("idEvent",idevent);
				//query3.setParameter("seat",seat);
				if ((long) query3.getSingleResult() < 100){
					return true;
				}
				else {return false;}
			case 'D':
				Query query4 = em.createNamedQuery("Booking.getBookingByEventandsectionD");
				query4.setParameter("idEvent",idevent);
				//query4.setParameter("seat",seat);
				if ((long) query4.getSingleResult() < 500){
					return true;
				}
				else {return false;}
			default:
				return false;
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
}
