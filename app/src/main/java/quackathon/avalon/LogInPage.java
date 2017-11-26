package quackathon.avalon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zachary Bys on 2017-11-25.
 */

public class LogInPage extends AppCompatActivity {
    private boolean shouldLogin;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        ImageButton backbutton = (ImageButton) findViewById(R.id.backbutton);
        backbutton.bringToFront();
        backbutton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LogInPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.bringToFront();
        loginButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (shouldLogin) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LogInPage.this);
                    alertDialogBuilder.setTitle("Log In Successful");
                    alertDialogBuilder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(LogInPage.this, UpdateInfoPage.class);
                            intent.putExtra("key", key);
                            startActivity(intent);} });
                    alertDialogBuilder.show();

                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LogInPage.this);
                    alertDialogBuilder.setTitle("Unknown Key");
                    alertDialogBuilder.setMessage("User Key has not been found. User Key is case sensitive.");
                    alertDialogBuilder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {} });
                    alertDialogBuilder.show();
                }
            }
        });

        EditText enteredKey = (EditText)findViewById(R.id.userKey);
        enteredKey.bringToFront();
        enteredKey.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                checkId();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void checkId(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference setRef = database.getReference("people");
        shouldLogin = false;

        EditText enteredKey = (EditText)findViewById(R.id.userKey);
        final String enteredUserKey = String.valueOf(enteredKey.getText());

        setRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()){

                    String userKey = String.valueOf(child.getKey());
                    if (enteredUserKey.equals(userKey)){
                        DataSnapshot setRef = child.child("First Name");
                        if (setRef.getValue() != null) {
                            shouldLogin = true;
                            key = enteredUserKey;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
