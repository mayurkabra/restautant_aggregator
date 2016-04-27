package edu.rutgers.database.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import edu.rutgers.database.model.Business;

/**
 * Code sample for accessing the Yelp API V2.
 * 
 * This program demonstrates the capability of the Yelp API version 2.0 by using the Search API to
 * query for businesses by a search term and location, and the Business API to query additional
 * information about the top result from the search query.
 * 
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
 * 
 */
public class YelpAPI {

	private static final String API_HOST = "api.yelp.com";
	private static final String DEFAULT_TERM = "dinner";
	private static final String DEFAULT_LOCATION = "San Francisco, CA";
	private static final int SEARCH_LIMIT = 10;
	private static final String SEARCH_PATH = "/v2/search";
	private static final String BUSINESS_PATH = "/v2/business";

	/*
	 * Update OAuth credentials below from the Yelp Developers API site:
	 * http://www.yelp.com/developers/getting_started/api_access
	 */
	private static final String CONSUMER_KEY = "UjsTOryMCGxfBKjmHNrQ6A";
	private static final String CONSUMER_SECRET = "Hp_DirNLp5n8wN0_qt1f18xAHk4";
	private static final String TOKEN = "4lGDRtkfC1R0sjk4fqESEWDYoicR9mQz";
	private static final String TOKEN_SECRET = "13hRven0k5tFIGVs1xXtj-vjmsA";

	OAuthService service;
	Token accessToken;

	/**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * @param consumerKey Consumer key
	 * @param consumerSecret Consumer secret
	 * @param token Token
	 * @param tokenSecret Token secret
	 */
	public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
		this.service =
				new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
				.apiSecret(consumerSecret).build();
		this.accessToken = new Token(token, tokenSecret);
	}

	/**
	 * Creates and sends a request to the Search API by term and location.
	 * <p>
	 * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API V2</a>
	 * for more info.
	 * 
	 * @param term <tt>String</tt> of the search term to be queried
	 * @param location <tt>String</tt> of the location
	 * @return <tt>String</tt> JSON Response
	 */
	public String searchForBusinessesByLocation(String term, String location) {
		OAuthRequest request = createOAuthRequest(SEARCH_PATH);
		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("location", location);
		request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
		return sendRequestAndGetResponse(request);
	}

	/**
	 * Creates and sends a request to the Business API by business ID.
	 * <p>
	 * See <a href="http://www.yelp.com/developers/documentation/v2/business">Yelp Business API V2</a>
	 * for more info.
	 * 
	 * @param businessID <tt>String</tt> business ID of the requested business
	 * @return <tt>String</tt> JSON Response
	 */
	public String searchByBusinessId(String businessID) {
		OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);
		return sendRequestAndGetResponse(request);
	}

	/**
	 * Creates and returns an {@link OAuthRequest} based on the API endpoint specified.
	 * 
	 * @param path API endpoint to be queried
	 * @return <tt>OAuthRequest</tt>
	 */
	private OAuthRequest createOAuthRequest(String path) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + path);
		return request;
	}

	/**
	 * Sends an {@link OAuthRequest} and returns the {@link Response} body.
	 * 
	 * @param request {@link OAuthRequest} corresponding to the API request
	 * @return <tt>String</tt> body of API response
	 */
	private String sendRequestAndGetResponse(OAuthRequest request) {
		System.out.println("Querying " + request.getCompleteUrl() + " ...");
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();
		return response.getBody();
	}

	/**
	 * Queries the Search API based on the command line arguments and takes the first result to query
	 * the Business API.
	 * 
	 * @param yelpApi <tt>YelpAPI</tt> service instance
	 * @param yelpApiCli <tt>YelpAPICLI</tt> command line arguments
	 * @return 
	 */
	private static JSONObject queryAPI(YelpAPI yelpApi, YelpAPICLI yelpApiCli) {
		String searchResponseJSON =
				yelpApi.searchForBusinessesByLocation(yelpApiCli.term, yelpApiCli.location);

		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(searchResponseJSON);
			return response;
		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
			System.out.println(searchResponseJSON);
			System.exit(1);
		}

		JSONArray businesses = (JSONArray) response.get("businesses");
		int index = 0;
		for (index = 0; index < businesses.size(); index++) {
			JSONObject business = (JSONObject) businesses.get(index);
			String firstBusinessID = business.get("id").toString();
			System.out.println(String.format("Querying business info for "+firstBusinessID+" ..."));
			// Select the first business and display business details
			String businessResponseJSON = yelpApi.searchByBusinessId(firstBusinessID.toString());
			System.out.println(String.format("Result for business "+firstBusinessID+" found:"));
			System.out.println(businessResponseJSON);
		}
		return response;
	}

	/**
	 * Command-line interface for the sample Yelp API runner.
	 */
	private static class YelpAPICLI {
		@Parameter(names = {"-q", "--term"}, description = "Search Query Term")
		public String term = DEFAULT_TERM;

		@Parameter(names = {"-l", "--location"}, description = "Location to be Queried")
		public String location = DEFAULT_LOCATION;
	}

	/**
	 * Main entry for sample Yelp API requests.
	 * <p>
	 * After entering your OAuth credentials, execute <tt><b>run.sh</b></tt> to run this example.
	 */
	public static void main(String[] args) {
		String term = "Pizza";
		List<Business> yelpSearch = yelpSearch("New York", "NY", term);
		for (Business business : yelpSearch) {
			System.out.println(business);
		}
	}

	public static List<Business> yelpSearch(String city, String stateCode, String searchTerm) {
		YelpAPICLI yelpApiCli = new YelpAPICLI();
		yelpApiCli.term=searchTerm;
		yelpApiCli.location=city+", "+stateCode;
		new JCommander(yelpApiCli);

		YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
		JSONObject queryResponse = queryAPI(yelpApi, yelpApiCli);

		JSONArray businesses = (JSONArray) queryResponse.get("businesses");
		Iterator iterator = businesses.iterator();
		
		List<Business> yelpBusinesses = new ArrayList<>();

		while(iterator.hasNext()){
			JSONObject yelpBusiness = (JSONObject) iterator.next();
			Business business = new Business();
			business.setName((String) yelpBusiness.get("name"));
			try {
				business.setAddress((String) ((JSONArray)((JSONObject)yelpBusiness.get("location")).get("address")).get(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			business.setContactNumber((String) yelpBusiness.get("phone"));
			business.setRating(((Double)yelpBusiness.get("rating")).floatValue());
			business.setRatingCount(((Long)yelpBusiness.get("review_count")));
			business.setCountOfSources(1);
			double latitude = (double) ((JSONObject)((JSONObject)yelpBusiness.get("location")).get("coordinate")).get("latitude");
			business.setLatitude(latitude);
			double longitude = (double) ((JSONObject)((JSONObject)yelpBusiness.get("location")).get("coordinate")).get("longitude");
			business.setLongitude(longitude);
			//business.setCategories(categories);

			//System.out.println(business.toString());
			yelpBusinesses.add(business);
		}
		return yelpBusinesses;
	}
}
