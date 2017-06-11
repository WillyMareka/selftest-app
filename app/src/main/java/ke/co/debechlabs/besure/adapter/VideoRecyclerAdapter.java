package ke.co.debechlabs.besure.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.WebClient;
import ke.co.debechlabs.besure.app.Config;
import ke.co.debechlabs.besure.models.Videos;

/**
 * Created by chriz on 6/9/2017.
 */

public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<Videos> videosList;
    VideosAdapterListener listener;
    private SparseBooleanArray selectedItems;

    private static int currentSelectedIndex = -1;

    public VideoRecyclerAdapter(Context mContext, List<Videos> videos, VideosAdapterListener listener) {
        this.mContext = mContext;
        this.videosList = videos;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Videos video = this.videosList.get(position);

        holder.txtVideoTitle.setText(video.get_video_title());
        holder.iconText.setText("V");
        holder.txtVideoID.setText(video.get_video_id());

        applyThumbnail(holder, video);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView videoID = (TextView) v.findViewById(R.id.video_id);
                String video_id = videoID.getText().toString();
                Intent intent = new Intent(mContext, WebClient.class);
                intent.putExtra("url", Config.getYoutubeVideoURL(video_id));
                mContext.startActivity(intent);
            }
        });
    }

    private void applyThumbnail(MyViewHolder holder, Videos video) {
        if (!TextUtils.isEmpty(video.get_video_id())){
            String video_id = video.get_video_id();
            Glide.with(mContext).load(Config.getYoutubeThumb(video_id))
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.imgVideoThumb);
            holder.imgVideoThumb.setColorFilter(null);
            holder.iconText.setVisibility(View.GONE);
        }else{
            holder.imgVideoThumb.setColorFilter(video.get_color());
            holder.iconText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.videosList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtVideoTitle, txtVideoDescription, iconText, txtVideoID;
        public ImageView imgVideoThumb;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtVideoTitle = (TextView) itemView.findViewById(R.id.video_title);
            txtVideoDescription = (TextView) itemView.findViewById(R.id.video_description);
            imgVideoThumb = (ImageView) itemView.findViewById(R.id.video_thumb);
            iconText = (TextView) itemView.findViewById(R.id.icon_text);
            txtVideoID = (TextView) itemView.findViewById(R.id.video_id);
        }
    }

    public interface VideosAdapterListener {

        void onMessageRowClicked(int position);
    }
}
