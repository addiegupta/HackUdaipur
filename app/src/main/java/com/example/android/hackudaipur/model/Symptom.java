package com.example.android.hackudaipur.model;

public class Symptom {
    private String symptomName;
    private boolean isSelected;

    public Symptom(String symptomName, boolean isSelected) {
        this.symptomName = symptomName;
        this.isSelected = isSelected;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
