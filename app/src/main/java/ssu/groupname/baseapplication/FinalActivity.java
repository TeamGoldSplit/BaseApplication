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

        Intent intent = getIntent();
        ArrayList<int[]> colorIntArrays = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            colorIntArrays.add(intent.getIntArrayExtra("Color"+Integer.toString(i)));
        }
        ArrayList<GSColor> finalColors = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            int[] rgba = colorIntArrays.get(i);
            int r = rgba[0], g = rgba[1], b = rgba[2];
            finalColors.add(new GSColor(r, g, b));
        }




/*
        imgView = findViewById(R.id.final_color_solid);
        GSColor dispColor = new GSColor(255, 153, 51, 255);
        imgView.setImageBitmap(dispColor.getBmp());

        imgView1 = findViewById(R.id.final_color_solid1);
        GSColor dispColor1 = new GSColor(153, 255, 51, 255);
        imgView1.setImageBitmap(dispColor1.getBmp());

        imgView2 = findViewById(R.id.final_color_solid2);
        GSColor dispColor2 = new GSColor(51, 255, 153, 255);
        imgView2.setImageBitmap(dispColor2.getBmp());

        */
        views = new ImageView[20];
        views[0] = imgView;
        views[1] = imgView1;
        views[2] = imgView2;


        colorPagerAdapter = new ColorPagerAdapter(getSupportFragmentManager());
        cViewPager = findViewById(R.id.final_pager);
        cViewPager.setAdapter(colorPagerAdapter);

    }

    public ImageView getImgView(int i) {
        return views[i];
    }

}

