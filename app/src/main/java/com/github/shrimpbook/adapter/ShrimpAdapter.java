package com.github.shrimpbook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.shrimpbook.R;
import com.github.shrimpbook.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 11/23/2019.
 */

public class ShrimpAdapter extends BaseAdapter {


    Context context;
    List<String> name;
    List<List<String>> reasons;

    public ShrimpAdapter(Context context, List<String> name, List<List<String>> reasons) {
        this.context = context;
        this.name = name;
        this.reasons = reasons;
    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.single_list_shrimp, parent, false);

        int imageID = Utility.getShrimpImageByName(name.get(position));
        ImageView imageView = (ImageView) rowView.findViewById(R.id.compatibilityImage);
        imageView.setImageResource(imageID);


        List<Integer> reasonId = new ArrayList<>();
        reasonId.add(R.id.reason0);
        reasonId.add(R.id.reason1);
        reasonId.add(R.id.reason2);
        reasonId.add(R.id.reason3);
        reasonId.add(R.id.reason4);

        List<String> reasonForCurrentShrimp = reasons.get(position);
        int reasonSize = reasonForCurrentShrimp.size();

        TextView textView;
        for (int i=0; i<reasonId.size(); i++) {
            textView = (TextView)  rowView.findViewById(reasonId.get(i));

            if ( i >= reasonSize) {
                textView.setVisibility(View.GONE);
            } else {
                String reasonText = reasonForCurrentShrimp.get(i);
                textView.setText(reasonText);
            }
        }

        return rowView;
    }

    private static class ViewHolder {
        TextView tmp;

    }
}
