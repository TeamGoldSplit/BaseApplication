package ssu.groupname.baseapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.FileInputStream;

public class PaletteFragment extends Fragment {
    private String title;
    private int page;
    private String[] bmpFileNames;

    private Bitmap bmp0;
    private Bitmap bmp1;
    private Bitmap bmp2;
    private Bitmap bmp3;
    private Bitmap bmp4;
    private Bitmap bmp5;

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
        page = getArguments().getInt("page_number");
        title = getArguments().getString("title");
        bmpFileNames = getArguments().getStringArray("bmpFileNames");
        try {
            bmp0 = load(bmpFileNames[0], getActivity());
            bmp1 = load(bmpFileNames[1], getActivity());
            bmp2 = load(bmpFileNames[2], getActivity());
            bmp3 = load(bmpFileNames[3], getActivity());
            bmp4 = load(bmpFileNames[4], getActivity());
            bmp5 = load(bmpFileNames[5], getActivity());
        } catch (Exception e){
            e.printStackTrace();
        }

        bmp0 = getResizedBitmap(bmp0, 120);
        bmp1 = getResizedBitmap(bmp1, 120);
        bmp2 = getResizedBitmap(bmp2, 120);
        bmp3 = getResizedBitmap(bmp3, 120);
        bmp4 = getResizedBitmap(bmp4, 120);
        bmp5 = getResizedBitmap(bmp5, 120);

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
}
