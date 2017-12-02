package com.example.android.hackudaipur.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.hackudaipur.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiagnosisActivity extends AppCompatActivity {

    @BindView(R.id.btn_finish_diagnosis)
    Button mFinishDiagnosisButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        ButterKnife.bind(this);

        mFinishDiagnosisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDiagnosis();
            }
        });
    }

    // To be improved when more time is available at hand
    private void handleDiagnosis(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(R.string.in_progress);
        dialog.setMessage(getString(R.string.waiting_for_diagnosis));
        dialog.setCancelable(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                dialog.dismiss();
                startActivity(new Intent(DiagnosisActivity.this,PostDiagnosisActivity.class));
            }
        },10000);
    }
}
