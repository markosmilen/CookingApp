package com.example.cookingapp.models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class CookedModel extends SugarRecord<CookedModel> {

    int identificationNum;
    String img;
    String name;

    @Ignore
    String password;

    public CookedModel(){}


    public CookedModel(int identificationNum, String img, String name){
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
