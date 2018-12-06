package ssu.groupname.baseapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.TermCriteria;
import org.opencv.features2d.Params;
import org.opencv.imgproc.Imgproc;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.internal.Util;


public class ColorCalcTask extends AsyncTask<Bitmap, Integer, Long> {

    public ArrayList<int[]> kMeans(Bitmap bmp){

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

        colors.convertTo(colors, CvType.CV_8UC1);
        colors.reshape(3);

        Log.i("colors", "kMeans: " + colors.dump());

        ArrayList<int[]> finalColors = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            int r = (int)colors.get(i, 0)[0];
            int g = (int)colors.get(i, 1)[0];
            int b = (int)colors.get(i, 2)[0];
            int[] color = {r, g, b};
            //GSColor gsColor = new GSColor(r, g, b, a);
            finalColors.add(color);
        }
        return finalColors;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
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


    @Override
    protected Long doInBackground(Bitmap... bmps) {
//        Bitmap paramBMP = bmps[0];
//
//        Mat imgMat = new Mat(paramBMP.getHeight(), paramBMP.getWidth(), CvType.CV_8UC3);
//        Mat rgbMat = new Mat(paramBMP.getHeight(), paramBMP.getWidth(), CvType.CV_8UC3);
//
//        Utils.bitmapToMat(paramBMP, rgbMat);
//
//        Imgproc.cvtColor(imgMat, rgbMat, Imgproc.COLOR_RGBA2RGB,3);
//
//        List<Mat> rgb_planes = new ArrayList<Mat>(3);
//        Core.split(rgbMat, rgb_planes);
//
//
//        Mat channel = rgb_planes.get(2);
//        channel = Mat.zeros(rgbMat.rows(),rgbMat.cols(),CvType.CV_8UC1);
//        rgb_planes.set(2,channel);
//        Core.merge(rgb_planes,rgbMat);
//
//
//
//        Mat clusteredHSV = new Mat();
//        rgbMat.convertTo(rgbMat, CvType.CV_32FC3);
//        TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER,100,0.1);
//        Core.kmeans(rgbMat, 6, clusteredHSV, criteria, 10, Core.KMEANS_PP_CENTERS);
//
//        Bitmap bmp = Bitmap.createBitmap(rgbMat.height(),rgbMat.width(),Bitmap.Config.ARGB_8888);
//
//        Utils.matToBitmap(rgbMat, bmp);
        return null;
    }
}
