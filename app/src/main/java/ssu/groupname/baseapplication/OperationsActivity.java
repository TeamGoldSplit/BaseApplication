package ssu.groupname.baseapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.VolumeShaper;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.File;
import java.util.ArrayList;

public class OperationsActivity extends AppCompatActivity {

    private Button backButton;
    private Button computeButton;
    private String origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        Intent intent = getIntent();
        origin = intent.getStringExtra("origin");

        //backButton
        /*backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent;
                if (origin == "camera")
                    backIntent = new Intent(OperationsActivity.this, CameraActivity.class);
                else //yeah this is kinda sloppy but I don't wanna write the exception catching rn
                    backIntent = new Intent(OperationsActivity.this, FileActivity.class);
                startActivity(backIntent);
            }
        });*/
        //opsButton

        computeButton = findViewById(R.id.compute_button);
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bitmap img = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() +
//                                                        File.separator + "TeamGoldSplit" + File.separator +
//                                                        "temp" + File.separator + "temp.jpg");
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.toucan);
                Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
                Utils.bitmapToMat(img, mat);
                ArrayList<int[]> colors = new ColorCalcTask().kMeans(img);
                Intent computeIntent = new Intent(OperationsActivity.this, FinalActivity.class);
                computeIntent.putExtra("Color0", colors.get(0));
                computeIntent.putExtra("Color1", colors.get(1));
                computeIntent.putExtra("Color2", colors.get(2));
                computeIntent.putExtra("Color3", colors.get(3));
                computeIntent.putExtra("Color4", colors.get(4));
                computeIntent.putExtra("Color5", colors.get(5));
                startActivity(computeIntent);
            }
        });
    }
}
