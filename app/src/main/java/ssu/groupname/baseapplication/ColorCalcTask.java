package ssu.groupname.baseapplication;

import android.graphics.Bitmap;
import android.os.AsyncTask;

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
import java.util.List;



public class ColorCalcTask extends AsyncTask<Bitmap, Integer, Long> {

    public Bitmap find_histogram(Bitmap bMap){

        Bitmap img = bMap;

        Mat nMat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        Mat imgMat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);

        Utils.bitmapToMat(img, nMat);

        Imgproc.cvtColor(imgMat, nMat, Imgproc.COLOR_RGB2HSV);


        Utils.bitmapToMat(img, imgMat);

        List<Mat> matList = new ArrayList<>();
        Core.split(imgMat, matList);

        int histSize = 360;

        float[] range = {0, 360};
        MatOfFloat histRange = new MatOfFloat(range);

        boolean accumulate = false;

        Mat hHist = new Mat(), sHist = new Mat(), vHist = new Mat();
        Imgproc.calcHist(matList, new MatOfInt(0), new Mat(), hHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(matList, new MatOfInt(1), new Mat(), sHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(matList, new MatOfInt(2), new Mat(), vHist, new MatOfInt(histSize), histRange, accumulate);


        int hist_w = 160;
        int hist_h = 160;
        int bin_w = (int) Math.round((double) hist_w / histSize);

        Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC3, new Scalar(0, 0, 0));

        Core.normalize(hHist, hHist, 0, histImage.rows(), Core.NORM_MINMAX);
        Core.normalize(sHist, sHist, 0, histImage.rows(), Core.NORM_MINMAX);
        Core.normalize(vHist, vHist, 0, histImage.rows(), Core.NORM_MINMAX);

        //Actual histogram axes
        float[] hHistData = new float[(int) (hHist.total() * hHist.channels())];
        hHist.get(0, 0, hHistData);
        float[] sHistData = new float[(int) (sHist.total() * sHist.channels())];
        sHist.get(0, 0, sHistData);
        float[] vHistData = new float[(int) (vHist.total() * vHist.channels())];
        vHist.get(0, 0, vHistData);

        //Histogram partitioning

        for(int i = 1; i < histSize; i++){
            Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hHistData[i - 1])),
                    new Point(bin_w * (i), hist_h - Math.round(hHistData[i])), new Scalar(255, 0, 0), 2);
            Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(sHistData[i - 1])),
                    new Point(bin_w * (i), hist_h - Math.round(sHistData[i])), new Scalar(0, 255, 0), 2);
            Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(vHistData[i - 1])),
                    new Point(bin_w * (i), hist_h - Math.round(vHistData[i])), new Scalar(0, 0, 255), 2);
        }

        Bitmap histBmp = Bitmap.createBitmap(hist_h,hist_w, Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(histImage, histBmp);

        return histBmp;
    }

    public void kMeans(Mat imgMat){

//        Mat rgbMat = new Mat(imgMat.height(), imgMat.width(), CvType.CV_8UC3);
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
//        Core.kmeans(rgbMat, 2, clusteredHSV, criteria, 10, Core.KMEANS_PP_CENTERS);
//
//        Bitmap bmp = Bitmap.createBitmap(rgbMat.height(),rgbMat.width(),Bitmap.Config.ARGB_8888);
//
//        Utils.matToBitmap(rgbMat, bmp);

        //
    }


    @Override
    protected Long doInBackground(Bitmap... bmps) {
        Bitmap paramBMP = bmps[0];

        Mat imgMat = new Mat(paramBMP.getHeight(), paramBMP.getWidth(), CvType.CV_8UC3);
        Mat rgbMat = new Mat(paramBMP.getHeight(), paramBMP.getWidth(), CvType.CV_8UC3);

        Utils.bitmapToMat(paramBMP, rgbMat);

        Imgproc.cvtColor(imgMat, rgbMat, Imgproc.COLOR_RGBA2RGB,3);

        List<Mat> rgb_planes = new ArrayList<Mat>(3);
        Core.split(rgbMat, rgb_planes);


        Mat channel = rgb_planes.get(2);
        channel = Mat.zeros(rgbMat.rows(),rgbMat.cols(),CvType.CV_8UC1);
        rgb_planes.set(2,channel);
        Core.merge(rgb_planes,rgbMat);



        Mat clusteredHSV = new Mat();
        rgbMat.convertTo(rgbMat, CvType.CV_32FC3);
        TermCriteria criteria = new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER,100,0.1);
        Core.kmeans(rgbMat, 2, clusteredHSV, criteria, 10, Core.KMEANS_PP_CENTERS);

        Bitmap bmp = Bitmap.createBitmap(rgbMat.height(),rgbMat.width(),Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(rgbMat, bmp);
        return null;
    }
}
