package ssu.groupname.baseapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


class ColorCalcTask {



    ArrayList<int[]> kMeans(Bitmap bmp, Context context, int quality){

        Mat rgba = new Mat();
        //Resize for easier computation
        int maxWidth;
        switch (quality){
            case 0: maxWidth = 200;
            case 1: maxWidth = 400;
            case 2: maxWidth = 800;
            default: maxWidth = 200;
        }
        Bitmap resized = getResizedBitmap(bmp, maxWidth);
        Utils.bitmapToMat(resized, rgba);
        Mat rgb = new Mat();
        Mat imgLab = new Mat();

        //Convert RGBA mat to RGB
        Imgproc.cvtColor(rgba, rgb, Imgproc.COLOR_RGBA2RGB,3);


//SET UP ARGUMENTS

        Mat samples = rgb.reshape(1, rgb.cols() * rgb.rows());
        int n = rgb.rows() * rgb.cols();
        Mat data = rgb.reshape(1, n);
        data.convertTo(data, CvType.CV_32F);
        //samples.convertTo(data, CvType.CV_32F, 1.0 / 255.0);

        Mat labels = new Mat();
        TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER, 100, 1);
        Mat colors = new Mat();
        //Mat clusteredLab = new Mat();

//DO THE THING
        Core.kmeans(data, 6, labels, criteria, 10, Core.KMEANS_PP_CENTERS, colors);
        Log.i("labels first element", Double.toString(labels.get(0,0)[0]));
        Log.i("AH", "rows, cols in rgb: " + Integer.toString(rgb.rows()) + ", " + Integer.toString(rgb.cols()));
        Log.i("AH", "rows, cols in data: " + Integer.toString(data.rows()) + ", " + Integer.toString(data.cols()));
        Log.i("AH", "rows, cols in labels: " + Integer.toString(labels.rows()) + ", " + Integer.toString(labels.cols()));
        Log.i("AH", "rows, cols in colors: " + Integer.toString(colors.rows()) + ", " + Integer.toString(colors.cols()));

//OPERATE ON THE DATA
        for(int i = 0; i < n; i++){
            int r = (int)labels.get(i, 0)[0];
            data.put(i,0, colors.get(r, 0));
            data.put(i,1, colors.get(r, 1));
            data.put(i,2, colors.get(r, 2));
        }

        Mat reduced = data.reshape(3, rgb.rows());
        reduced.convertTo(reduced, CvType.CV_8U);

        Bitmap output = Bitmap.createBitmap(reduced.cols(), reduced.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(reduced, output);

        save("kmeans_output.png", output, context);

        colors.convertTo(colors, CvType.CV_8UC1);
        colors.reshape(3);

        Log.i("colors", "kMeans: " + colors.dump());

        ArrayList<int[]> finalColors = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            int r = (int)colors.get(i, 0)[0];
            int g = (int)colors.get(i, 1)[0];
            int b = (int)colors.get(i, 2)[0];
            int[] color = {r, g, b};
            finalColors.add(color);
        }
        storeColorsHex(finalColors, context);
        return finalColors;
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void generatePalette(int[] seed, Context context){

        float[] hsv0 = new float[3];
        Color.RGBToHSV(seed[0], seed[1], seed[2], hsv0);
        Log.i("hsv0", "generatePalette: hsv0:" + Float.toString(hsv0[0]) + " " + Float.toString(hsv0[1]) + " " + Float.toString(hsv0[2]));
        float[] hsv1 = new float[3];
        float[] hsv2 = new float[3];
        float[] hsv3 = new float[3];
        float[] hsv4 = new float[3];
        float[] hsv5 = new float[3];

        float hueIncrement1 = 120;
        float hueIncrement2 = 30;

        float saturationIncrement;
        if(hsv0[1] < .5)
            saturationIncrement = -1/12f;
        else
            saturationIncrement = 1/12f;

        float valueIncrement;
        if(hsv0[2] < .5)
            valueIncrement = -1/8f;
        else
            valueIncrement = 1/8f;

        hsv1[0] = (hsv0[0] + hueIncrement2)%360f;
        hsv2[0] = (hsv0[0] + (2*hueIncrement2))%360f;
        hsv3[0] = (hsv0[0] + hueIncrement1)%360f;
        hsv4[0] = (hsv0[0] + hueIncrement1 + hueIncrement2)%360f;
        hsv5[0] = (hsv0[0] + hueIncrement1 - hueIncrement2)%360f;


        hsv1[1] = (hsv0[1] - saturationIncrement)%1f;
        hsv2[1] = (hsv0[1] - saturationIncrement)%1f;
        hsv3[1] = (hsv0[1] + saturationIncrement)%1f;
        hsv4[1] = (hsv0[1] + saturationIncrement)%1f;
        hsv5[1] = (hsv0[1] + (2*saturationIncrement))%1f;

        hsv1[2] = (hsv0[2] - valueIncrement)%1f;
        hsv2[2] = (hsv0[2] - valueIncrement)%1f;
        hsv3[2] = (hsv0[2] + valueIncrement)%1f;
        hsv4[2] = (hsv0[2] + valueIncrement)%1f;
        hsv5[2] = (hsv0[2] + (2*valueIncrement))%1f;


        ArrayList<float[]> hsvs = new ArrayList<>();
        hsvs.add(hsv0);
        hsvs.add(hsv1);
        hsvs.add(hsv2);
        hsvs.add(hsv3);
        hsvs.add(hsv4);
        hsvs.add(hsv5);

        Bitmap bmp0 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp0.eraseColor(Color.HSVToColor(hsv0));
        Bitmap bmp1 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp1.eraseColor(Color.HSVToColor(hsv1));
        Bitmap bmp2 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp2.eraseColor(Color.HSVToColor(hsv2));
        Bitmap bmp3 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp3.eraseColor(Color.HSVToColor(hsv3));
        Bitmap bmp4 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp4.eraseColor(Color.HSVToColor(hsv4));
        Bitmap bmp5 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp5.eraseColor(Color.HSVToColor(hsv5));
        save("generatedPalette0", bmp0, context);
        save("generatedPalette1", bmp1, context);
        save("generatedPalette2", bmp2, context);
        save("generatedPalette3", bmp3, context);
        save("generatedPalette4", bmp4, context);
        save("generatedPalette5", bmp5, context);

        String[] hexCodes = new String[6];
        for(int i = 0; i < 6; i++){
            hexCodes[i] = buildHex(Color.HSVToColor(hsvs.get(i)));
        }
        writeToFile("gen_palette_hexes.txt", hexCodes, context);
    }




    private static void save(String filename, Bitmap bmp, Context context){
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

    private void writeToFile(String filename, String[] data,Context context) {
        OutputStreamWriter outputStreamWriter;
        try {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            for(int i = 0; i < 6; i++){
                outputStreamWriter.write(data[i] + System.getProperty("line.separator"));
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String buildHex (int colorInt){
        String hex = String.format("#%06X", (0xFFFFFF & colorInt));
        return hex;
    }

    private void storeColorsHex(ArrayList<int[]> colors, Context context) {
        String[] data = new String[6];
        for (int i = 0; i < 6; i++){
            int r = colors.get(i)[0];
            int g = colors.get(i)[1];
            int b = colors.get(i)[2];
            data[i] = buildHex(Color.rgb(r, g, b));
        }
        writeToFile("decomposed_colors_hex.txt", data, context);
    }
}
