package ssu.groupname.baseapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PictureActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_camera);

//        File imgFile = new File("storage/emulated/0");
//        if (imgFile.exists()) {
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            ImageView customImage = (ImageView) findViewById(R.id.image);
//            customImage.setImageBitmap(myBitmap);
//        }
    }
}
