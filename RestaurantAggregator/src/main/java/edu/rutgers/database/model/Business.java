package edu.rutgers.database.model;

import java.util.ArrayList;
import java.util.List;

public class Business implements Comparable<Business>{
	
	String name;
	Float rating;
	long ratingCount;
	int countOfSources;
	String contactNumber;
	String address;
	List<String> categories;
	double latitude;
	double longitude;
	boolean duplicate;
	Double distance;
	List<String> reviews;
	
	public Business() {
		super();
		categories = new ArrayList<>();
		contactNumber="";
		address="";
		duplicate=false;
		reviews = new ArrayList<>();
	}
	public boolean isDuplicate() {
		return duplicate;
	}
	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	public int getCountOfSources() {
		return countOfSources;
	}
	public void setCountOfSources(int countOfSources) {
		this.countOfSources = countOfSources;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public long getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(long ratingCount) {
		this.ratingCount = ratingCount;
	}
	
	public List<String> getReviews() {
		return reviews;
	}
	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}
	@Override
	public String toString() {
		return "Business [name=" + name + ", rating=" + rating + ", ratingCount=" + ratingCount + ", countOfSources="
				+ countOfSources + ", contactNumber=" + contactNumber + ", address=" + address + ", categories="
				+ categories + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	@Override
	public int compareTo(Business o) {
		return o.rating.compareTo(this.getRating());
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
	
	

}
