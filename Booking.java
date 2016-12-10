package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the EVENTS database table.
 * 
 */
@Entity
@Table(name = "BOOKING")

@NamedQueries({
	@NamedQuery(name = "Booking.getBookingByIdEvent", query = "SELECT b FROM Booking b WHERE b.idEvent = :idEvent"),
	@NamedQuery(name = "Booking.getBookingByUserName", query = "SELECT b FROM Booking b WHERE b.userName = :userName"),
	@NamedQuery(name = "Booking.getBookingBySeat", query = "SELECT b FROM Booking b WHERE b.seat = :seat"),
	@NamedQuery(name = "Booking.getBookingByIdEventandSeat", query = "SELECT b FROM Booking b WHERE b.seat = :seat AND b.idEvent = :idEvent"),
	@NamedQuery(name = "Booking.getSeatsoccupiednumber", query = "SELECT COUNT(b) FROM Booking b WHERE b.idEvent = :idEvent"),
})

public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(name = "IDEVENT")
	private int idEvent;

	@Column(name = "SEAT")
	private String seat;

	public Booking() {
	}

	
	public int getIdEvent() {
		return this.idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSeat() {
		return this.seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	@Override
	public String toString() {
		return "Booking [UserName=" + userName + ", idevent=" + idEvent + ", seat=" + seat
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else {
			if (idEvent == 0) {
				if (other.idEvent != 0)
					return false;
			} else {
				if (seat == null) {
					if (other.seat!= null)
						return false;
				} else {
					if (!userName.equals(other.userName) || !seat.equals(other.seat) || idEvent != other.idEvent){
						return false;
					}
				}
			}
		}
		return true;
	}

}