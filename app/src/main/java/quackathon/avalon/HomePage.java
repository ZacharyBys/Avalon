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
import java.util.List;

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

        Button addInfo = (Button) findViewById(R.id.addInfo);
        addInfo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                onAddInfoForm();
            }
        });

        Button updateInfo = (Button) findViewById(R.id.updateInfo);
        updateInfo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, UpdateInfoPage.class);
                startActivity(intent);
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

        final EditText editTextCity = new EditText(getApplicationContext());
        editTextCity.setHint("Enter City");
        layout.addView(editTextCity);

        final EditText editTextAddress = new EditText(getApplicationContext());
        editTextAddress.setHint("Enter Address");
        layout.addView(editTextAddress);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Geocoder coder = new Geocoder(HomePage.this);
                double longitude = 0.0;
                double latitude = 0.0;

                String key = String.valueOf(userKey.getText());
                String name= String.valueOf(editTextName.getText());
                String lastName = String.valueOf(editTextLastName.getText());
                String phoneNum = String.valueOf(editTextPhone.getText());
                String email = String.valueOf(editTextEmail.getText());
                String city = String.valueOf(editTextCity.getText());
                String address = String.valueOf(editTextAddress.getText());

                try {
                    List<Address> addresses = coder.getFromLocationName(address + city, 6);

                    if(addresses.size() == 0)
                        return;

                    Address location = addresses.get(0);
                    if(location == null)
                        location = null;
                    else{
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                addInfo(key, name, lastName, phoneNum, email,latitude,longitude);
            }
        });

        builder.show();
    }

    private void addInfo(String key, String firstName, String lastName, String phoneNum, String email, double latitude, double longitude){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference nameRef = keyRef.child("First Name");
        DatabaseReference lastNameRef = keyRef.child("Last Name");
        DatabaseReference phoneNumRef = keyRef.child("Phone Number");
        DatabaseReference emailRef = keyRef.child("Email");
        DatabaseReference latRef = keyRef.child("Latitude");
        DatabaseReference longRef = keyRef.child("Longitude");

        nameRef.setValue(firstName);
        lastNameRef.setValue(lastName);
        phoneNumRef.setValue(phoneNum);
        emailRef.setValue(email);
        latRef.setValue(Double.toString(latitude));
        longRef.setValue(Double.toString(longitude));

    }

}
