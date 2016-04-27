package edu.rutgers.database.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.model.Request;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import edu.rutgers.database.model.Business;
import edu.rutgers.database.model.LocationDTO;

public class LocuTest {
	
	/*public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      Request request = new Request(Verb.GET, "https://developers.zomato.com/api/v2.1/search?q=Pizza%20Somerset%20NJ");
	      request.addHeader("user-key", "251a3dc1ee904f8ccbb97ff8c4ef20a1");
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }*/

	private static final String LOCU_API_ID = "f165c0e560d0700288c2f70cf6b26e0c2de0348f";

	public static JSONObject httpPOST(String url) throws ParseException {
		//Request request = new Request(Verb.GET, "https://developers.zomato.com/api/v2.1/search?q=Pizza%20Somerset%20NJ");
		Request request = createRequest(url);
	    return postRequest(request);
	}

	private static Request createRequest(String url) {
		Request request = new Request(Verb.POST, url);
	    request.addHeader("Accept", "application/json");
	    request.addHeader("api_key", LOCU_API_ID);
		return request;
	}

	private static JSONObject postRequest(Request request) throws ParseException {
		Response response = request.send();
	    
	    JSONParser jsonParser = new JSONParser();
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
	    
	    return jsonObject;
	}
	
	public static LocationDTO getLocationFromCityAndState(String citySearch, String stateCode) throws UnsupportedEncodingException, ParseException{
		String url = "https://developers.zomato.com/api/v2.1/cities?q="+URLEncoder.encode(citySearch, "UTF-8");
		JSONObject locationObject = httpPOST(url);
		JSONArray locationArray =  (JSONArray) locationObject.get("location_suggestions");
		LocationDTO locationDTO = null;
		Iterator iterator = locationArray.iterator();
		
		while(iterator.hasNext()){
			JSONObject next = (JSONObject) iterator.next();
			if(((String)next.get("state_code")).equalsIgnoreCase(stateCode)){
				long cityId = (long) next.get("id");
				locationDTO = new LocationDTO(stateCode, cityId);
				break;
			}
		}
		return locationDTO;
	}
	
	public static void locuSearchWithLocation(String city, String query) throws UnsupportedEncodingException, ParseException{
		String url = "https://api.locu.com/v2/venue/search";
		//JSONObject results = httpPOST(url);
		Request request = createRequest(url);
		
		request.addHeader("api_key", LOCU_API_ID);
		
		/*String[] fields = new String[]{"name", "menu_items"};
		request.addQuerystringParameter("fields", fields.toString());*/
		
		JSONObject locality = new JSONObject();
		locality.put("locality", city);
		
		JSONObject location = new JSONObject();
		location.put("location", locality);
		
		request.addQuerystringParameter("venue_queries", location.toJSONString());
		
		JSONObject queryJSON = new JSONObject();
		queryJSON.put("name", query);
		
		request.addQuerystringParameter("menu_item_queries", queryJSON.toJSONString());
		
		System.out.println(postRequest(request));
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
		String city = "New York";
		String stateCode = "NY";
		String searchTerm = "Pizza";
		/*List<Business> zomatoSearch = zomatoSearch(city, stateCode, searchTerm);
		for (Business business : zomatoSearch) {
			System.out.println(business);
		}*/
		locuSearchWithLocation(city, searchTerm);
	}

	/*public static List<Business> zomatoSearch(String city, String stateCode, String searchTerm){
		try {
			LocationDTO locationFromCityAndState = getLocationFromCityAndState(city, stateCode);
			return locuSearchWithLocation(locationFromCityAndState.getCityId(), searchTerm);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/

}
