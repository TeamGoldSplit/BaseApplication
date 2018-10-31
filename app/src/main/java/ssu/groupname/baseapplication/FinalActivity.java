package ssu.groupname.baseapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinalActivity extends AppCompatActivity {

    private Button topButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        //backButton
        topButton = findViewById(R.id.camera_button);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(FinalActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}
