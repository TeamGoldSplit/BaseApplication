package ssu.groupname.baseapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PaletteFragment extends Fragment {
    private String[] hexCodes;

    private Bitmap bmp0;
    private Bitmap bmp1;
    private Bitmap bmp2;
    private Bitmap bmp3;
    private Bitmap bmp4;
    private Bitmap bmp5;

    private String hex0;
    private String hex1;
    private String hex2;
    private String hex3;
    private String hex4;
    private String hex5;

    public static final String ARG_OBJECT = "ImageView index";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.palette_frag, container, false);
        Bundle args = getArguments();

        assert args != null;

        ImageView imgView0 = (ImageView) rootView.findViewById(R.id.palette0);
        ImageView imgView1 = (ImageView) rootView.findViewById(R.id.palette1);
        ImageView imgView2 = (ImageView) rootView.findViewById(R.id.palette2);
        ImageView imgView3 = (ImageView) rootView.findViewById(R.id.palette3);
        ImageView imgView4 = (ImageView) rootView.findViewById(R.id.palette4);
        ImageView imgView5 = (ImageView) rootView.findViewById(R.id.palette5);
        imgView0.setImageBitmap(bmp0);
        imgView1.setImageBitmap(bmp1);
        imgView2.setImageBitmap(bmp2);
        imgView3.setImageBitmap(bmp3);
        imgView4.setImageBitmap(bmp4);
        imgView5.setImageBitmap(bmp5);

        TextView textView0 = (TextView) rootView.findViewById(R.id.palette0_hex);
        TextView textView1 = (TextView) rootView.findViewById(R.id.palette1_hex);
        TextView textView2 = (TextView) rootView.findViewById(R.id.palette2_hex);
        TextView textView3 = (TextView) rootView.findViewById(R.id.palette3_hex);
        TextView textView4 = (TextView) rootView.findViewById(R.id.palette4_hex);
        TextView textView5 = (TextView) rootView.findViewById(R.id.palette5_hex);

        textView0.setText(hex0);
        textView1.setText(hex1);
        textView2.setText(hex2);
        textView3.setText(hex3);
        textView4.setText(hex4);
        textView5.setText(hex5);

        return rootView;
    }

    public static PaletteFragment newInstance(int pageNum, String title, String[] bmpFileNames){
        PaletteFragment cFrag = new PaletteFragment();
        Bundle args = new Bundle();
        args.putStringArray("bmpFileNames", bmpFileNames);
        args.putInt("page_number", pageNum);
        args.putString("title", title);
        cFrag.setArguments(args);
        return cFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        try {
            hexCodes = readFromFile(getActivity());
        } catch (Exception e){
            e.printStackTrace();
        }
        hex0 = hexCodes[5];
        hex1 = hexCodes[4];
        hex2 = hexCodes[3];
        hex3 = hexCodes[2];
        hex4 = hexCodes[1];
        hex5 = hexCodes[0];

        bmp0 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp0.eraseColor(Color.parseColor(hex0));
        bmp1 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp1.eraseColor(Color.parseColor(hex1));
        bmp2 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp2.eraseColor(Color.parseColor(hex2));
        bmp3 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp3.eraseColor(Color.parseColor(hex3));
        bmp4 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp4.eraseColor(Color.parseColor(hex4));
        bmp5 = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        bmp5.eraseColor(Color.parseColor(hex5));

    }

    public static Bitmap loadBMP(String filename, Context context){
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
    private String[] readFromFile(Context context) {

        String[] ret = new String[6];

        try {
            InputStream inputStream = context.openFileInput("decomposed_colors_hex.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                for(int i = 0; i < 6; i++){
                    String line = bufferedReader.readLine();
                    ret[i] = line;
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("PaletteFrag", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("PaletteFrag", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
