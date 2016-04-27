package edu.rutgers.database.model;

public class LocationDTO {
	
	private String stateCode;
	private long cityId;
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public LocationDTO(String stateCode, long cityId) {
		super();
		this.stateCode = stateCode;
		this.cityId = cityId;
	}
	@Override
	public String toString() {
		return "LocationDTO [stateCode=" + stateCode + ", cityId=" + cityId + "]";
	}
	
	

}
