package com.github.shrimpbook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.shrimpbook.AccountFragment;
import com.github.shrimpbook.favorites.FavoritesFragment;
import com.github.shrimpbook.MainActivity;
import com.github.shrimpbook.R;
import com.github.shrimpbook.Utility;
import com.github.shrimpbook.items.ViewItem;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<ViewItem> viewItems;
    private String viewTYpe;
    private boolean isAccount;
    private boolean isHome;
    private boolean isFavorites;

    // default home
    public ListAdapter(Context context, List<ViewItem> view) {
        this.context = context;
        this.viewItems = view;
        this.isHome = true;
    }

    public ListAdapter(Context context, List<ViewItem> view, String type) {
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
            viewHolder.tempTextView = (TextView) convertView.findViewById(R.id.tempHome);
            viewHolder.TDSTextView = (TextView) convertView.findViewById(R.id.TDSHome);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        // https://stackoverflow.com/questions/12596199/android-how-to-set-onclick-event-for-button-in-list-item-of-listview
        Button buttonDelete = (Button) convertView.findViewById(R.id.deleteItem);
        if (!isAccount) {
            buttonDelete.setVisibility(View.GONE);
        }

        Button favButton = (Button) convertView.findViewById(R.id.favoriteButton);
        if (!isHome || ParseUser.getCurrentUser() == null || ParseUser.getCurrentUser().getUsername() == null) {
            favButton.setVisibility(View.GONE);
        }

        if (isFavorites) {
            favButton.setVisibility(View.VISIBLE);
            favButton.setText(R.string.remove);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorites) {
                    String favoriteEntryToDelete = viewItems.get(position).getViewObjectId();
                    ParseQuery<ParseObject> queryTest = ParseQuery.getQuery(Utility.DB_FAVORITES);
                    queryTest.whereEqualTo(Utility.DB_VIEW_ID, favoriteEntryToDelete).whereEqualTo(Utility.DB_USER_ID, ParseUser.getCurrentUser().getObjectId());

                    queryTest.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> parseObjects, ParseException e) {
                            for (ParseObject entry : parseObjects) {
                                try {
                                    entry.delete();
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                                entry.saveInBackground();
                            }
                            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                            Utility.moveToAnotherFragment(new FavoritesFragment(), fragmentManager);
                            Toast.makeText(context, R.string.favorite_removed, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ParseObject parseObject = new ParseObject(Utility.DB_FAVORITES);

                    String currentUserObjectId = ParseUser.getCurrentUser().getObjectId();
                    parseObject.put(Utility.DB_USER_ID, currentUserObjectId);

                    String viewItemId = viewItems.get(position).getViewObjectId();
                    parseObject.put(Utility.DB_VIEW_ID, viewItemId);

                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(context, R.string.favorite_added, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, R.string.favorite_add_failed, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String objectIdToDelete = viewItems.get(position).getViewObjectId();
                ParseQuery<ParseObject> query = ParseQuery.getQuery(Utility.DB_ENTRIES);

                query.getInBackground(objectIdToDelete, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        try {
                            object.delete();
                            object.saveInBackground();
                            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                            Utility.moveToAnotherFragment(new AccountFragment(), fragmentManager);
                            Toast.makeText(context, R.string.delete_item, Toast.LENGTH_SHORT).show();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }

        });

        viewHolder.shrimpTypeTextView.setText(viewItems.get(position).getShrimpType());
        viewHolder.soilTypeTextView.setText(viewItems.get(position).getSoilType());
        viewHolder.tankSizeTextView.setText(viewItems.get(position).getTankSize());
        viewHolder.GHTextView.setText(viewItems.get(position).getGH());
        viewHolder.KHTextView.setText(viewItems.get(position).getKH());
        viewHolder.pHTextView.setText(viewItems.get(position).getpH());
        viewHolder.viewObjectId.setText((viewItems.get(position).getViewObjectId()));

        viewHolder.TDSTextView.setText((viewItems.get(position).getTDS()));
        viewHolder.tempTextView.setText((viewItems.get(position).getTemp()));

        // For getting image
        ParseFile file = viewItems.get(position).getFile();
        if (file != null) {
            file.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null && data != null) {
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
        TextView TDSTextView;
        TextView tempTextView;
    }
}