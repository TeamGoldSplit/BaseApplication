package ssu.groupname.baseapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class KMeansImageFragment extends Fragment {

    private String title;
    private int page;
    private String bmpFile;
    private Button rotate;
    private float rotation;

    private Bitmap bmp;

    public static final String ARG_OBJECT = "ImageView index";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.kmeans_image_frag, container, false);
        Bundle args = getArguments();

        assert args != null;

        ImageView imgView = (ImageView) rootView.findViewById(R.id.kmeans_imageView);
        imgView.setImageBitmap(bmp);
        imgView.setRotation(rotation);

        rotate = (Button) rootView.findViewById(R.id.rotate_image_button);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation = (rotation + 90f) % 360f;
                imgView.setRotation(rotation);
            }
        });

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
        File file = new File(bmpFile);

        rotation = ((FinalActivity)getActivity()).getKmeansRotation();

        BitmapIO bIO = new BitmapIO();
        bmp = bIO.loadBMPFromFile(bmpFile, getContext());

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
