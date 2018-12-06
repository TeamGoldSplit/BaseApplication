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
import android.widget.ProgressBar;

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
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        Intent intent = getIntent();
        origin = intent.getStringExtra("origin");

        spinner = (ProgressBar)findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);

        computeButton = findViewById(R.id.compute_button);
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                //BITMAP GOES HERE!!! All you need to do is assign it to img, and the rest is taken care of
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
                ArrayList<int[]> colors = new ColorCalcTask().kMeans(img, OperationsActivity.this, 0);
                Intent computeIntent = new Intent(OperationsActivity.this, FinalActivity.class);
                for(int i = 0; i < 6; i++){
//                    computeIntent.putExtra("Color" + Integer.toString(i), colors.get(i));
                    Bitmap bmp = new GSColor(colors.get(i)[0],colors.get(i)[1],colors.get(i)[2]).getBmp();

                    //Write to file
                    //5: most prominent
                    //0-5: 6 most prominent
                    String filename = "color" + Integer.toString(i) + ".png";
                    save(filename, bmp, OperationsActivity.this);
                    computeIntent.putExtra("color_image" + Integer.toString(i), filename);
                }
                new ColorCalcTask().generatePalette(colors.get(5), OperationsActivity.this);
                //spinner.setVisibility(View.GONE);
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
