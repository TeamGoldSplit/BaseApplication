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

public class KMeansImageFragment extends Fragment {

    private String title;
    private int page;
    private String bmpFile;

    private Bitmap bmp;

    public static final String ARG_OBJECT = "ImageView index";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.most_prominent_color_frag, container, false);
        Bundle args = getArguments();

        assert args != null;

        ImageView imgView = (ImageView) rootView.findViewById(R.id.single_color);
        imgView.setImageBitmap(bmp);

        return rootView;
    }

    public static KMeansImageFragment newInstance(int pageNum, String title, String bmpFileName){
        KMeansImageFragment cFrag = new KMeansImageFragment();
        Bundle args = new Bundle();
        args.putString("bmpFileName", bmpFileName);
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
        bmpFile = getArguments().getString("bmpFileName");
        bmp = load(bmpFile, getActivity());
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
