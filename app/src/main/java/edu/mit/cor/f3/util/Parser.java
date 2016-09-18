/*
 * Instantiate using input string of title and body (nothing in between)
 * Public functions:
 * 		isNum() returns true if it returns building number, false if building name
 * 		getLocation() returns the result of the parse
 */

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private String location = null;
	private Boolean isNum = false;
	private String delimeter = "(N|n|W|w|E|e|NW|nw|NE|ne)+\\d{1,2}";
	private String otherPattern = "\\s\\d{1,2}";
	private Map<String,String> dict = new HashMap<String,String>();
	private String[] places = {"Zesiger", "Kresge", "Stata","Stratton", "Lobby 7", "Lobby 10", "Next house","New House","Chocolate City","iHouse","Spanish House","German House","East Campus","Burton","Conner","random house","macgregor","baker","senior house", "simmons","maseeh","mccormick","rockwell","dupont","johnson","walker"};
	
	// instantiator (or whatever this is called)
	public Parser(String in){
		startDictionary();
		location = locationParse(in);
	}
	
	public void startDictionary(){
		dict.put("stud","w20");
		dict.put("ec","east campus");
		dict.put("bc","burton conner");
		dict.put("sponge","simmons");
		dict.put("the z", "zesiger");
		dict.put("z center","zesiger");
		dict.put("killian","killian court");
		dict.put("student center","stratton");
		dict.put("connor","conner");
		dict.put("senior haus","senior house");
	}
	
	// returns location
	public String locationParse(String in){
		// returns any of the locations in places
		for (String place: places){
			place = place.toLowerCase();
			for (int i = 0; i < in.length()-place.length()+1; i++){
				if (place.equalsIgnoreCase(in.substring(i,i+place.length()))) {
					return place;
				}
				
			}
		}
		
		// returns locations of nicknames
		for (String key: dict.keySet()){
			key = key.toLowerCase();
			for (int i = 0; i < in.length()-key.length()+1; i++){
				if (key.equalsIgnoreCase(in.substring(i,i+key.length()))) {
					return dict.get(key);
				}
				
			}
		}
		
		// returns the numbers by looking for delimeters
		Pattern p = Pattern.compile(delimeter);
		Matcher m = p.matcher(in);
		if (m.find()) {
			isNum = true;
			return m.group(0);
		}
		Pattern pa = Pattern.compile(otherPattern);
		Matcher ma = pa.matcher(in);
		if (ma.find()) {
			String temp = ma.group(0);
			String out = "";
			for (int i = temp.length()-1; i > -1; i--){
				if (temp.substring(i,i+1).equals(" ")) break;
				out = temp.substring(i,i+1) + out;
			}
			isNum = true;
			return out;
		}
		
		return null;
	}
	
	// returns location parse from email
	public String getLocation(){
		return location;
	}
	
	// returns whether or not it is a building number
	public Boolean isNum(){
		return isNum;
	}
}
