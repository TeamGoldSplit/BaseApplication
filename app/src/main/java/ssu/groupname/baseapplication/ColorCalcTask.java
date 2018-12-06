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
import java.util.ArrayList;


class ColorCalcTask {



    ArrayList<int[]> kMeans(Bitmap bmp, Context context){

        Mat rgba = new Mat();
        //Resize for easier computation
        Bitmap resized = getResizedBitmap(bmp, 400);
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
        int zero = 0, one = 0, two = 0, three = 0, four = 0, five = 0;
        for(int i = 0; i < n; i++){
            int r = (int)labels.get(i, 0)[0];
            switch (r){
                case 0: zero++;
                case 1: one++;
                case 2: two++;
                case 3: three++;
                case 4: four++;
                case 5: five++;
            }
            data.put(i,0, colors.get(r, 0));
            data.put(i,1, colors.get(r, 1));
            data.put(i,2, colors.get(r, 2));
        }
        int[] choice = {zero, one, two, three, four, five};
        int max = 0;
        int maxIndex = 0;
        for(int i = 0; i < 6; i++){
            if(choice[i] > max)
                max = choice[i];
        }
        for(int i = 0; i < 6; i++){
            if(choice[i] == max)
                maxIndex = i;
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
        int[] temp = new int[3];
        temp = finalColors.get(0);
        finalColors.set(0, finalColors.get(maxIndex));
        for(int i = 0; i < 6; i++){
            if(finalColors.get(i) == finalColors.get(maxIndex))
                finalColors.set(i, temp);
        }
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
        float[] hsv1 = new float[3];
        float[] hsv2 = new float[3];
        float[] hsv3 = new float[3];
        float[] hsv4 = new float[3];
        float[] hsv5 = new float[3];

        float increment = (float)1/3;
        for(int i = 0; i < 3; i+=1){
            hsv1[i] = (hsv0[i] + increment)%1;
            hsv2[i] = (hsv0[i] + (2* increment))%1;
        }

        float increment2 = (float)1/7;
        for(int i = 0; i < 3; i+=1){
            hsv3[i] = (hsv0[i] + increment2)%1;
            hsv4[i] = (hsv1[i] + increment2)%1;
            hsv5[i] = (hsv2[i] + increment2)%1;
        }

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
}
