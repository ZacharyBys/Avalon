package quackathon.avalon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by Zachary Bys on 2017-11-25.
 */

public class KeyGenPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keygen_page);

        Button KeyGen = (Button) findViewById(R.id.generatekey);
        KeyGen.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                publishKey(generateKey());
            }
        });

        Button backbutton = (Button) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(KeyGenPage.this, HomePage.class);
                startActivity(intent);
            }
        });
    }


    private String generateKey(){
        TextView KeyGen = (TextView) findViewById(R.id.keytext);
        String key = getKey();
        KeyGen.setText(key);
        return key;
    }

    private String getKey(){
        Random rand = new Random();
        String sample = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder stringKey = new StringBuilder();
        while(stringKey.length() <= 6){
            char newChar = sample.charAt(rand.nextInt(62) + 1);
            stringKey.append(newChar);
        }
        return stringKey.toString();
    }

    private void publishKey(String key){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference setRef = database.getReference("people");
        DatabaseReference setSubRef = setRef.child(key);

        setSubRef.setValue("key");
    }
}
