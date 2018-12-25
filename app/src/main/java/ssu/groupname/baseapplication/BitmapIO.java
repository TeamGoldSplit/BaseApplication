package ssu.groupname.baseapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

class BitmapIO {


    void storeImage(byte[] data, String filename) {
        String completeFilename = filename + ".jpeg";
        File picture_file = getOutPutMediaFile(completeFilename);
        if(picture_file.exists()){
            picture_file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(picture_file.getPath());
            fos.write(data);
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private File getOutPutMediaFile(String filename) {
        String state = Environment.getExternalStorageState();
        if(!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        } else {
            File folder_TGS = new File(Environment.getExternalStorageDirectory() + File.separator + "TeamGoldSplit");
            try {
                folder_TGS.mkdirs();
            } catch (Exception e){
                e.printStackTrace();
            }
            return new File(folder_TGS, filename);
        }
    }

    Bitmap loadBMPFromFile(String filename, Context context){
        String completeFilename = filename + ".jpeg";
        File loadFile = new File(completeFilename);
        Uri bmpURI = Uri.fromFile(loadFile);
        return load(bmpURI, context);
    }

    Bitmap loadBMPFromFile(Uri bmpURI, Context context){
        return load(bmpURI, context);
    }

    private Bitmap load(Uri bmpURI, Context context){
        InputStream pictureInputStream = null;
        try {
            //convert the URI to a stream
            pictureInputStream = context.getContentResolver().openInputStream(bmpURI);
        } catch (Exception e){
            e.printStackTrace();
        }

        return BitmapFactory.decodeStream(pictureInputStream);
    }
}
