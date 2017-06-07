package ke.co.debechlabs.besure.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.models.Facility;

/**
 * Created by chriz on 6/7/2017.
 */

public class ReferralListAdapter extends BaseAdapter {
    Activity activity;
    List<Facility> facilityList;
    LayoutInflater inflater;

    public ReferralListAdapter(Activity a, List<Facility> f){
        this.activity = a;
        this.facilityList = f;
    }
    @Override
    public int getCount() {
        return this.facilityList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.facilityList.get(position);
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
            convertView = inflater.inflate(R.layout.referral_list_item, null);

        TextView facility_name_txt = (TextView) convertView.findViewById(R.id.facility_name);
        TextView facility_county_txt = (TextView) convertView.findViewById(R.id.facility_county);
        TextView facility_id_txt = (TextView) convertView.findViewById(R.id.facility_id);

        Facility facility = facilityList.get(position);

        facility_name_txt.setText(facility.get_facility_name());
        facility_county_txt.setText(facility.get_county_name());
        facility_id_txt.setText(String.valueOf(facility.get_id()));

        return convertView;
    }
}
