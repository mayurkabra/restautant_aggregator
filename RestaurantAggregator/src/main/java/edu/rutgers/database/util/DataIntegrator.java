package edu.rutgers.database.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.rutgers.database.model.Business;

public class DataIntegrator {

	public static void main(String[] args) {
		String city = "New York";
		String stateCode = "NY";
		String searchTerm = "Pizza";
		
		List<Business> aggregatedValue = locationAndQuerySearch(city, stateCode, searchTerm);
		
		for (Business business : aggregatedValue) {
			System.out.println(business);
		}

	}

	public static List<Business> locationAndQuerySearch(String city, String stateCode, String searchTerm) {
		List<Business> yelpSearch = YelpAPI.yelpSearch(city, stateCode, searchTerm);
		List<Business> zomatoSearch = ZomatoTest.zomatoSearch(city, stateCode, searchTerm);
		DecimalFormat decimalFormat = new DecimalFormat("##.##");
		List<Business> aggregatedValue = new ArrayList<>();
		
		for (Business yelpB : yelpSearch) {
			for (Business zomatoB : zomatoSearch) {
				if(yelpB.getName().equalsIgnoreCase(zomatoB.getName())){
					if(Math.abs(yelpB.getLatitude()-zomatoB.getLatitude())<0.0009 && Math.abs(yelpB.getLongitude()-zomatoB.getLongitude())<0.0009){
						yelpB.setDuplicate(true);
						zomatoB.setDuplicate(true);
						
						if (yelpB.getRatingCount()>0) {
							float rating = (yelpB.getRating()*yelpB.getRatingCount() + zomatoB.getRating()*zomatoB.getRatingCount()) / (yelpB.getRatingCount()+zomatoB.getRatingCount());
							yelpB.setRating(Float.parseFloat(decimalFormat.format(rating)));
						}
						yelpB.setRatingCount(yelpB.getRatingCount()+zomatoB.getRatingCount());
						yelpB.setCountOfSources(yelpB.getCountOfSources()+1);
						aggregatedValue.add(yelpB);
					}
				}
			}
			if(!yelpB.isDuplicate()){
				aggregatedValue.add(yelpB);
			}
		}
		
		if(zomatoSearch!=null){
			for (Business z : zomatoSearch) {
				if(!z.isDuplicate()){
					aggregatedValue.add(z);
				}
			}
		}
		
		Collections.sort(aggregatedValue);
		return aggregatedValue;
	}

}
