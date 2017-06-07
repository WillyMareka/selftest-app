package ke.co.debechlabs.besure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ke.co.debechlabs.besure.Database.DatabaseHandler;
import ke.co.debechlabs.besure.app.AppController;
import ke.co.debechlabs.besure.app.Config;
import ke.co.debechlabs.besure.models.County;
import ke.co.debechlabs.besure.models.Facility;
import ke.co.debechlabs.besure.models.Pharmacy;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final DatabaseHandler db = new DatabaseHandler(this);

        int number_of_counties = db.getCountiesCount();

        if(number_of_counties == 0){
            db.clearDatabase();

            String url = Config.BASE_URL + "getData";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status") == true){
                            JSONObject dataObj = response.getJSONObject("data");

                            JSONArray countiesArray = dataObj.getJSONArray("counties");
                            JSONArray pharmaciesArray = dataObj.getJSONArray("pharmacies");
                            JSONArray facilitiesArray = dataObj.getJSONArray("facilities");

                            List<County> countyList = new ArrayList<County>();
                            List<Pharmacy> pharmacyList = new ArrayList<Pharmacy>();
                            List<Facility> facilityList = new ArrayList<Facility>();

                            for (int i = 0; i < countiesArray.length(); i++) {
                                County county = new County();
                                JSONObject countyObj = countiesArray.getJSONObject(i);
                                county.set_id(countyObj.getInt("id"));
                                county.setCounty_name(countyObj.getString("county_name"));

                                countyList.add(county);
                            }

                            for (int i = 0; i < pharmaciesArray.length(); i++) {
                                Pharmacy pharmacy = new Pharmacy();
                                JSONObject pharmacyObj = pharmaciesArray.getJSONObject(i);

                                pharmacy.set_id(pharmacyObj.getInt("id"));
                                pharmacy.set_pharmacy_name(pharmacyObj.getString("pharmacy_name"));
                                pharmacy.set_location(pharmacyObj.getString("pharmacy_location"));
                                pharmacy.set_longitude(pharmacyObj.getString("pharmacy_longitude"));
                                pharmacy.set_latitude(pharmacyObj.getString("pharmacy_latitude"));
                                pharmacy.set_county_id(pharmacyObj.getInt("county_id"));

                                pharmacyList.add(pharmacy);
                            }

                            for (int i = 0; i < facilitiesArray.length(); i++) {
                                Facility facility = new Facility();
                                JSONObject facilityObj = facilitiesArray.getJSONObject(i);

                                facility.set_id(facilityObj.getInt("id"));
                                facility.set_facility_code(facilityObj.getString("facility_code"));
                                facility.set_facility_name(facilityObj.getString("facility_name"));
                                facility.set_latitude(facilityObj.getString("latitude"));
                                facility.set_longitude(facilityObj.getString("longitude"));
                                facility.set_county_name(facilityObj.getString("county_name"));

                                facilityList.add(facility);
                            }

                            db.addCounties(countyList);
                            db.addPharmacies(pharmacyList);
                            db.addFacilities(facilityList);

                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SplashActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        }else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
