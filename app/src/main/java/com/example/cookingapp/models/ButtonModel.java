package com.example.cookingapp.models;

import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ButtonModel {
    String button;
    List<ButtonModel> buttons1, buttons2, buttons3;

    public ButtonModel(String name){
        this.button = name;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public static List<ButtonModel> prepareButtons(String[] names) {
        List<ButtonModel> buttons = new ArrayList<>(names.length);

        for (int i = 0; i < names.length; i++) {
            ButtonModel button = new ButtonModel (names[i]);
            Log.d("POSITION", i +"");
            buttons.add(button);
        }
        return buttons;
    }
}
