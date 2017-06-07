package ke.co.debechlabs.besure.models;

/**
 * Created by chriz on 6/6/2017.
 */

public class Facility {
    private int _id;
    private String _facility_name, _facility_code, _longitude, _latitude, _county_name;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_facility_name() {
        return _facility_name;
    }

    public void set_facility_name(String _facility_name) {
        this._facility_name = _facility_name;
    }

    public String get_longitude() {
        return _longitude;
    }

    public void set_longitude(String _longitude) {
        this._longitude = _longitude;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }

    public String get_county_name() {
        return _county_name;
    }

    public void set_county_name(String _county_name) {
        this._county_name = _county_name;
    }

    public String get_facility_code() {
        return _facility_code;
    }

    public void set_facility_code(String _facility_code) {
        this._facility_code = _facility_code;
    }
}
