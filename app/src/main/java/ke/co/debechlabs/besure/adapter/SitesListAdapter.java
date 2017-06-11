package ke.co.debechlabs.besure.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.app.AppController;
import ke.co.debechlabs.besure.app.Config;
import ke.co.debechlabs.besure.models.Sites;

/**
 * Created by chriz on 6/9/2017.
 */

public class SitesListAdapter extends BaseAdapter {
    Context context;
    List<Sites> sitesList;
    LayoutInflater inflater;
    ImageLoader loader;

    public SitesListAdapter(Context context, List<Sites> sites){
        this.sitesList = sites;
        this.context = context;
        loader = AppController.getInstance().getImageLoader();
    }
    @Override
    public int getCount() {
        return this.sitesList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.sitesList.get(position);
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
            convertView = inflater.inflate(R.layout.link_resource_list_item, null);

        Sites site = sitesList.get(position);

        TextView txtSiteTitle = (TextView) convertView.findViewById(R.id.site_title);
        TextView txtSiteLink= (TextView) convertView.findViewById(R.id.site_link);

        txtSiteTitle.setText(site.getTitle());
        txtSiteLink.setText(site.getLink());
        return convertView;
    }
}
