package ke.co.debechlabs.besure.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.util.TimeUtils;

/**
 * Created by chriz on 6/9/2017.
 */

public class AudioPlayerFragment extends DialogFragment implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    private String title, station, url;
    MediaPlayer mediaPlayer;
    ImageView imgPlay, imgPause;
    TextView txtCurrentTime, txtAudioDuration;
    SeekBar audioSeekBar;
    ProgressDialog pdialog;
    int audio_length = 0;
    public AudioPlayerFragment(){}

    int pausedAt = -1;
    private Handler mHandler = new Handler();
    private TimeUtils timeUtils;

    public static AudioPlayerFragment newInstance(String title, String station, String url) {

        Bundle args = new Bundle();

        AudioPlayerFragment fragment = new AudioPlayerFragment();
        args.putString("title", title);
        args.putString("station", station);
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.title = getArguments().getString("title");
        this.station = getArguments().getString("station");
        this.url = getArguments().getString("url");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        View view = null;
        try {
            view = factory.inflate(R.layout.fragment_audio_player, null);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnCompletionListener(this);
        timeUtils = new TimeUtils();
        pdialog = new ProgressDialog(getActivity());
        TextView title_txt = (TextView) view.findViewById(R.id.audio_title);
        TextView station_txt = (TextView) view.findViewById(R.id.audio_source);
        imgPlay = (ImageView) view.findViewById(R.id.control);
        imgPause = (ImageView) view.findViewById(R.id.control_pause);
        audioSeekBar = (SeekBar) view.findViewById(R.id.audio_seek_bar);
        txtCurrentTime = (TextView) view.findViewById(R.id.currentTime);
        txtAudioDuration = (TextView) view.findViewById(R.id.audioTime);

        imgPlay.setOnClickListener(this);
        imgPause.setOnClickListener(this);
        audioSeekBar.setOnSeekBarChangeListener(this);
        title_txt.setText(this.title);
        station_txt.setText(this.station);
        txtAudioDuration.setText("0:00");
        txtCurrentTime.setText("0:00");
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return new AlertDialog.Builder(getActivity())
                .setView(view).create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    @Override
    public void onPause() {
        super.onPause();
        pausedAt = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.control:
                if (pausedAt != -1){
                    mediaPlayer.seekTo(pausedAt);
                    mediaPlayer.start();
                }else{
                    pdialog.setMessage("Buffering");
                    pdialog.show();
                }
                imgPlay.setVisibility(View.INVISIBLE);
                imgPause.setVisibility(View.VISIBLE);
                updateProgressBar();
                break;
            case R.id.control_pause:
                if (mediaPlayer.isPlaying()){
                    try{
                        pausedAt = mediaPlayer.getCurrentPosition();
                        mediaPlayer.pause();
                    }catch (Exception ex){
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                imgPlay.setVisibility(View.VISIBLE);
                imgPause.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentPosition = mediaPlayer.getCurrentPosition();

            txtAudioDuration.setText(""+timeUtils.milliSecondsToTimer(totalDuration));
            txtCurrentTime.setText(""+timeUtils.milliSecondsToTimer(currentPosition));

            int progress = (int)(timeUtils.getProgressPercentage(currentPosition, totalDuration));

            audioSeekBar.setProgress(progress);

            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onPrepared(MediaPlayer mp) {
        audio_length = mediaPlayer.getDuration();
        audioSeekBar.setProgress(0);
        audioSeekBar.setMax(100);
        imgPlay.setVisibility(View.INVISIBLE);
        imgPause.setVisibility(View.VISIBLE);
        mediaPlayer.start();
        pdialog.hide();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        imgPause.setVisibility(View.INVISIBLE);
        imgPlay.setVisibility(View.VISIBLE);
        pausedAt = 0;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);

        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = timeUtils.progressToTimer(audioSeekBar.getProgress(), totalDuration);
        mediaPlayer.seekTo(currentPosition);

        updateProgressBar();

    }
}
