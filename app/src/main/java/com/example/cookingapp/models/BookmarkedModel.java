package com.example.cookingapp.models;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class BookmarkedModel extends SugarRecord<BookmarkedModel> {

    int identificationNum;
    String img;
    String name;

    @Ignore
    String password;

    public BookmarkedModel(){}

    public BookmarkedModel(int identificationNum, String img, String name){
        this.identificationNum = identificationNum;
        this.img = img;
        this.name = name;
    }

    public int getIdentificationNum() {
        return identificationNum;
    }

    public void setIdentificationNum(int identificationNum) {
        this.identificationNum = identificationNum;
    }

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
}
