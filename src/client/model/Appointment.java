package client.model;

import java.util.Date;

public class Appointment {

	private String start;
	private String end;
	private int week;
	private String description;
	private String location;
	private String name;

	public Appointment(String start, String end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Appointment(String name, String start, String end, String description,
			String location) {
		super();
		this.name = name;
		this.start = start;
		this.end = end;
		this.description = description;
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setWeek(int week) {
		this.week = week;
	}
	
	public int getWeek() {
		return week;
	}

}
