package layout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stamatis.questiongameapp.AboutDialog;
import com.example.stamatis.questiongameapp.GameActivity;
import com.example.stamatis.questiongameapp.R;

/**
 * Created by Stamatis Stiliatis Togrou(ExXoDuSs) on 5/3/2017.
 */

public class MainFragment extends Fragment {

    private String playerName;
    private Button btnPlay;
    private EditText editTextName;
    private TextView txtNameHeader;
    private Typeface font;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        /*
        * Triggered when user types or makes any changes on the
        * edit text. Checks if edit text is empty and disables
        * button
        * */
        editTextName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // you can call or do what you want with your EditText here
                if (editTextName.getText().toString().equals("")){
                    btnPlay.setEnabled(false);
                }else{
                    btnPlay.setEnabled(true);
                }
            }
            // Necessary implementations for the TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_scores:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScoreFragment fragment = new ScoreFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;
            case R.id.menu_item_about:
                AboutDialog dialog = new AboutDialog(getActivity());
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews(){
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bear_hugs.ttf");
        editTextName = (EditText) getView().findViewById(R.id.edit_text_name);
        editTextName.setTypeface(font);
        txtNameHeader = (TextView) getView().findViewById(R.id.text_header_name);
        txtNameHeader.setTypeface(font);
        btnPlay = (Button) getView().findViewById(R.id.btn_play);
        btnPlay.setTypeface(font);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            /*
            * Intent that carries the name of the player
            * on the new activity
            *
            * PLAYER_NAME variable to access player
            * name on the new activity
            * */
            @Override
            public void onClick(View view) {
                getPlayerName();
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra("PLAYER_NAME", playerName);
                startActivity(intent);
            }
        });
        btnPlay.setEnabled(false);
    }

    private void getPlayerName(){
        playerName = editTextName.getText().toString();
    }
}
