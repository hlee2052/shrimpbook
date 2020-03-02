package com.github.shrimpbook.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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
import com.github.shrimpbook.shrimp.Shrimp;

import java.util.ArrayList;
import java.util.List;


public class ShrimpAdapter extends BaseAdapter {
    private Context context;
    private List<String> name;
    private List<List<String>> reasons;
    private TextView result;
    private TextView shrimpName;

    private TextView rangePH;
    private TextView rangeGH;
    private TextView rangeKH;
    private TextView rangeTDS;
    private TextView rangeTemp;

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
            textView = rowView.findViewById(reasonId.get(i));

            if ( i >= reasonSize) {
                textView.setVisibility(View.GONE);
            } else {
                String reasonText = reasonForCurrentShrimp.get(i);
                textView.setText(reasonText);
            }
        }

        shrimpName = rowView.findViewById(R.id.CompatibilityShrimpName);
        shrimpName.setText(String.format("%s shrimp", name.get(position)));
        result = rowView.findViewById(R.id.CompatibilityResult);

        if (reasonForCurrentShrimp.size() == 0) {
            result.setText(R.string.compatible);
            result.setTextColor(Color.GREEN);
        } else {
            result.setText(R.string.not_compatible);
            result.setTextColor(Color.RED);
        }

        Shrimp current = Utility.getShrimpByName(name.get(position));

        rangePH = rowView.findViewById(R.id.rangePH);
        rangePH.setText(rangeToString(current.getPH()));

        rangeGH = rowView.findViewById(R.id.rangeGH);
        rangeGH.setText(rangeToString(current.getGH()));

        rangeKH = rowView.findViewById(R.id.rangeKH);
        rangeKH.setText(rangeToString(current.getKH()));

        rangeTDS = rowView.findViewById(R.id.rangeTDS);
        rangeTDS.setText(rangeToString(current.getTDS()));

        rangeTemp = rowView.findViewById(R.id.rangeTemp);
        rangeTemp.setText(rangeToString(current.getTEMP()));

        return rowView;
    }

    private String rangeToString (double[] range) {
        return range[0] + " - " + range[1];
    }
}