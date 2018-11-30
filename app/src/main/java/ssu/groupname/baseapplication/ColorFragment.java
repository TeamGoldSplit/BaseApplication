package ssu.groupname.baseapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class ColorFragment extends Fragment {

    private String title;
    private int page;
    private int bitmapIndex;

    public static final String ARG_OBJECT = "ImageView index";
    private ImageView imgView;

    public static ColorFragment newInstance(int pageNum, String title, int bitmapIndex){
        ColorFragment cFrag = new ColorFragment();
        Bundle args = new Bundle();
        args.putInt("page_number", pageNum);
        args.putString("title", title);
        args.putInt("bitmap_index", bitmapIndex);
        cFrag.setArguments(args);
        return cFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        page = getArguments().getInt("page_number");
        title = getArguments().getString("title");
        bitmapIndex = getArguments().getInt("bitmap_index");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);
        Bundle args = getArguments();

        assert args != null;
        imgView = ((FinalActivity) Objects.requireNonNull(getActivity())).getImgView(args.getInt(ARG_OBJECT));



        return rootView;
    }
}

