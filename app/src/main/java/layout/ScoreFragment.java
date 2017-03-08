package layout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.stamatis.questiongameapp.AboutDialog;
import com.example.stamatis.questiongameapp.R;
import com.example.stamatis.questiongameapp.util.DataUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stamatis Stiliatis Togrou(ExXoDuSs) on 6/3/2017.
 */

public class ScoreFragment extends ListFragment {

    private List<Integer> scores;
    private ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateDate();
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.score_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DataUtil.deleteAllScores(getActivity());
        updateDate();
        return true;
    }

    public void updateDate(){
        scores = new ArrayList<>();
        scores = DataUtil.getScore(getActivity());
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, scores);
        setListAdapter(adapter);
    }
}
