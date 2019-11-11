package com.example.aptiv.View.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aptiv.Model.Classe.Mode;
import com.example.aptiv.R;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Mode> {

    private Context mContext;
    private List<Mode> modeList;

    public CustomListAdapter(@NonNull Context context, ArrayList<Mode> list) {
        super(context, 0 , list);
        mContext = context;
        modeList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null)
            listView = LayoutInflater.from(mContext).inflate(R.layout.list_mode,parent,false);

        Mode currentMode = modeList.get(position);

        TextView title = (TextView) listView.findViewById(R.id.txtTitle);
        title.setText(currentMode.getTitle());

        return listView;
    }
}
