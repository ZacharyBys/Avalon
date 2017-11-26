package quackathon.avalon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class HomePage extends AppCompatActivity {

    public Typeface ralewayReg;
    public Typeface ralewayBold;
    public Typeface aleoReg;
    public Typeface aleoBold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView imageview = (ImageView) findViewById(R.id.logo);
        imageview.bringToFront();

        Button KeyGen = (Button) findViewById(R.id.keygen);
        KeyGen.bringToFront();
        KeyGen.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, KeyGenPage.class);
                startActivity(intent);
            }
        });

        Button updateInfo = (Button) findViewById(R.id.updateInfo);
        updateInfo.bringToFront();
        updateInfo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, LogInPage.class);
                startActivity(intent);
            }
        });

        ralewayReg = Typeface.createFromAsset(getAssets(),
                "fonts/Raleway-Regular.ttf");
        ralewayBold = Typeface.createFromAsset(getAssets(),
                "fonts/Raleway-Bold.ttf");
        aleoReg = Typeface.createFromAsset(getAssets(),
                "fonts/Aleo-Regular.otf");
        aleoBold = Typeface.createFromAsset(getAssets(),
                "fonts/Aleo-Bold.otf");

        updateInfo.setTypeface(ralewayBold);
        KeyGen.setTypeface(ralewayBold);

        TextView title = (TextView) findViewById(R.id.titletext);
        title.bringToFront();
        title.setTypeface(aleoReg);

    }

}
