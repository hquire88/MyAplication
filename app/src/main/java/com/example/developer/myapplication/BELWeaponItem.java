package com.example.developer.myapplication;

public class BELWeaponItem {

    private Location location;
    private float radius;
    private float radiusInMeter;
    private String code;
    private String kind;

    public BELWeaponItem () {}

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadiusInMeter() {
        return radiusInMeter;
    }

    public void setRadiusInMeter(float radiusInMeter) {
        this.radiusInMeter = radiusInMeter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public android.location.Location getGeoLocation() {
        android.location.Location geoLocation = new android.location.Location("");
        geoLocation.setLatitude(this.location.latitude);
        geoLocation.setLongitude(this.location.longitude);

        return geoLocation;
    }

    public class Location {
        private double latitude;
        private double longitude;

        public Location(){}

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
    }
}
