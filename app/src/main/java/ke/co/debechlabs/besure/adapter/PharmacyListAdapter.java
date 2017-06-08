package ke.co.debechlabs.besure.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ke.co.debechlabs.besure.Database.DatabaseHandler;
import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.models.Pharmacy;

/**
 * Created by chriz on 6/8/2017.
 */

public class PharmacyListAdapter extends BaseAdapter {
    Activity activity;
    List<Pharmacy> pharmacyList;
    LayoutInflater inflater;
    public PharmacyListAdapter(Activity a, List<Pharmacy> pharmacies){
        this.activity = a;
        this.pharmacyList = pharmacies;
    }
    @Override
    public int getCount() {
        return pharmacyList.size();
    }

    @Override
    public Object getItem(int position) {
        return pharmacyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.pharmacy_list_item, null);

        Pharmacy pharmacy = pharmacyList.get(position);
        DatabaseHandler db = new DatabaseHandler(activity);

        TextView txtPharmacyName = (TextView) convertView.findViewById(R.id.pharmacy_name);
        TextView txtPharmacyCounty = (TextView) convertView.findViewById(R.id.county_name);
        TextView txtPharmacyID = (TextView) convertView.findViewById(R.id.pharmacy_id);

        txtPharmacyName.setText(pharmacy.get_pharmacy_name());
        txtPharmacyID.setText(String.valueOf(pharmacy.get_id()));
        txtPharmacyCounty.setText(db.getCounty(pharmacy.get_county_id()).getCounty_name());

        return convertView;
    }
}
