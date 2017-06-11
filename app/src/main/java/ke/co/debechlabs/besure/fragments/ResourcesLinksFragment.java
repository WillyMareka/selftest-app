package ke.co.debechlabs.besure.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import ke.co.debechlabs.besure.WebClient;
import ke.co.debechlabs.besure.adapter.SitesListAdapter;
import ke.co.debechlabs.besure.app.AppController;
import ke.co.debechlabs.besure.app.Config;
import ke.co.debechlabs.besure.models.Sites;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResourcesLinksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResourcesLinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResourcesLinksFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    
    List<Sites> sitesList = new ArrayList<Sites>();
    ListView linkListView;
    SitesListAdapter sitesListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public ResourcesLinksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResourcesLinksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResourcesLinksFragment newInstance(String param1, String param2) {
        ResourcesLinksFragment fragment = new ResourcesLinksFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_resources_links, container, false);
        linkListView = (ListView) rootView.findViewById(R.id.links_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        
        sitesListAdapter = new SitesListAdapter(getActivity(), sitesList);
        linkListView.setAdapter(sitesListAdapter);
        
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getSites();
            }
        });

        linkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtSiteURL = (TextView) view.findViewById(R.id.site_link);
                String url = txtSiteURL.getText().toString();

                Intent intent = new Intent(getActivity(), WebClient.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        return rootView;
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
        getSites();
    }

    private void getSites() {
        String url = Config.BASE_URL_LOCAL + "getResources/sites";
        swipeRefreshLayout.setRefreshing(true);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sitesList.clear();
                try {
                    if (response.getBoolean("status") == true){
                        JSONArray sitesArray = response.getJSONArray("data");
                        for (int i = 0; i < sitesArray.length(); i++) {
                            Sites site = new Sites();
                            JSONObject siteObj = sitesArray.getJSONObject(i);

                            site.setLink(siteObj.getString("link"));
                            site.setThumb(siteObj.getString("thumb"));
                            site.setTitle(siteObj.getString("title"));

                            sitesList.add(site);
                        }
                    }

                    sitesListAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
