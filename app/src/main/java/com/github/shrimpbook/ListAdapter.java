package com.github.shrimpbook;

/**
 * Created by Lee on 10/25/2019.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.shrimpbook.items.ViewItem;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.List;


public class ListAdapter extends BaseAdapter {



    Context context;
    private List<ViewItem> viewItems;


    public ListAdapter(Context context, List<ViewItem> view){
        this.context = context;
        this.viewItems = view;
    }


    @Override
    public int getCount() {
        return viewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    // Tutorial from : http://www.sanktips.com/2017/10/12/android-custom-listview-with-image-and-text-example/
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        final View result;

        // for reuse old view
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_my_item, parent, false);

            viewHolder.shrimpTypeTextView = (TextView) convertView.findViewById(R.id.shrimpTypeHome);
            viewHolder.soilTypeTextView = (TextView) convertView.findViewById(R.id.soilTypeHome);
            viewHolder.tankSizeTextView = (TextView) convertView.findViewById(R.id.tankSizeHome);
            viewHolder.pHTextView = (TextView) convertView.findViewById(R.id.pHHome);
            viewHolder.GHTextView = (TextView) convertView.findViewById(R.id.GHHome);
            viewHolder.KHTextView = (TextView) convertView.findViewById(R.id.KHHome);
            viewHolder.shrimpImageView = (ImageView) convertView.findViewById(R.id.singleItemImage);

            result = convertView;

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.shrimpTypeTextView.setText(viewItems.get(position).getShrimpType());
        viewHolder.soilTypeTextView.setText(viewItems.get(position).getSoilType());
        viewHolder.tankSizeTextView.setText(viewItems.get(position).getTankSize());
        viewHolder.GHTextView.setText(viewItems.get(position).getGH());
        viewHolder.KHTextView.setText(viewItems.get(position).getKH());
        viewHolder.pHTextView.setText(viewItems.get(position).getpH());

        // For getting image
        ParseFile file = viewItems.get(position).getFile();
        if (file !=null) {
            file.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e==null && data !=null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        viewHolder.shrimpImageView.setImageBitmap(bitmap);
                    }
                }
            });
        }

        return convertView;
    }

    private static class ViewHolder {

        TextView shrimpTypeTextView;
        TextView soilTypeTextView;
        TextView tankSizeTextView;
        TextView pHTextView;
        TextView GHTextView;
        TextView KHTextView;

        ImageView shrimpImageView;

    }
}