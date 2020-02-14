package com.example.cookingapp.models;

import java.util.ArrayList;

public class EquipmentByIdModel {

    ArrayList<EquipmentModel> equipment;

    EquipmentByIdModel(){}

    public ArrayList<EquipmentModel> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<EquipmentModel> equipment) {
        this.equipment = equipment;
    }
}
