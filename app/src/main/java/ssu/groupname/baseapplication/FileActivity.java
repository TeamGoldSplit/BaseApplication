package ssu.groupname.baseapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileActivity extends AppCompatActivity {


    public static final int IMAGE_GALLERY_REQUEST = 20;

    private ImageButton opsButton;
    private Button ops2Button;
    private Uri imageUri;
    private Bitmap Bitimage;
    private ImageView imageView;
    private static final int PICK_IMAGE = 100;
    private String filename = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/TeamGoldSplit/temp.jpeg";

    private String pictureDirectoryPath;
    private File pictureDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        imageView = (ImageView) findViewById(R.id.image_view);


        //opsButton
        opsButton = findViewById(R.id.ops_button);

        opsButton.setEnabled(false);

        opsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent opsIntent = new Intent(FileActivity.this, OperationsActivity.class);

                Drawable drawable = imageView.getDrawable();
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] b = baos.toByteArray();
                //saveBMP(filename, bitmap, FileActivity.this);
//                try (FileOutputStream out = new FileOutputStream((filename))){
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                } catch (IOException e){
//                    e.printStackTrace();
//                }

                Intent intent = new Intent(FileActivity.this, OperationsActivity.class);
                intent.putExtra("imageView", bitmap);
                startActivity(intent);

            }
        });


        //imageView = (ImageView) findViewById(R.id.image_view);
        ops2Button = findViewById(R.id.ops2_button);
        ops2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


    }
    public void onActivityResult(View v) {

    }

    //Method i created to use above when they click the ops button
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            //going good if you get here

            //listening to hear back from image gallery
            imageUri = data.getData();
            //display the image
            //imageView.setImageURI(imageUri);

            // converts uri to stream
            InputStream pictureInputStream;
            try{
                //convert the URI to a stream
                pictureInputStream = getContentResolver().openInputStream(imageUri);

                //open image as bitmap
                Bitmap Bitimage = BitmapFactory.decodeStream(pictureInputStream);
                //show the bitmap through our imageview
                imageView.setImageBitmap(Bitimage);
                opsButton.setEnabled(true);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }


        }
    }
    public String saveBMP(String filename, Bitmap bmp, Context context){
        String savedImagePath = null;
        String imageFileName = "temp.jpeg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/TeamGoldSplit/");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            //Toast.makeText(context, "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
//        Bitmap saveBMP = null;
//        try{
//            //write
//            saveBMP = bmp;
//            saveBMP.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
//
//            //cleanup
//            fileOut.close();
//            bmp.recycle();
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }
    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
}