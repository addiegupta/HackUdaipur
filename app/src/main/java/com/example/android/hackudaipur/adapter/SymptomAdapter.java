package com.example.android.hackudaipur.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.hackudaipur.R;
import com.example.android.hackudaipur.model.Symptom;

import java.util.List;


public class SymptomAdapter extends
        RecyclerView.Adapter<SymptomAdapter.ViewHolder> {
    private List<Symptom> symptomList;
    public SymptomAdapter(List<Symptom> symptoms) {
        this.symptomList = symptoms;
    }

    @Override
    public SymptomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.symptom_item_row, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final int pos = position;
        Symptom symptom = symptomList.get(position);
        viewHolder.tvName.setText(symptom.getSymptomName());
        viewHolder.chkSelected.setChecked(symptom.isSelected());
        viewHolder.chkSelected.setTag(symptomList.get(position));
        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Symptom symptom = (Symptom) cb.getTag();

                symptom.setSelected(cb.isChecked());
                symptomList.get(pos).setSelected(cb.isChecked());

                Toast.makeText(
                        v.getContext(),
                        "Selected Symptoms: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return symptomList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public CheckBox chkSelected;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.tv_symptom_name);
            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.checkBox);
        }
    }
    public List<Symptom> getEmployeeList() {
        return symptomList;
    }
}