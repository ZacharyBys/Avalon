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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateinfo_page);
        Bundle bundle=getIntent().getExtras();
        key = bundle.getString("key");

        Button updateName = (Button) findViewById(R.id.updateName);
        updateName.bringToFront();
        updateName.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updateName();
            }
        });

        Button updatePhone = (Button) findViewById(R.id.updatePhone);
        updatePhone.bringToFront();
        updatePhone.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updatePhone();
            }
        });

        Button updateEmail = (Button) findViewById(R.id.updateEmail);
        updateEmail.bringToFront();
        updateEmail.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updateEmail();
            }
        });

        ImageButton backbutton = (ImageButton) findViewById(R.id.backbutton);
        backbutton.bringToFront();
        backbutton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UpdateInfoPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button updateAddress = (Button) findViewById(R.id.updateAddress);
        updateAddress.bringToFront();
        updateAddress.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updateAddress();
            }
        });

        Button updateChildren = (Button) findViewById(R.id.updateChildren);
        updateChildren.bringToFront();
        updateChildren.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updateChildren();
            }
        });

        Button updateMarital = (Button) findViewById(R.id.updateMarital);
        updateMarital.bringToFront();
        updateMarital.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                updateMarital();
            }
        });
    }

    private void updateName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Name");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editTextName = new EditText(getApplicationContext());
        editTextName.setHint("First Name");
        layout.addView(editTextName);

        final EditText editTextLastName = new EditText(getApplicationContext());
        editTextLastName.setHint("Last Name");
        layout.addView(editTextLastName);

        final CheckBox changedCheck = new CheckBox(getApplicationContext());
        changedCheck.setHint("Alert Refugee Center of Change?");
        layout.addView(changedCheck);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name= String.valueOf(editTextName.getText());
                String lastName = String.valueOf(editTextLastName.getText());
                String changed = String.valueOf(changedCheck.isChecked());

                pushNewName(key, name, lastName, changed);
            }
        });

        builder.show();
    }

    private void updatePhone(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Phone Number");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editTextPhone = new EditText(getApplicationContext());
        editTextPhone.setHint("Phone Number");
        layout.addView(editTextPhone);

        final CheckBox changedCheck = new CheckBox(getApplicationContext());
        changedCheck.setHint("Alert Refugee Center of Change?");
        layout.addView(changedCheck);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String phone = String.valueOf(editTextPhone.getText());
                String changed = String.valueOf(changedCheck.isChecked());

                pushNewPhone(key, phone, changed);
            }
        });

        builder.show();
    }

    private void updateChildren(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Number of Children");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editTextPhone = new EditText(getApplicationContext());
        editTextPhone.setHint("Number of Children");
        layout.addView(editTextPhone);

        final CheckBox changedCheck = new CheckBox(getApplicationContext());
        changedCheck.setHint("Alert Refugee Center of Change?");
        layout.addView(changedCheck);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String children = String.valueOf(editTextPhone.getText());
                String changed = String.valueOf(changedCheck.isChecked());

                pushChildren(key, children, changed);
            }
        });

        builder.show();
    }

    private void updateMarital(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Marital Status");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editTextPhone = new EditText(getApplicationContext());
        editTextPhone.setHint("Marital Status");
        layout.addView(editTextPhone);

        final CheckBox changedCheck = new CheckBox(getApplicationContext());
        changedCheck.setHint("Alert Refugee Center of Change?");
        layout.addView(changedCheck);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String maritalStatus = String.valueOf(editTextPhone.getText());
                String changed = String.valueOf(changedCheck.isChecked());

                pushMarital(key, maritalStatus, changed);
            }
        });

        builder.show();
    }

    private void updateEmail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Email Address");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editTextEmail = new EditText(getApplicationContext());
        editTextEmail.setHint("Email");
        layout.addView(editTextEmail);

        final CheckBox changedCheck = new CheckBox(getApplicationContext());
        changedCheck.setHint("Alert Refugee Center of Change?");
        layout.addView(changedCheck);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String phone = String.valueOf(editTextEmail.getText());
                String changed = String.valueOf(changedCheck.isChecked());

                pushNewEmail(key, phone, changed);
            }
        });

        builder.show();
    }

    public void updateAddress(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Address");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editTextCity = new EditText(getApplicationContext());
        editTextCity.setHint("Enter City");
        layout.addView(editTextCity);

        final EditText editTextAddress = new EditText(getApplicationContext());
        editTextAddress.setHint("Enter Address");
        layout.addView(editTextAddress);

        final CheckBox changedCheck = new CheckBox(getApplicationContext());
        changedCheck.setHint("Alert Refugee Center of Change?");
        layout.addView(changedCheck);

        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Geocoder coder = new Geocoder(UpdateInfoPage.this);
                double longitude = 0.0;
                double latitude = 0.0;

                String city = String.valueOf(editTextCity.getText());
                String address = String.valueOf(editTextAddress.getText());
                String checked = String.valueOf(changedCheck.isChecked());

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

                pushNewAddress(key, Double.toString(latitude) , Double.toString(longitude), checked);
            }
        });

        builder.show();
    }

    private void pushNewName(String key, String name, String lastName, String changed){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference nameRef = keyRef.child("First Name");
        DatabaseReference lastNameRef = keyRef.child("Last Name");
        DatabaseReference changedRef = keyRef.child("Changed");

        nameRef.setValue(name);
        lastNameRef.setValue(lastName);
        changedRef.setValue(changed);
    }

    private void pushNewPhone(String key, String phone, String changed){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference phoneRef = keyRef.child("Phone Number");
        DatabaseReference changedRef = keyRef.child("Changed");

        phoneRef.setValue(phone);
        changedRef.setValue(changed);
    }

    private void pushNewEmail(String key, String email, String changed){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference emailRef = keyRef.child("Email");
        DatabaseReference changedRef = keyRef.child("Changed");

        emailRef.setValue(email);
        changedRef.setValue(changed);
    }

    private void pushNewAddress(String key, String latitude, String longitude, String changed){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference latRef = keyRef.child("Latitude");
        DatabaseReference longRef = keyRef.child("Longitude");
        DatabaseReference changedRef = keyRef.child("Changed");

        latRef.setValue(latitude);
        longRef.setValue(longitude);
        changedRef.setValue(changed);
    }

    private void pushChildren(String key, String children, String changed){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference childRef = keyRef.child("Children");
        DatabaseReference changedRef = keyRef.child("Changed");

        childRef.setValue(children);
        changedRef.setValue(changed);
    }

    private void pushMarital(String key, String marital, String changed){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference maritalRef = keyRef.child("Marital Status");
        DatabaseReference changedRef = keyRef.child("Changed");

        maritalRef.setValue(marital);
        changedRef.setValue(changed);
    }

}
