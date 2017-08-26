package test.dependencies.tsuyoyo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterRootActivity;
import test.dependencies.tsuyoyo.ui.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(v -> {
            startActivity(new Intent(this, EventRegisterRootActivity.class));
        });
    }

}
