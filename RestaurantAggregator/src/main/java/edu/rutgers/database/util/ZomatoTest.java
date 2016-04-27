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

public class ZomatoTest {
	
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

	private static final String ZOMATO_USER_ID = "251a3dc1ee904f8ccbb97ff8c4ef20a1";

	public static JSONObject httpGET(String url) throws ParseException {
		//Request request = new Request(Verb.GET, "https://developers.zomato.com/api/v2.1/search?q=Pizza%20Somerset%20NJ");
		Request request = new Request(Verb.GET, url);
	    request.addHeader("Accept", "application/json");
	    request.addHeader("user-key", ZOMATO_USER_ID);
	    Response response = request.send();
	    
	    JSONParser jsonParser = new JSONParser();
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
	    
	    return jsonObject;
	}
	
	public static LocationDTO getLocationFromCityAndState(String citySearch, String stateCode) throws UnsupportedEncodingException, ParseException{
		String url = "https://developers.zomato.com/api/v2.1/cities?q="+URLEncoder.encode(citySearch, "UTF-8");
		JSONObject locationObject = httpGET(url);
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
	
	public static List<Business> zomatoSearchWithLocation(long cityId, String query) throws UnsupportedEncodingException, ParseException{
		String url = "https://developers.zomato.com/api/v2.1/search?entity_id="+cityId+"&entity_type=city&q="+URLEncoder.encode(query, "UTF-8");
		JSONObject results = httpGET(url);
		JSONArray restaurants = (JSONArray) results.get("restaurants");
		
		Iterator iterator = restaurants.iterator();
		List<Business> zomatoBusinesses = new ArrayList<>();
		
		while(iterator.hasNext()){
			JSONObject innerRestaurant = (JSONObject) iterator.next();
			JSONObject restaurant = (JSONObject) innerRestaurant.get("restaurant");
			Business business = new Business();
	    	business.setName((String) restaurant.get("name"));
	    	business.setAddress((String)((JSONObject)restaurant.get("location")).get("address"));
	    	business.setRating(Float.parseFloat((String) ((JSONObject)restaurant.get("user_rating")).get("aggregate_rating")));
	    	business.setRatingCount(Long.parseLong((String) ((JSONObject)restaurant.get("user_rating")).get("votes")));
			business.setCountOfSources(1);
	    	double latitude = Double.parseDouble((String)((JSONObject)restaurant.get("location")).get("latitude"));
			business.setLatitude(latitude);
	    	double longitude = Double.parseDouble((String)((JSONObject)restaurant.get("location")).get("longitude"));
			business.setLongitude(longitude);
	    	
	    	//System.out.println(business.toString());
			zomatoBusinesses.add(business);
		}
		return zomatoBusinesses;
	}
	
	public static void main(String[] args) {
		String city = "New York";
		String stateCode = "NY";
		String searchTerm = "Pizza";
		List<Business> zomatoSearch = zomatoSearch(city, stateCode, searchTerm);
		for (Business business : zomatoSearch) {
			System.out.println(business);
		}
	}

	public static List<Business> zomatoSearch(String city, String stateCode, String searchTerm){
		try {
			LocationDTO locationFromCityAndState = getLocationFromCityAndState(city, stateCode);
			if (locationFromCityAndState !=null) {
				return zomatoSearchWithLocation(locationFromCityAndState.getCityId(), searchTerm);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

}
