package client.model;

import java.util.Collection;
import java.util.LinkedList;

// List of available rooms
public class Rooms {

	private Collection rooms; 
	Rooms(){
		rooms = new LinkedList<Room>();
	}
	
	class Room{
		String roomNumber;
		int roomCapacity;
	}
}
