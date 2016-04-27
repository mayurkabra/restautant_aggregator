package edu.rutgers.database.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.rutgers.database.model.Business;
import edu.rutgers.database.util.DataIntegrator;
import edu.rutgers.database.util.LLToDist;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value="data/")
public class DataController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "restaurantSearch")
	public ModelAndView restaurantSearch(HttpServletRequest request, String city, String state, String query) {
		ModelAndView modelAndView = new ModelAndView();
		List<Business> result = DataIntegrator.locationAndQuerySearch(city, state, query);
		System.out.println(result);
		modelAndView.addObject("result", result);
		modelAndView.addObject("city", city);
		modelAndView.addObject("state", state);
		modelAndView.addObject("query", query);
		modelAndView.setViewName("restaurant_results");
		
		return modelAndView;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "search")
	public ModelAndView search(HttpServletRequest request, String city, String state, String query) {
		ModelAndView modelAndView = new ModelAndView();
		List<Business> result = DataIntegrator.locationAndQuerySearch(city, state, query);
		System.out.println(result);
		modelAndView.addObject("result", result);
		modelAndView.addObject("city", city);
		modelAndView.addObject("state", state);
		modelAndView.addObject("query", query);
		modelAndView.setViewName("display_results");
		
		return modelAndView;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "searchByDistanceForRestaurant")
	public ModelAndView searchByDistanceForRestaurant(HttpServletRequest request, String city, String state, String query, String lat, String lon, String name) {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println(lat+"---"+lon);
		List<Business> result = DataIntegrator.locationAndQuerySearch(city, state, query);
		//LinkedList<Business> closeByResult = new LinkedList<>();
		DecimalFormat decimalFormat = new DecimalFormat("##.##");
		for(Business business : result){
			double distanceFromLatLonInKm = LLToDist.getDistanceFromLatLonInKm(business.getLatitude(), business.getLongitude(), Double.parseDouble(lat), Double.parseDouble(lon));
			business.setDistance(Double.parseDouble(decimalFormat.format(distanceFromLatLonInKm)));
		}
		result.sort(new Comparator<Business>() {
			@Override
			public int compare(Business o1, Business o2) {
				return o1.getDistance().compareTo(o2.getDistance());
			}
		});
		System.out.println(result);
		modelAndView.addObject("result", result);
		modelAndView.addObject("city", city);
		modelAndView.addObject("state", state);
		modelAndView.addObject("query", query);
		modelAndView.addObject("name", name);
		modelAndView.addObject("lat", lat);
		modelAndView.addObject("lon", lon);
		modelAndView.addObject("showDistance", true);
		modelAndView.setViewName("display_results_for_restaurant");
		
		return modelAndView;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "searchByDistance")
	public ModelAndView searchByDistance(HttpServletRequest request, String city, String state, String query, String lat, String lon) {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println(lat+"---"+lon);
		List<Business> result = DataIntegrator.locationAndQuerySearch(city, state, query);
		//LinkedList<Business> closeByResult = new LinkedList<>();
		DecimalFormat decimalFormat = new DecimalFormat("##.##");
		for(Business business : result){
			double distanceFromLatLonInKm = LLToDist.getDistanceFromLatLonInKm(business.getLatitude(), business.getLongitude(), Double.parseDouble(lat), Double.parseDouble(lon));
			business.setDistance(Double.parseDouble(decimalFormat.format(distanceFromLatLonInKm)));
		}
		result.sort(new Comparator<Business>() {
			@Override
			public int compare(Business o1, Business o2) {
				return o1.getDistance().compareTo(o2.getDistance());
			}
		});
		System.out.println(result);
		modelAndView.addObject("result", result);
		modelAndView.addObject("city", city);
		modelAndView.addObject("state", state);
		modelAndView.addObject("query", query);
		modelAndView.addObject("showDistance", true);
		modelAndView.setViewName("display_results");
		
		return modelAndView;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "thisIsMe")
	public ModelAndView thisIsMe(HttpServletRequest request, String city, String state, String lat, String lon, String name) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("city", city);
		modelAndView.addObject("state", state);
		modelAndView.addObject("lat", lat);
		modelAndView.addObject("lon", lon);
		modelAndView.addObject("name", name);
		modelAndView.setViewName("restaurant_specific_search");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "restaurant")
	public ModelAndView restaurant() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("restaurant_form");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "form")
	public ModelAndView form() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("search_form");
		
		return modelAndView;
	}
	
}
