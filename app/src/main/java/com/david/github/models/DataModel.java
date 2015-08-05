package com.david.github.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by davidhodge on 7/23/15.
 */
public class DataModel {

    @Expose
    public String description;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("merchant_name")
    @Expose
    public String merchantName;
    @Expose
    public Double price;
    @Expose
    public String title;

}
