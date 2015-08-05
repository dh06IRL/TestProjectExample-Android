package com.david.github.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.david.github.activity.DataViewerActivity;
import com.david.github.activity.MainActivity;
import com.david.github.models.DataModel;
import com.github.florent37.glidepalette.GlidePalette;
import com.livingsocial.livingsocialtest.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by davidhodge on 7/23/15.
 */
public class DataAdapter extends BaseAdapter {

    private ArrayList<DataModel> items;
    private ArrayList<DataModel> arraylist;
    private LayoutInflater inflater;
    private Context mContext;
    private MainActivity mainActivity;

    public DataAdapter(Context mContext, ArrayList<DataModel> items, MainActivity mainActivity) {
        this.mContext = mContext;
        this.items = items;
        inflater = LayoutInflater.from(this.mContext);
        this.arraylist = new ArrayList<DataModel>();
        this.arraylist.addAll(items);
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_data_view, parent, false);
            holder.dataTitle = (TextView) convertView.findViewById(R.id.data_title);
            holder.dataMerchant = (TextView) convertView.findViewById(R.id.data_merchant_name);
            holder.dataPrice = (TextView) convertView.findViewById(R.id.data_price);
            holder.dataDesc = (TextView) convertView.findViewById(R.id.data_desc);
            holder.dataImage = (ImageView) convertView.findViewById(R.id.data_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext)
                .load(items.get(position).imageUrl)
                .centerCrop()
                .listener(GlidePalette.with(items.get(position).imageUrl)
                        .use(GlidePalette.Profile.VIBRANT)
                        .intoBackground(holder.dataPrice, GlidePalette.Swatch.RGB)
                        .intoTextColor(holder.dataPrice, GlidePalette.Swatch.BODY_TEXT_COLOR)
                        .use(GlidePalette.Profile.MUTED)
                        .intoBackground(holder.dataImage)
                        .intoCallBack(new GlidePalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(Palette palette) {
                                //maybe?
                            }
                        }))
                .crossFade()
                .into(holder.dataImage);

        holder.dataTitle.setText(items.get(position).title);
        holder.dataMerchant.setText(items.get(position).merchantName);
        holder.dataDesc.setText(items.get(position).description);
        holder.dataPrice.setText(String.format("$%.2f", items.get(position).price));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DataViewerActivity.class);
                intent.putExtra("title", items.get(position).title);
                intent.putExtra("price", items.get(position).price);
                intent.putExtra("desc", items.get(position).description);
                intent.putExtra("merchant", items.get(position).merchantName);
                intent.putExtra("imageUrl", items.get(position).imageUrl);

                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(mainActivity,
                                new Pair<View, String>(holder.dataTitle,
                                        mContext.getString(R.string.transition_title)),
                                new Pair<View, String>(holder.dataPrice,
                                        mContext.getString(R.string.transition_price)),
                                new Pair<View, String>(holder.dataMerchant,
                                        mContext.getString(R.string.transition_merchant)),
                                new Pair<View, String>(holder.dataDesc,
                                        mContext.getString(R.string.transition_desc))
                        );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mContext.startActivity(intent, options.toBundle());
                }else{
                    mContext.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView dataTitle;
        TextView dataMerchant;
        TextView dataPrice;
        TextView dataDesc;
        ImageView dataImage;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        } else {
            for (DataModel model : arraylist) {
                if (model.title.toLowerCase(Locale.getDefault()).contains(charText) || model.description.toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}
