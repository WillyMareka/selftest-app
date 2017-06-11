package ke.co.debechlabs.besure.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.app.AppController;
import ke.co.debechlabs.besure.models.Audios;

/**
 * Created by chriz on 6/9/2017.
 */

public class AudioListAdapter extends BaseAdapter {
    List<Audios> audiosList;
    LayoutInflater inflater;
    Context context;
    ImageLoader imageLoader;
    public AudioListAdapter(Context ctx, List<Audios> audios){
        this.context = ctx;
        this.audiosList = audios;
        imageLoader = AppController.getInstance().getImageLoader();
    }
    @Override
    public int getCount() {
        return audiosList.size();
    }

    @Override
    public Object getItem(int position) {
        return audiosList.get(position);
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
            convertView = inflater.inflate(R.layout.audio_list_row, null);

        Audios audio = audiosList.get(position);

        TextView txtAudioTitle = (TextView) convertView.findViewById(R.id.audio_title);
        TextView txtAudioDate = (TextView) convertView.findViewById(R.id.audio_date);
        TextView txtAudioURL = (TextView) convertView.findViewById(R.id.audio_url);
        TextView txtAudioStation = (TextView) convertView.findViewById(R.id.audio_station);
        NetworkImageView imgStationUrl = (NetworkImageView) convertView.findViewById(R.id.list_image);

        txtAudioDate.setText(audio.getAudio_date());
        txtAudioTitle.setText(audio.getAudio_title());
        txtAudioURL.setText(audio.getAudio_link());
        txtAudioStation.setText(audio.getAudio_source());
        imgStationUrl.setImageUrl(audio.getAudio_station_url(), imageLoader);
        return convertView;
    }
}
