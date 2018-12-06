package ssu.groupname.baseapplication;

import android.content.Intent;
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
import java.util.concurrent.TimeUnit;

public class CameraActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout cameraScreen;
    ShowCamera showCamera;
    String pathToFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraScreen = (FrameLayout)findViewById(R.id.cameraScreen);
        camera = Camera.open();
        showCamera = new ShowCamera(this, camera);
        cameraScreen.addView(showCamera);
//
//        Button btn = (Button)findViewById(R.id.captureButton);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                captureImage(v);
//            }
//        });

        Button cBtn = (Button)findViewById(R.id.captureButton);
        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage(v);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent cIntent = new Intent(CameraActivity.this,PictureActivity.class);
                startActivity(cIntent);
            }
        });
    }
    Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File picture_file = getOutPutMediaFile();
            if(picture_file == null) {
                return;
            } else {
                try {
                    FileOutputStream fos = new FileOutputStream(picture_file);
                    fos.write(data);
                    fos.close();
                    camera.startPreview();
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private File getOutPutMediaFile() {
        String imageFileName = "temp.jpeg";
        String state = Environment.getExternalStorageState();
        if(!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        } else {
            File folder_TGS = new File(Environment.getExternalStorageDirectory() + File.separator + "TeamGoldSplit");
            File outPutFile = new File(folder_TGS, imageFileName);
            pathToFile = outPutFile.getAbsolutePath();
            return outPutFile;
        }
    }
    public void captureImage(View v) {
        if(camera != null) {
            camera.takePicture(null, null, mPictureCallBack);
        }
    }
}
