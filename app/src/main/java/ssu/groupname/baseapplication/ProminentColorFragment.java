package ssu.groupname.baseapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProminentColorFragment extends Fragment {

    private String title;
    private int page;
    private String bmpFile;

    private String hex0;

    String[] hexCodes;
    private Bitmap bmp;

    public static final String ARG_OBJECT = "ImageView index";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.most_prominent_color_frag, container, false);
        Bundle args = getArguments();

        assert args != null;

        ImageView imgView = (ImageView) rootView.findViewById(R.id.single_color);
        imgView.setImageBitmap(bmp);

        TextView textView = (TextView) rootView.findViewById(R.id.prominent_hex);
        textView.setText(hex0);

        return rootView;
    }

    public static ProminentColorFragment newInstance(int pageNum, String title, String bmpFileName){
        ProminentColorFragment cFrag = new ProminentColorFragment();
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
        try {
            hexCodes = readFromFile(getActivity());
        } catch (Exception e){
            e.printStackTrace();
        }
        hex0 = hexCodes[0];

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
            Log.e("PromColorFrag", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("PromColorFrag", "Can not read file: " + e.toString());
        }

        return ret;
    }

}

