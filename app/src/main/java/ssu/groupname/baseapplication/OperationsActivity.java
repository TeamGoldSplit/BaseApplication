package ssu.groupname.baseapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.VolumeShaper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

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
                //BITMAP GOES HERE!!! All you need to do is assign it to img, and the rest is taken care of
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.toucan);
                ArrayList<int[]> colors = new ColorCalcTask().kMeans(img, OperationsActivity.this);
                Intent computeIntent = new Intent(OperationsActivity.this, FinalActivity.class);
                for(int i = 0; i < 6; i++){
//                    computeIntent.putExtra("Color" + Integer.toString(i), colors.get(i));
                    Bitmap bmp = new GSColor(colors.get(i)[0],colors.get(i)[1],colors.get(i)[2]).getBmp();

                    //Write to file
                    //color naming convention:
                    //0: most prominent
                    //0-5: 6 most prominent
                    String filename = "color" + Integer.toString(i) + ".png";
                    save(filename, bmp, OperationsActivity.this);
                    computeIntent.putExtra("color_image" + Integer.toString(i), filename);
                }
                Random r = new Random();
                new ColorCalcTask().generatePalette(colors.get(r.nextInt(5)), OperationsActivity.this);
                startActivity(computeIntent);
            }
        });
    }

    public static void save(String filename, Bitmap bmp, Context context){
        FileOutputStream fileOut;
        Bitmap saveBMP = null;
        try{
            //write
            fileOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            saveBMP = bmp;
            saveBMP.compress(Bitmap.CompressFormat.PNG, 100, fileOut);

            //cleanup
            fileOut.close();
            bmp.recycle();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
