package com.example.aptiv.View.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aptiv.Model.Classe.Mode;
import com.example.aptiv.R;
import com.example.aptiv.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Mode> {

    private Context mContext;
    private List<Mode> modeList;
    MainActivity _owner;
    View _view;
    private int selectedPosition;

    public CustomListAdapter(@NonNull Context context, ArrayList<Mode> list, MainActivity Owner, View View) {
        super(context, 0, list);
        mContext = context;
        modeList = list;
        _owner = Owner;
        _view = View;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;

        if (listView == null)
            listView = LayoutInflater.from(mContext).inflate(R.layout.list_mode, parent, false);

        final Mode currentMode = modeList.get(position);
        TextView title = (TextView) listView.findViewById(R.id.txtTitle);
        title.setText(currentMode.getTitle());

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _owner.OpenModeFragment(_view, currentMode);
            }
        });

        loadData();
        RadioButton radioButton = (RadioButton) listView.findViewById(R.id.radiobutton);
        radioButton.setChecked(position == selectedPosition);
        radioButton.setTag(position);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
                saveData();
            }
        });

        return listView;
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("selected_pos", selectedPosition);
        editor.commit();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
        selectedPosition = sharedPreferences.getInt("selected_pos", 0);
    }
}