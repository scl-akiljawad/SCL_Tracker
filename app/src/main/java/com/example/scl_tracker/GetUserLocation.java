package com.example.scl_tracker;

class GetUserLocation {
    public int id ;
    public String phone ;
    public double lat ;
    public double lon ;
    public String geolocation ;
    public String created_at ;
    public String status ;

    public GetUserLocation(int id, String phone, double lat, double lon, String geolocation, String created_at, String status) {
        this.id = id;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
        this.geolocation = geolocation;
        this.created_at = created_at;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
