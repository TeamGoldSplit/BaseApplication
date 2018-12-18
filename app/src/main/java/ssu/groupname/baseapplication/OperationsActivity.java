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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


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
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        spinner = (ProgressBar)findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);

        //Bundle extras = getIntent().getExtras();
        //Check if we came from camera or file Activity
        String cameraOrFile = getIntent().getExtras().getString("cameraOrFile");
        switch (cameraOrFile) {
            case "file":
                imageUri = Uri.parse(getIntent().getExtras().getString("imageUri"));
                // converts uri to stream
                InputStream pictureInputStream;
                try {
                    //convert the URI to a stream
                    pictureInputStream = getContentResolver().openInputStream(imageUri);

                    //open image as bitmap
                    bmp = BitmapFactory.decodeStream(pictureInputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "camera":
//                bmp = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/TeamGoldSplit/temp.jpeg");
                bmp = load(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/TeamGoldSplit/temp.jpeg", OperationsActivity.this);
                break;
            default:
                Log.d("cameraOrFile", "onCreate: ERROR cameraOrFile Extra not parsed correctly");
                break;
        }

        ImageView fileImage = (ImageView) findViewById(R.id.image_view);
        fileImage.setImageBitmap(bmp);


       // Intent intent = getIntent();
        //origin = intent.getStringExtra("origin");

        //fileImage = (ImageVientent.getStringExtra("origin");w) findViewById(R.id.image_view);
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

    public static Bitmap load(String filename, Context context){
        FileInputStream fileIn;
        Bitmap bmp = null;
        try{
            fileIn = context.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(fileIn);
            fileIn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return bmp;
    }


}
