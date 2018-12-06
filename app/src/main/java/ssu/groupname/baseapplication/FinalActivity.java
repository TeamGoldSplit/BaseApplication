package ssu.groupname.baseapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.strictmode.Violation;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FinalActivity extends FragmentActivity {

    private Button topButton;
    ColorPagerAdapter colorPagerAdapter;
    private ViewPager cViewPager;
    private ImageView imgView;
    private ImageView imgView1;
    private ImageView imgView2;

    private ImageView[] views;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        //topButton FIX THIS FIX THIS FIX THIS
        topButton = findViewById(R.id.top_button);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(FinalActivity.this, MainActivity.class);
                backIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backIntent);
            }
        });

//        Intent intent = getIntent();
////        ArrayList<int[]> colorIntArrays = new ArrayList<>();
////        for(int i = 0; i < 6; i++){
////            colorIntArrays.add(intent.getIntArrayExtra("Color"+Integer.toString(i)));
////        }
////        ArrayList<GSColor> finalColors = new ArrayList<>();
////        for(int i = 0; i < 6; i++){
////            int[] rgba = colorIntArrays.get(i);
////            int r = rgba[0], g = rgba[1], b = rgba[2];
////            finalColors.add(new GSColor(r, g, b));
////        }
        String[] bmpFileNames = new String[6];
        Bitmap bmp = null;
        for(int i = 0; i < 6; i++){
            String filename = getIntent().getStringExtra("color_image" + Integer.toString(i));
            bmpFileNames[i] = filename;
        }
        //bmpFileNames[0]: most prominent
        //bmpFileNames[0..5]: 6 most prominent


        colorPagerAdapter = new ColorPagerAdapter(getSupportFragmentManager());
        cViewPager = findViewById(R.id.final_pager);
        cViewPager.setAdapter(colorPagerAdapter);

    }

    public ImageView getImgView(int i) {
        return views[i];
    }

}

