package client.model;

import java.util.Date;

public class Appointment {

	private Date start;
	private Date end;
	private String description;
	private String status;
	private String location;
	private String name;

	public Appointment(Date start, Date end, String status) {
		super();
		this.start = start;
		this.end = end;
		this.status = status;
	}

	public Appointment(Date start, Date end, String description, String status,
			String location) {
		super();
		this.start = start;
		this.end = end;
		this.description = description;
		this.status = status;
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
