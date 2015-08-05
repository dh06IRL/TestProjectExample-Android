package com.david.github.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.david.github.R;
import com.github.florent37.glidepalette.GlidePalette;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by davidhodge on 7/23/15.
 */
public class DataViewerActivity extends BaseActivity {

    Context mContext;
    ActionBar actionBar;

    String title, merchant, desc, imageUrl;
    Double price;

    @Bind(R.id.viewer_image)
    ImageView viewerImage;
    @Bind(R.id.viewer_price)
    TextView viewerPrice;
    @Bind(R.id.viewer_title)
    TextView viewerTitle;
    @Bind(R.id.viewer_merchant_name)
    TextView viewerMerchantName;
    @Bind(R.id.viewer_desc)
    TextView viewerDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_viewer);
        ButterKnife.bind(this);

        mContext = this;
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title = extras.getString("title");
            merchant = extras.getString("merchant");
            desc = extras.getString("desc");
            imageUrl = extras.getString("imageUrl");

            price = extras.getDouble("price");

            viewerPrice.setText(String.format("$%.2f", price));
            viewerTitle.setText(title);
            viewerMerchantName.setText(merchant);
            viewerDesc.setText(desc);

            Glide.with(mContext)
                    .load(imageUrl)
                    .listener(GlidePalette.with(imageUrl)
                            .use(GlidePalette.Profile.VIBRANT)
                            .intoBackground(viewerPrice, GlidePalette.Swatch.RGB)
                            .intoTextColor(viewerPrice, GlidePalette.Swatch.BODY_TEXT_COLOR)
                            .use(GlidePalette.Profile.MUTED)
                            .intoBackground(viewerImage, GlidePalette.Swatch.RGB)
                            .intoCallBack(new GlidePalette.CallBack() {
                                @Override
                                public void onPaletteLoaded(Palette palette) {
                                    //maybe?
                                }
                            }))
                    .crossFade()
                    .into(viewerImage);
        }else{
            viewerImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_sad_face));
            SnackbarManager.show(
                    Snackbar.with(mContext)
                            .type(SnackbarType.MULTI_LINE)
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .actionColor(Color.WHITE)
                            .text(getString(R.string.viewer_error))
                            .actionLabel(getString(R.string.viewer_okay))
                            .actionListener(new ActionClickListener() {
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    finish();
                                }
                            })
                            .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                            .animation(true));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
