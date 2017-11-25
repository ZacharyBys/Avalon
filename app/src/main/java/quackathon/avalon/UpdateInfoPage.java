package quackathon.avalon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Zachary Bys on 2017-11-25.
 */

public class UpdateInfoPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateinfo_page);

        Button updateName = (Button) findViewById(R.id.updateName);
        updateName.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updateName();
            }
        });

        Button updatePhone = (Button) findViewById(R.id.updatePhone);
        updatePhone.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updatePhone();
            }
        });

        Button updateEmail = (Button) findViewById(R.id.updateEmail);
        updateEmail.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updateEmail();
            }
        });

        Button updateAddress = (Button) findViewById(R.id.updateAddress);
        updateAddress.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updateAddress();
            }
        });
    }

    private void updateName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Name");

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

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String key = String.valueOf(userKey.getText());
                String name= String.valueOf(editTextName.getText());
                String lastName = String.valueOf(editTextLastName.getText());

                pushNewName(key, name, lastName);
            }
        });

        builder.show();
    }

    private void updatePhone(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Phone Number");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText userKey = new EditText (getApplicationContext());
        userKey.setHint("User Key");
        layout.addView(userKey);

        final EditText editTextPhone = new EditText(getApplicationContext());
        editTextPhone.setHint("Phone Number");
        layout.addView(editTextPhone);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String key = String.valueOf(userKey.getText());
                String phone = String.valueOf(editTextPhone.getText());

                pushNewPhone(key, phone);
            }
        });

        builder.show();
    }

    private void updateEmail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Email Address");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText userKey = new EditText (getApplicationContext());
        userKey.setHint("User Key");
        layout.addView(userKey);

        final EditText editTextEmail = new EditText(getApplicationContext());
        editTextEmail.setHint("Email");
        layout.addView(editTextEmail);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String key = String.valueOf(userKey.getText());
                String phone = String.valueOf(editTextEmail.getText());

                pushNewEmail(key, phone);
            }
        });

        builder.show();
    }

    public void updateAddress(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Address");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText userKey = new EditText (getApplicationContext());
        userKey.setHint("User Key");
        layout.addView(userKey);

        final EditText editTextCity = new EditText(getApplicationContext());
        editTextCity.setHint("Enter City");
        layout.addView(editTextCity);

        final EditText editTextAddress = new EditText(getApplicationContext());
        editTextAddress.setHint("Enter Address");
        layout.addView(editTextAddress);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Geocoder coder = new Geocoder(UpdateInfoPage.this);
                double longitude = 0.0;
                double latitude = 0.0;

                String key = String.valueOf(userKey.getText());
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

                pushNewAddress(key, Double.toString(latitude) , Double.toString(longitude));
            }
        });

        builder.show();
    }

    private void pushNewName(String key, String name, String lastName){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference nameRef = keyRef.child("First Name");
        DatabaseReference lastNameRef = keyRef.child("Last Name");

        nameRef.setValue(name);
        lastNameRef.setValue(lastName);
    }

    private void pushNewPhone(String key, String phone){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference phoneRef = keyRef.child("Phone Number");

        phoneRef.setValue(phone);
    }

    private void pushNewEmail(String key, String email){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference emailRef = keyRef.child("Email");

        emailRef.setValue(email);
    }

    private void pushNewAddress(String key, String latitude, String longitude){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference latRef = keyRef.child("Latitude");
        DatabaseReference longRef = keyRef.child("Longitude");

        latRef.setValue(latitude);
        longRef.setValue(longitude);
    }
}
