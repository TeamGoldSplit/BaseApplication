package ssu.groupname.baseapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

public class OriginalImageFragment extends Fragment {
    private String title;
    private int page;
    private String bmpFile;
    private Button rotate;
    private float rotation;

    private Bitmap bmp;

    public static final String ARG_OBJECT = "ImageView index";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.orig_image_frag, container, false);
        Bundle args = getArguments();

        assert args != null;

        ImageView imgView = (ImageView) rootView.findViewById(R.id.orig_imageView);
        imgView.setImageBitmap(bmp);
        imgView.setRotation(rotation);

        rotate = (Button) rootView.findViewById(R.id.orig_rotate_image_button);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation = (rotation + 90f) % 360f;
                imgView.setRotation(rotation);
            }
        });

        return rootView;
    }

    public static OriginalImageFragment newInstance(int pageNum, String title, String bmpFile){
        OriginalImageFragment cFrag = new OriginalImageFragment();
        Bundle args = new Bundle();
        args.putInt("page_number", pageNum);
        args.putString("title", title);
        args.putString("bmpFileName", bmpFile);
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

        BitmapIO bIO = new BitmapIO();
        String fileOrCamera = ((FinalActivity)getActivity()).getFileOrCamera();
        switch (fileOrCamera){
            case "file":
                Uri imgURI = ((FinalActivity)getActivity()).getOriginalURI();
                rotation = ((FinalActivity)getActivity()).getOrigRotation();
                bmp = bIO.loadBMPFromFile(imgURI, getContext());
                break;
            case "camera":
                bmp = bIO.loadBMPFromFile(bmpFile, getContext());
                break;
        }
    }

}
