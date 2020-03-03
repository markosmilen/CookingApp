package com.example.cookingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientsAndValueModel implements Parcelable {

    String img;
    String name;
    IngredientsAmountModel amount;

    IngredientsAndValueModel(){}

    protected IngredientsAndValueModel(Parcel in) {
        img = in.readString();
        name = in.readString();
    }

    public static final Creator<IngredientsAndValueModel> CREATOR = new Creator<IngredientsAndValueModel>() {
        @Override
        public IngredientsAndValueModel createFromParcel(Parcel in) {
            return new IngredientsAndValueModel(in);
        }

        @Override
        public IngredientsAndValueModel[] newArray(int size) {
            return new IngredientsAndValueModel[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientsAmountModel getAmount() {
        return amount;
    }

    public void setAmount(IngredientsAmountModel amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(name);
    }
}
