package ssu.groupname.baseapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;

public class PictureActivity extends AppCompatActivity {
    public String imgPath;
    public Button intentButton;
    private Bitmap bmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_camera);

        imgPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/TeamGoldSplit/temp.jpeg";
        File imgFile = new File(imgPath);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView customImage = (ImageView) findViewById(R.id.image);
            customImage.setImageBitmap(myBitmap);
        }

        intentButton = findViewById(R.id.compute_button);
        intentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goGoGo = new Intent(PictureActivity.this, OperationsActivity.class);
                bmap = BitmapFactory.decodeFile(imgPath);
                goGoGo.putExtra("imageView", bmap);
            }
        });

//        Button saveBtn = findViewById(R.id.save);
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File saveToPhone = saveImage();
//                ImageView viewImage =
//                if(saveToPhone == null) {
//                    return;
//                } else {
//                    try {
//                        FileOutputStream fos = new FileOutputStream(saveToPhone);
//                        fos.write(data);
//                        fos.close();
//                    } catch(FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//        });
    }
//     private File saveImage() {
//        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new java.util.Date());
//        String imageFileName = timeStamp + ".jpeg";
//        String state = Environment.getExternalStorageState();
//        if(!state.equals(Environment.MEDIA_MOUNTED)) {
//            return null;
//        } else {
//            File folder_TGS = new File(Environment.getExternalStorageDirectory() + File.separator + "TeamGoldSplit");
//            File outPutFile = new File(folder_TGS, imageFileName);
//            return outPutFile;
//        }
//    }
}
