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
import java.io.OutputStream;
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
                cIntent.putExtra("cameraOrFile", "camera");
                cIntent.putExtra("imgFilePath", pathToFile);
                startActivity(cIntent);
            }
        });
    }
    Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //deleteFiles("/storage/emulated/0/Pictures/TeamGoldSplit/");
            File picture_file = getOutPutMediaFile();
            if(picture_file.exists()){
                picture_file.delete();
            }
            if(picture_file == null) {
                return;
            } else {
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(picture_file.getPath());
                    fos.write(data);
                    fos.close();
                    camera.startPreview();
                } catch(Exception e) {
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
            File folder_TGS = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "TeamGoldSplit");
            try {
                folder_TGS.mkdirs();
            } catch (Exception e){
                e.printStackTrace();
            }
            File outPutFile = new File(folder_TGS, imageFileName);
            try{
                pathToFile = outPutFile.getAbsolutePath();
            } catch (Exception e){
                e.printStackTrace();
            }

            return outPutFile;
        }
    }
    public void captureImage(View v) {
        if(camera != null) {
            camera.takePicture(null, null, mPictureCallBack);
        }
    }

    public static void deleteFiles(String path) {

        File file = new File(path);

        if (file.exists()) {
            String deleteCmd = "rm -r " + path;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (IOException e) { }
        }
    }
}
