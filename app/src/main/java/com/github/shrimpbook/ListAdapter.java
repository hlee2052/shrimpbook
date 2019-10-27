package com.github.shrimpbook;

/**
 * Created by Lee on 10/25/2019.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.shrimpbook.items.ViewItem;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends BaseAdapter {



    Context context;
    private List<ViewItem> viewItems;
    private String viewTYpe;

    private boolean isAccount;
    private boolean isHome;
    private boolean isFavorites;

    // default home
    public ListAdapter(Context context, List<ViewItem> view){
        this.context = context;
        this.viewItems = view;
        this.isHome = true;
    }

    public ListAdapter(Context context, List<ViewItem> view, String type){
        this.context = context;
        this.viewItems = view;
        this.viewTYpe = type;

        if (type.equals(Utility.ACCOUNT_FRAGMENT)) {
            isAccount = true;
        }

        if (type.equals(Utility.FAVORITES_FRAGMENT)) {
            isFavorites = true;
        }

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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
            viewHolder.viewObjectId = (TextView) convertView.findViewById(R.id.itemObjectId);

            result = convertView;

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        // https://stackoverflow.com/questions/12596199/android-how-to-set-onclick-event-for-button-in-list-item-of-listview
        Button buttonDelete = (Button) convertView.findViewById(R.id.deleteItem);
        if (!isAccount) {
            buttonDelete.setVisibility(View.GONE);
        }

        Button favButton = (Button)convertView.findViewById(R.id.favoriteButton);
        if (!isHome || ParseUser.getCurrentUser()==null) {
            favButton.setVisibility(View.GONE);
        }

        if (isFavorites) {
            favButton.setVisibility(View.VISIBLE);
            favButton.setText("Remove Fav");
        }

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isFavorites) {
                    String favoriteEntryToDelete = viewItems.get(position).getViewObjectId();

                    /*ParseQuery<ParseObject> query1 = ParseQuery.getQuery("favorites");
                    query1.whereEqualTo("viewId", favoriteEntryToDelete);

                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("favorites");
                    query2.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());

                    List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
                    queries.add(query1);
                    queries.add(query2);



                    ParseQuery<ParseObject> query = ParseQuery.getQuery("favorite");
                    query = ParseQuery.or(queries);*/


                    ParseQuery<ParseObject> queryTest = ParseQuery.getQuery("favorites");
                    queryTest.whereEqualTo("viewId", favoriteEntryToDelete).whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());


                    queryTest.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            for (ParseObject each: objects) {
                                try {
                                    each.delete();
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                                each.saveInBackground();

                            }

                        FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                        Utility.moveToAnotherFragment(new FavoritesFragment(), fragmentManager);
                        Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show();


                        }
                    });









                } else {
                    ParseObject object = new ParseObject("favorites");

                    String currentUserObjectId = ParseUser.getCurrentUser().getObjectId();
                    object.put("userId", currentUserObjectId);

                    String viewItemId = viewItems.get(position).getViewObjectId();
                    object.put("viewId", viewItemId);

                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null) {
                                Toast.makeText(context, "Added to your favorite list", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });




        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // Now that we have id to delete available, delete it from Parse server

                String objectIdToDelete = viewItems.get(position).getViewObjectId();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("entries");

                // may want to check if object to delete also belongs to current user for extra security
                query.getInBackground(objectIdToDelete, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        try {
                            object.delete();
                            object.saveInBackground();



                            FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                            Utility.moveToAnotherFragment(new AccountFragment(), fragmentManager);
                            Toast.makeText(context, "The item has been deleted", Toast.LENGTH_SHORT).show();


                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                Log.i("tagggg", objectIdToDelete);
            }

        });




        viewHolder.shrimpTypeTextView.setText(viewItems.get(position).getShrimpType());
        viewHolder.soilTypeTextView.setText(viewItems.get(position).getSoilType());
        viewHolder.tankSizeTextView.setText(viewItems.get(position).getTankSize());
        viewHolder.GHTextView.setText(viewItems.get(position).getGH());
        viewHolder.KHTextView.setText(viewItems.get(position).getKH());
        viewHolder.pHTextView.setText(viewItems.get(position).getpH());
        viewHolder.viewObjectId.setText((viewItems.get(position).getViewObjectId()));

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
        } else {
            viewHolder.shrimpImageView.setImageResource(R.drawable.ic_desktop_windows_black_24dp);
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

        TextView viewObjectId;

    }
}