package it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.elements;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceInformationElement;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

/**
 * Location Info in terms of [Latitude, Longitude, Altitude, Range]
 *
 * @author nextworks
 *
 */
@Embeddable
public class LocationInfo implements InterfaceInformationElement {

	private double latitude;
	private double longitude;
	private float altitude;  //It could be int (or omitted)
	private float range;

	public LocationInfo() { }

	/**
	 * Constructor
	 *
	 * @param latitude	Latitude of the element.
	 * @param longitude Longitude of the element.
	 * @param altitude  Altitude of the element.
	 * @param range Range (coverage) of the element.
	 */
	public LocationInfo(double latitude, double longitude, float altitude, float range) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.range = range;
	}

	/**
	 * @return the latitude.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return the altitude.
	 */
	public float getAltitude() {
		return altitude;
	}

	/**
	 *
	 * @return the range
	 */
	public float getRange() {
		return range;
	}

	@Override
	public void isValid() throws MalformattedElementException {
		// Nothing to do.
	}
	
	@JsonIgnore
	public boolean isMeaningful() {
		if ((latitude != 0) && (longitude != 0)) return true;
		return false;
	}

}
