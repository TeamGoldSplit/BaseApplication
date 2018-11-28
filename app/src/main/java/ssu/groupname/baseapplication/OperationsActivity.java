package ssu.groupname.baseapplication;

import android.content.Intent;
import android.media.VolumeShaper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OperationsActivity extends AppCompatActivity {

    private Button backButton;
    private Button computeButton;
    private String origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        Intent intent = getIntent();
        origin = intent.getStringExtra("origin");

        //backButton
        /*backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent;
                if (origin == "camera")
                    backIntent = new Intent(OperationsActivity.this, CameraActivity.class);
                else //yeah this is kinda sloppy but I don't wanna write the exception catching rn
                    backIntent = new Intent(OperationsActivity.this, FileActivity.class);
                startActivity(backIntent);
            }
        });*/

        //opsButton
        computeButton = findViewById(R.id.compute_button);
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent computeIntent = new Intent(OperationsActivity.this, FinalActivity.class);
                startActivity(computeIntent);
            }
        });
    }
}
