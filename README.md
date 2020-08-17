<h1 align="center">Awesome Code scanner </h1>

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[ ![Download](https://api.bintray.com/packages/gpfreetech/AwesomeCodeScanner/AwesomeCodeScanner/images/download.svg?version=1.1) ](https://bintray.com/gpfreetech/AwesomeCodeScanner/AwesomeCodeScanner/1.1/link)
[ ![Bintray](https://img.shields.io/badge/Bintray-v1.1-red) ](https://bintray.com/gpfreetech/AwesomeCodeScanner/AwesomeCodeScanner/1.1/link)


![Github Followers](https://img.shields.io/github/followers/govindapaliwal?label=Follow&style=social)
![GitHub stars](https://img.shields.io/github/stars/govindapaliwal/AwesomeCodeScanner?style=social)
![GitHub forks](https://img.shields.io/github/forks/govindapaliwal/AwesomeCodeScanner?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/govindapaliwal/AwesomeCodeScanner?style=social)


Awesome Code scanner library for [Android](https://developer.android.com), 
This is multi code scanner module. You can scan QR, Barcode etc in your app using this library.
This library is based on [ZXing](https://github.com/zxing/zxing). If you are not familiar with ZXing then check [this](https://github.com/zxing/zxing). link 

Sample Screen
------
![Sample][image_sample]

### Why Awesome Code scanner
* Scanning Animation 
* Back and front facing cameras Supported
* You Can customize viewfinder View
* Provide control for Auto focus and flash light 
* Portrait and landscape screen orientations
* Focus on Touch

### Uses ([example](https://github.com))
### Step 1
Add dependency in your gradle:
```gradle
dependencies {
    implementation 'com.gpfreetech:AwesomeCodeScanner:1.1'
}
```
### Step 2
Add camera permission to AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.CAMERA"/>
```
Don't forget to add dynamic permissions on API >= 23;

### Step 3
Here your layout file code:
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
        
        <com.gpfreetech.awesomescanner.ui.ScannerView
            android:id="@+id/scanner_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight=".3"
            app:autoFocusButtonColor="@android:color/white"
            app:autoFocusButtonVisible="true"
            app:flashButtonColor="@android:color/white"
            app:flashButtonVisible="true"
            app:frameAspectRatioHeight="1"
            app:frameAspectRatioWidth="1"
            app:frameColor="@android:color/white"
            app:frameCornersRadius="0dp"
            app:frameCornersSize="50dp"
            app:frameSize="0.70"
            app:frameThickness="5dp"
            app:maskColor="#77000000"
            app:isAnimated="true"/>
</FrameLayout>
```
### Step 4
And add following code to your activity:


Java
```java
public class MainActivity extends AppCompatActivity {

    private GpCodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
```
### Additional Methods

### Change / set BACK or FRONT Camera
```
mCodeScanner = new GpCodeScanner(this, scannerView,GpCodeScanner.CAMERA_FRONT);
```
or
```
mCodeScanner = new GpCodeScanner(this, scannerView,GpCodeScanner.CAMERA_BACK);
```
### Animation Enable / disable
```
// in your .xml 
app:isAnimated="true | false"
```

### Who's using it
Does your app use Awesome Code scanner library? If you want to be featured on this list drop me a line.

## How to make contributions
Please read and follow the steps in [CONTRIBUTING.md](/CONTRIBUTING.md)

[image_sample]:https://raw.githubusercontent.com/GovindaPaliwal/AwesomeCodeScanner/master/screenshots/sample.gif
