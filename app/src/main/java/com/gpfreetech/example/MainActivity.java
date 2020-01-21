package com.gpfreetech.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.gpfreetech.awesomescanner.ui.ScannerView;
import com.gpfreetech.awesomescanner.ui.GpCodeScanner;
import com.gpfreetech.awesomescanner.util.DecodeCallback;

public class MainActivity extends AppCompatActivity {

    private TextView txtScanText;

    private GpCodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtScanText = findViewById(R.id.text);

        RequestCameraPermission requestCameraPermission=new RequestCameraPermission(this);

        if(requestCameraPermission.verifyCameraPermission()) {

            ScannerView scannerView = findViewById(R.id.scanner_view);
            mCodeScanner = new GpCodeScanner(this, scannerView);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
                            txtScanText.setText("" + result.getText());
                        }
                    });
                }
            });

            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"Camera Permission required", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCodeScanner!=null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if(mCodeScanner!=null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }
}
