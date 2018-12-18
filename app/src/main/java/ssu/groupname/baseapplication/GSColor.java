package ssu.groupname.baseapplication;

import android.graphics.Bitmap;
import android.graphics.Color;

public class GSColor {

    private int red, green, blue;

    private String hex;
    private Bitmap bmp;

    public GSColor(int r, int g, int b){
        bmp = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888);
        bmp.eraseColor(Color.rgb(r, g, b));

        red = r;
        green = g;
        blue = b;

        hex = Integer.toHexString(b);
        if(b < 16)
            hex = "0" + hex;
        hex = Integer.toHexString(g) + hex;
        if(g < 16)
            hex = "0" + hex;
        hex = Integer.toHexString(r) + hex;
        if(r < 16)
            hex = "0" + hex;

    }


    public int getColorInt(){ return Color.rgb(red, green, blue); }
    public String getHexString() {
        return hex;
    }
    public Bitmap getBmp() {
        return bmp;
    }


}
