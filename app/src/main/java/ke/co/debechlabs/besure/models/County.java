package ke.co.debechlabs.besure.models;

/**
 * Created by chriz on 6/6/2017.
 */

public class County {
    private int _id;
    private String county_name;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCounty_name() {
        return county_name;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }
}
