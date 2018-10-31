package ssu.groupname.baseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CameraActivity extends AppCompatActivity {

    private Button backButton;
    private Button opsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //backButton
        backButton = findViewById(R.id.camera_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(CameraActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });

        //opsButton
        opsButton = findViewById(R.id.ops_button);
        opsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opsIntent = new Intent(CameraActivity.this, OperationsActivity.class);
                opsIntent.putExtra("origin", "camera");
                startActivity(opsIntent);
            }
        });
    }

}
