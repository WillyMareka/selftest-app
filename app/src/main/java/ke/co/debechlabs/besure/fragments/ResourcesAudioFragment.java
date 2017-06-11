package ke.co.debechlabs.besure.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.ResourcesActivity;
import ke.co.debechlabs.besure.adapter.AudioListAdapter;
import ke.co.debechlabs.besure.app.AppController;
import ke.co.debechlabs.besure.app.Config;
import ke.co.debechlabs.besure.models.Audios;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResourcesAudioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResourcesAudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResourcesAudioFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<Audios> audiosList = new ArrayList<Audios>();
    AudioListAdapter audioListAdapter;
    ListView audioListView;
    SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ResourcesAudioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResourcesAudioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResourcesAudioFragment newInstance(String param1, String param2) {
        ResourcesAudioFragment fragment = new ResourcesAudioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_resources_audio, container, false);
        audioListView = (ListView) rootView.findViewById(R.id.audioList);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        audioListAdapter = new AudioListAdapter(getActivity(), audiosList);
        audioListView.setAdapter(audioListAdapter);

        audioListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView audio_url_txt = (TextView) view.findViewById(R.id.audio_url);
                TextView audio_title_txt = (TextView) view.findViewById(R.id.audio_title);
                TextView audio_source_txt = (TextView) view.findViewById(R.id.audio_station);

                String audio_title = audio_title_txt.getText().toString();
                String audio_source = audio_source_txt.getText().toString();
                String audio_url = Config.ASSETS_URL_LOCAL + audio_url_txt.getText().toString();

                android.app.FragmentManager manager = getActivity().getFragmentManager();
                android.app.Fragment frag = manager.findFragmentByTag("fragment_audio_player_fragment");

                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                AudioPlayerFragment.newInstance(audio_title, audio_source, audio_url).show(manager, "fragment_audio_player_fragment");
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getAudios();
            }
        });
        return rootView;
    }

    private void getAudios() {
        swipeRefreshLayout.setRefreshing(true);
        String url = Config.BASE_URL_LOCAL + "getResources/audio";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                audiosList.clear();
                try {
                    if (response.getBoolean("status") == true){
                        JSONArray audiosArray = response.getJSONArray("data");
                        for (int i = 0; i < audiosArray.length(); i++) {
                            Audios audio = new Audios();

                            JSONObject audioJSONObject = audiosArray.getJSONObject(i);
                            audio.setAudio_date(audioJSONObject.getString("date"));
                            audio.setAudio_title(audioJSONObject.getString("title"));
                            audio.setAudio_link(audioJSONObject.getString("link"));
                            audio.setAudio_source(audioJSONObject.getString("source"));
                            audio.setAudio_station_url(audioJSONObject.getString("station_url"));

                            audiosList.add(audio);
                        }
                    }else{
                        Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                audioListAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        getAudios();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
