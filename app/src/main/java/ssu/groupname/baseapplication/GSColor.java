package ssu.groupname.baseapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class GSColor {


    public GSColor(int r, int g, int b, int a){
        bmp = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888);
        bmp.eraseColor(Color.argb(a, r, g, b));

        hex = Integer.toHexString(r);
        hex += Integer.toHexString(g);
        hex += Integer.toHexString(b);
    }

    public double getAlpha() {
        return color.alpha();
    }

    public int getColorInt(){
        return color.toArgb();
    }
    public String getHexString() {
        return hex;
    }
    public Bitmap getBmp() {
        return bmp;
    }

    private Color color;
    private String hex;
    private Bitmap bmp;
}
