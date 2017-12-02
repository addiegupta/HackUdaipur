package com.example.android.hackudaipur.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.hackudaipur.R;
import com.example.android.hackudaipur.model.Symptom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
        viewHolder.mTVSymptomName.setText(symptom.getSymptomName());
        viewHolder.mSymptomCheckbox.setChecked(symptom.isSelected());

        viewHolder.mSymptomCheckbox.setTag(symptomList.get(position));
        viewHolder.mSymptomCheckbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;
                Symptom symptom = (Symptom) cb.getTag();
                symptom.setSelected(cb.isChecked());
                symptomList.get(pos).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return symptomList == null ? 0 : symptomList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_symptom_name)
        TextView mTVSymptomName;
        @BindView(R.id.checkBox)
        CheckBox mSymptomCheckbox;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }
    }

    public List<Symptom> getSymptomList() {
        return symptomList;
    }
}