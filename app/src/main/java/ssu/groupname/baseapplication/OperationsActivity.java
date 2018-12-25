package ssu.groupname.baseapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStoragePublicDirectory;


public class OperationsActivity extends AppCompatActivity {

    private Button computeButton;
    RadioGroup radioGroup;
    private RadioButton lowQual;
    private RadioButton medQual;
    private RadioButton highQual;
    private String origin;
    private ProgressBar spinner;
    private ImageView fileImage;
    private Bitmap bmp;
    private String fileOrCamera;
    private Uri originalURI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        spinner = (ProgressBar)findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);

        fileImage = (ImageView) findViewById(R.id.image_view);

        Bundle extras = getIntent().getExtras();
        //Check if we came from camera or file Activity
        fileOrCamera = extras.getString("fileOrCamera");
//        imgFilePath = extras.getString("imgFilePath");
        BitmapIO bIO = new BitmapIO();
        switch (fileOrCamera) {
            case "file":
                originalURI = Uri.parse(getIntent().getExtras().getString("imageUri"));
                //open image as bitmap
                bmp = bIO.loadBMPFromFile(originalURI, OperationsActivity.this);
                fileImage.setImageBitmap(bmp);
                break;
            case "camera":
                //load image from file
                bmp = bIO.loadBMPFromFile(getExternalStorageDirectory() + "/TeamGoldSplit/temp", OperationsActivity.this);
                fileImage.setImageBitmap(bmp);
                fileImage.setRotation(90f);

                break;
            default:
                Log.d("cameraOrFile", "onCreate: ERROR cameraOrFile Extra not parsed correctly");
                break;
        }


        //IMAGE VIEW

        fileImage = (ImageView) findViewById(R.id.image_view);
        fileImage.setImageBitmap(bmp);

        //opsButton
        computeButton = findViewById(R.id.compute_button);
        computeButton.setEnabled(false);
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                //BITMAP GOES HERE!!! All you need to do is assign it to img, and the rest is taken care of
                //Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
                int quality = 0;
                if(medQual.isChecked())
                    quality = 1;
                if(highQual.isChecked())
                    quality = 2;
                ArrayList<int[]> colors = new ColorCalcTask().kMeans(bmp, OperationsActivity.this, quality);


                Intent computeIntent = new Intent(OperationsActivity.this, FinalActivity.class);
                switch (fileOrCamera){
                    case "file":
                        computeIntent.putExtra("fileOrCamera", "file");
                        computeIntent.putExtra("originalURI", originalURI);
                        break;
                    case "camera":
                        computeIntent.putExtra("fileOrCamera", "camera");
                        break;
                }

                for(int i = 0; i < 6; i++){
//                    computeIntent.putExtra("Color" + Integer.toString(i), colors.get(i));
//                    Bitmap bmp = new GSColor(colors.get(i)[0],colors.get(i)[1],colors.get(i)[2]).getBmp();
//
//                    //Write to file
//                    //5: most prominent
//                    //0-5: 6 most prominent
//                    String filename = "color" + Integer.toString(i) + ".png";
//                    save(filename, bmp, OperationsActivity.this);
//                    computeIntent.putExtra("color_image" + Integer.toString(i), filename);
                   new ColorCalcTask().generatePalette(colors.get(i), OperationsActivity.this, i);
                }
                //spinner.setVisibility(View.GONE);
                startActivity(computeIntent);
            }
        });
        //RadioGroup
        radioGroup = findViewById(R.id.quality_buttons);
        //RadioButtons
        lowQual = findViewById(R.id.low_quality);
        medQual = findViewById(R.id.med_quality);
        highQual = findViewById(R.id.high_quality);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                computeButton.setEnabled(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent topIntent = new Intent(OperationsActivity.this, MainActivity.class);
        topIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(topIntent);
    }


}
