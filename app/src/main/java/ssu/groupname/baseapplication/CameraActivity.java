package ssu.groupname.baseapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class CameraActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout cameraScreen;
    ShowCamera showCamera;
    String pathToFile;
    Button cBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraScreen = (FrameLayout)findViewById(R.id.cameraScreen);
        camera = Camera.open();
        showCamera = new ShowCamera(this, camera);
        cameraScreen.addView(showCamera);


        cBtn = (Button)findViewById(R.id.captureButton);
        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage(v);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent cIntent = new Intent(CameraActivity.this, OperationsActivity.class);
                cIntent.putExtra("fileOrCamera", "camera");
                cIntent.putExtra("imgFilePath", pathToFile);
                startActivity(cIntent);
            }
        });
    }
    Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            BitmapIO bIO = new BitmapIO();
            bIO.storeImage(data, "temp");
        }
    };

    public void captureImage(View v) {
        if(camera != null) {
            camera.takePicture(null, null, mPictureCallBack);
        }
    }

}
