package ssu.groupname.baseapplication;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class CameraActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout cameraScreen;
    ShowCamera showCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraScreen = (FrameLayout)findViewById(R.id.cameraScreen);
        camera = Camera.open();
        showCamera = new ShowCamera(this, camera);
        cameraScreen.addView(showCamera);
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
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new java.util.Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpeg";
        String state = Environment.getExternalStorageState();
        if(!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        } else {
            File folder_TGS = new File(Environment.getExternalStorageDirectory() + File.separator + "TeamGoldSplit");
            File outPutFile = new File(folder_TGS, imageFileName);
            return outPutFile;
        }
    }
    public void captureImage(View v) {
        if(camera != null) {
            camera.takePicture(null, null, mPictureCallBack);
        }
    }
}
