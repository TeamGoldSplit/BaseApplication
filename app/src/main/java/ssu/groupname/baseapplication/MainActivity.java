package ssu.groupname.baseapplication;

import android.content.Intent;
import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button cameraButton;
    private Button fileButton;
    //private Button settingsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cameraButton
        cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent= new Intent(MainActivity.this, CameraActivity.class);
                startActivity(cameraIntent);
            }
        });

        //fileButton
        fileButton = findViewById(R.id.file_button);
        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileIntent = new Intent(MainActivity.this, FileActivity.class);
                startActivity(fileIntent);
            }
        });
    }
}
