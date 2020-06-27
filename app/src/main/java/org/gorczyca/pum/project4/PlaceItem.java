package org.gorczyca.pum.project4;

/**
 * Created by: Radosław Gorczyca
 * 27.06.2020 19:29
 */
class PlaceItem {

    private String name;
    private Double lat;
    private Double lng;
    private String additionalInfo;

    public PlaceItem(String name, Double lat, Double lng, String additionalInfo) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.additionalInfo = additionalInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
