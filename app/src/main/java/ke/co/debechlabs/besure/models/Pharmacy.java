package ke.co.debechlabs.besure.models;

/**
 * Created by chriz on 6/6/2017.
 */

public class Pharmacy {
    private int _id, _county_id;
    private String _pharmacy_name, _location, _latitude, _longitude;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_county_id() {
        return _county_id;
    }

    public void set_county_id(int _county_id) {
        this._county_id = _county_id;
    }

    public String get_pharmacy_name() {
        return _pharmacy_name;
    }

    public void set_pharmacy_name(String _pharmacy_name) {
        this._pharmacy_name = _pharmacy_name;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String _latitude) {
        this._latitude = _latitude;
    }

    public String get_longitude() {
        return _longitude;
    }

    public void set_longitude(String _longitude) {
        this._longitude = _longitude;
    }
}
