package Util;

import java.util.Date;

import tn.esprit.infinity_server.persistence.WatchList;

public class WatchListTechnical {
	private static int id;
    private static String name;
    private static String description;
    private static Date creationDate;
    
    public static void setWatchList(WatchList w) {
        WatchListTechnical.id = w.getId();
        WatchListTechnical.name = w.getName();
        WatchListTechnical.description=w.getDescription();
        WatchListTechnical.creationDate=w.getCreationDate();  
    }
    public static void destroyWatchList(){
    	
    	WatchListTechnical.id = 0;
        WatchListTechnical.name = "";
        WatchListTechnical.description="";
        WatchListTechnical.creationDate=null;

    }
	public static int getId() {
		return id;
	}
	public static void setId(int id) {
		WatchListTechnical.id = id;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		WatchListTechnical.name = name;
	}
	public static String getDescription() {
		return description;
	}
	public static void setDescription(String description) {
		WatchListTechnical.description = description;
	}
	public static Date getCreationDate() {
		return creationDate;
	}
	public static void setCreationDate(Date creationDate) {
		WatchListTechnical.creationDate = creationDate;
	}
    
    
}
