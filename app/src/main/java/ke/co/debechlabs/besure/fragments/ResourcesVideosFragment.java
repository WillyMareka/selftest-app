package ke.co.debechlabs.besure.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.ResourcesActivity;
import ke.co.debechlabs.besure.adapter.VideoRecyclerAdapter;
import ke.co.debechlabs.besure.app.Config;
import ke.co.debechlabs.besure.helper.DividerItemDecoration;
import ke.co.debechlabs.besure.models.Videos;
import ke.co.debechlabs.besure.network.ApiClient;
import ke.co.debechlabs.besure.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResourcesVideosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResourcesVideosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResourcesVideosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, VideoRecyclerAdapter.VideosAdapterListener{
    private List<Videos> videosList = new ArrayList<Videos>();
    private RecyclerView recyclerView;
    private VideoRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Config config;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ResourcesVideosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResourcesVideosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResourcesVideosFragment newInstance(String param1, String param2) {
        ResourcesVideosFragment fragment = new ResourcesVideosFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_resources_videos, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.mainRecyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        config = new Config(getActivity());

        adapter = new VideoRecyclerAdapter(getActivity(), this.videosList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        actionModeCallback = new ActionModeCallback();

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getVideos();
                    }
                }
        );
        return rootView;
    }

    private void getVideos() {
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Videos>> call = apiService.getVideos();
        call.enqueue(new Callback<List<Videos>>() {
            @Override
            public void onResponse(Call<List<Videos>> call, Response<List<Videos>> response) {
                videosList.clear();
//                Log.d("Video Response List", response.toString());
                for (Videos video : response.body()){
                    video.set_color(config.getRandomMaterialColor("400"));

                    videosList.add(video);
                }

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Videos>> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

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
        getVideos();
    }

    @Override
    public void onMessageRowClicked(int position) {

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

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}
