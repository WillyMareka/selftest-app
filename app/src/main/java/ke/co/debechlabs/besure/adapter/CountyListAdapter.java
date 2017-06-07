package ke.co.debechlabs.besure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.models.County;

/**
 * Created by chriz on 6/7/2017.
 */

public class CountyListAdapter extends BaseAdapter {
    Context context;
    List<County> countyList;
    LayoutInflater inflater;

    public CountyListAdapter(Context ctx, List<County> counties){
        context = ctx;
        countyList = counties;
    }

    @Override
    public int getCount() {
        return countyList.size();
    }

    @Override
    public Object getItem(int position) {
        return countyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.county_list_item, null);

        TextView txtCountyID = (TextView) convertView.findViewById(R.id.county_id);
        TextView txtCountyName = (TextView) convertView.findViewById(R.id.county_name);

        County county = countyList.get(position);
        txtCountyID.setText(String.valueOf(county.get_id()));
        txtCountyName.setText(String.valueOf(county.getCounty_name()));

        return convertView;
    }
}
