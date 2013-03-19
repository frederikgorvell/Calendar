package shared.model;

public class Appointment {

	private String creator;
	private int AID = -1;
	private String name;
	private String start;
	private String end;
	private int week;
	private String description;
	private String location;
	private String other;
	
	public Appointment() {
	}
	
	public Appointment(int AID) {
		this.AID = AID;
	}
	
	public Appointment(String start, String end) {
		this.start = start;
		this.end = end;
	}

	public Appointment(String name, String start, String end, String description, String location) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.description = description;
		this.location = location;
	}
	
	public Appointment(int AID, String name, String start, String end, String description, String location) {
		this.AID = AID;
		this.name = name;
		this.start = start;
		this.end = end;
		this.description = description;
		this.location = location;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setAID(int AID) {
		this.AID = AID;
	}
	
	public int getAID() {
		return AID;
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
	
	public void setOther(String other) {
		this.other = other;
	}
	
	public String getOther() {
		return other;
	}

}
