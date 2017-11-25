package quackathon.avalon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button KeyGen = (Button) findViewById(R.id.keygen);
        KeyGen.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, KeyGenPage.class);
                startActivity(intent);
            }
        });

        Button updateInfo = (Button) findViewById(R.id.updateInfo);
        updateInfo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                onAddInfoForm();
            }
        });

    }

    public void onAddInfoForm(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Information");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText userKey = new EditText (getApplicationContext());
        userKey.setHint("User Key");
        layout.addView(userKey);

        final EditText editTextName = new EditText(getApplicationContext());
        editTextName.setHint("First Name");
        layout.addView(editTextName);

        final EditText editTextLastName = new EditText(getApplicationContext());
        editTextLastName.setHint("Last Name");
        layout.addView(editTextLastName);

        final EditText editTextPhone = new EditText(getApplicationContext());
        editTextPhone.setHint("Phone Number");
        layout.addView(editTextPhone);

        final EditText editTextEmail = new EditText(getApplicationContext());
        editTextEmail.setHint("Email");
        layout.addView(editTextEmail);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String key = String.valueOf(userKey.getText());
                String name= String.valueOf(editTextName.getText());
                String lastName = String.valueOf(editTextLastName.getText());
                String phoneNum = String.valueOf(editTextPhone.getText());
                String email = String.valueOf(editTextEmail.getText());

                updateInfo(key, name, lastName, phoneNum, email);
            }
        });

        builder.show();
    }

    private void updateInfo(String key, String firstName, String lastName, String phoneNum, String email){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference nameRef = keyRef.child("First Name");
        DatabaseReference lastNameRef = keyRef.child("Last Name");
        DatabaseReference phoneNumRef = keyRef.child("Phone Number");
        DatabaseReference emailRef = keyRef.child("Email");

        nameRef.setValue(firstName);
        lastNameRef.setValue(lastName);
        phoneNumRef.setValue(phoneNum);
        emailRef.setValue(email);

    }

}
