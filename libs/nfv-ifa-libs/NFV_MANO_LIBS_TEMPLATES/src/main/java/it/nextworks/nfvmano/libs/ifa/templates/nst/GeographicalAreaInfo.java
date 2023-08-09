package it.nextworks.nfvmano.libs.ifa.templates.nst;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Given the geographical coordinates expressed in latitude and longitude, the geographical area info represents an
 * area with shape of rectangle. Starting from the coordinates that represents the center, the rectangle is bounded
 * on the latitude by +/- LAT_DISTANCE and by +/- longitude LON_DISTANCE.
 * E.g. Lat = 10.00, Lon = 20.00
 * The rectangle is bounded by: [19.99, 9.995],[19.99, 10.005],[20,01, 9.995],[20,01, 10.005]
 */

@Entity
public class GeographicalAreaInfo {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private static final double LAT_MAX_DISTANCE=0.005;
    private static final double LON_MAX_DISTANCE=0.01;
    private String description;
    private double lat;
    private double lon;
    private boolean used;

    public GeographicalAreaInfo(){//for JPA Only
         }
    public GeographicalAreaInfo(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.used=false;
    }


    public GeographicalAreaInfo(String description, double lat, double lon){
        this.description=description;
        this.lat = lat;
        this.lon = lon;
        this.used=false;
    }

    public GeographicalAreaInfo(GeographicalAreaInfo geographicalAreaInfo){
        this.description=geographicalAreaInfo.description;
        this.lat = geographicalAreaInfo.lat;
        this.lon = geographicalAreaInfo.lon;
        this.used=geographicalAreaInfo.used;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Calculate distance between two points in latitude and longitude.
     * Uses Haversine method as its base.
     *
     *
     * @returns Distance in Meters
     */
    public double computeDistanceMeter(double lat2, double lon2) {
        return computeDistanceBetweenPoints(getLat(), lat2, getLon(), lon2);
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }


    public Long getId() {
        return id;
    }


    private double computeDistanceBetweenPoints(double lat1, double lat2, double lon1, double lon2){
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);


    }
    public Map<String,Double> getDistanceToTheEdgesInMeter(double lat, double lon){
        Map<String,Double> distancesToTheEdges = new HashMap<>();
        Map<String,Double []> edges=getEdges();
        for(String edgeName: edges.keySet()){
            Double [] coordinates=edges.get(edgeName);
            Double latEdge = coordinates[0];
            Double lonEdge = coordinates[1];
            double edgeDistance=computeDistanceBetweenPoints(latEdge, lat,lonEdge, lon);
            distancesToTheEdges.put("DIST_TO_"+edgeName,edgeDistance);
        }
        return distancesToTheEdges;
    }


    public Map<String,Double []> getEdges(){
        Map<String,Double []> coordinates = new HashMap<>();

        Double [] topLeft = new Double[2];
        topLeft[0]= lat+LAT_MAX_DISTANCE;
        topLeft[1]= lon-LON_MAX_DISTANCE;
        coordinates.put("TOP_LEFT",topLeft);

        Double [] topRight = new Double[2];
        topRight[0]= lat+LAT_MAX_DISTANCE;
        topRight[1]= lon+LON_MAX_DISTANCE;
        coordinates.put("TOP_RIGHT",topRight);

        Double [] bottomRight = new Double[2];
        bottomRight[0]= lat-LAT_MAX_DISTANCE;
        bottomRight[1]= lon+LON_MAX_DISTANCE;
        coordinates.put("BOTTOM_RIGHT",bottomRight);

        Double [] bottomLeft = new Double[2];
        bottomLeft[0]= lat-LAT_MAX_DISTANCE;
        bottomLeft[1]= lon-LON_MAX_DISTANCE;
        coordinates.put("BOTTOM_LEFT",bottomLeft);

        return coordinates;
    }
}
