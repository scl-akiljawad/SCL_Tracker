package com.example.scl_tracker;

import android.os.Parcel;
import android.os.Parcelable;

public class Update implements Parcelable {
    String url;
    String phone;
    double latitude;
    double longitude;
    String geo_location;

    public String getGeo_location() {
        return geo_location;
    }

    public void setGeo_location(String geo_location) {
        this.geo_location = geo_location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    Update(Parcel in){
        url=in.readString();
        phone=in.readString();
        latitude=in.readDouble();
        longitude=in.readDouble();
        geo_location=in.readString();

    }
    Update(String url,String phone,Double latitude,Double longitude,String geo_location){
        this.url=url;
        this.latitude=latitude;
        this.longitude=longitude;
        this.phone=phone;
        this.geo_location=geo_location;
    }

    public static final Creator<Update> CREATOR = new Creator<Update>() {
        @Override
        public Update createFromParcel(Parcel in) {
            return new Update(in);
        }

        @Override
        public Update[] newArray(int size) {
            return new Update[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(phone);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(geo_location);

    }
}
