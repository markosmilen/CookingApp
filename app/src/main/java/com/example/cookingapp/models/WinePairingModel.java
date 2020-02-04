package com.example.cookingapp.models;

import java.util.ArrayList;

public class WinePairingModel {

    ArrayList<String> pairedWines;
    String pairingText;
    ArrayList<PairedWineModel> productMatches;

    WinePairingModel(){}

    public ArrayList<PairedWineModel> getProductMatches() {
        return productMatches;
    }

    public void setProductMatches(ArrayList<PairedWineModel> productMatches) {
        this.productMatches = productMatches;
    }

    public ArrayList<String> getPairedWines() {
        return pairedWines;
    }

    public void setPairedWines(ArrayList<String> pairedWines) {
        this.pairedWines = pairedWines;
    }

    public String getPairingText() {
        return pairingText;
    }

    public void setPairingText(String pairingText) {
        this.pairingText = pairingText;
    }

}
