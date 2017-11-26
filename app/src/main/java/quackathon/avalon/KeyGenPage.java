package quackathon.avalon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by Zachary Bys on 2017-11-25.
 */


public class KeyGenPage extends AppCompatActivity {

    public Typeface ralewayReg;
    public Typeface ralewayBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keygen_page);

        ImageButton backbutton = (ImageButton) findViewById(R.id.backbutton);
        backbutton.bringToFront();
        backbutton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(KeyGenPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button addInfo = (Button) findViewById(R.id.addInfo);
        addInfo.bringToFront();
        addInfo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                onAddInfoForm();
            }
        });

        ralewayReg = Typeface.createFromAsset(getAssets(),
                "fonts/Raleway-Regular.ttf");
        ralewayBold = Typeface.createFromAsset(getAssets(),
                "fonts/Raleway-Bold.ttf");

        addInfo.setTypeface(ralewayBold);
    }


    private void showKey(String key){
        TextView KeyGen = (TextView) findViewById(R.id.keytext);
        KeyGen.setText("Your user key is: " + key + ". KEEP THIS KEY SAFE, it will be needed for you to update information");
        KeyGen.bringToFront();
        KeyGen.setTypeface(ralewayReg);
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

    public void onAddInfoForm(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Information");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextView userKey = new TextView (getApplicationContext());
        final String key = getKey();
        userKey.setText("Your User Key: " + key);
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

        final EditText editTextChildren = new EditText(getApplicationContext());
        editTextChildren.setHint("Enter Number of Children");
        layout.addView(editTextChildren);

        final EditText editTextMaritalStatus = new EditText(getApplicationContext());
        editTextMaritalStatus.setHint("Enter Marital Status");
        layout.addView(editTextMaritalStatus);
        
        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Geocoder coder = new Geocoder(KeyGenPage.this);
                double longitude = 0.0;
                double latitude = 0.0;

                String name= String.valueOf(editTextName.getText());
                String lastName = String.valueOf(editTextLastName.getText());
                String phoneNum = String.valueOf(editTextPhone.getText());
                String email = String.valueOf(editTextEmail.getText());
                String city = String.valueOf(editTextCity.getText());
                String address = String.valueOf(editTextAddress.getText());
                String children = String.valueOf(editTextChildren.getText());
                String marital = String.valueOf(editTextMaritalStatus.getText());

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

                addInfo(key, name, lastName, phoneNum, email,latitude,longitude,children,marital);
                showKey(key);
            }
        });

        builder.show();
    }

    private void addInfo(String key, String firstName, String lastName, String phoneNum, String email, double latitude, double longitude
            , String children, String marital){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference setRef = database.getReference("people");
        DatabaseReference keyRef = setRef.child(key);
        DatabaseReference nameRef = keyRef.child("First Name");
        DatabaseReference lastNameRef = keyRef.child("Last Name");
        DatabaseReference phoneNumRef = keyRef.child("Phone Number");
        DatabaseReference emailRef = keyRef.child("Email");
        DatabaseReference latRef = keyRef.child("Latitude");
        DatabaseReference longRef = keyRef.child("Longitude");
        DatabaseReference childRef = keyRef.child("Children");
        DatabaseReference maritalRef = keyRef.child("Marital Status");
        DatabaseReference changedRef = keyRef.child("Changed");

        nameRef.setValue(firstName);
        lastNameRef.setValue(lastName);
        phoneNumRef.setValue(phoneNum);
        emailRef.setValue(email);
        latRef.setValue(Double.toString(latitude));
        longRef.setValue(Double.toString(longitude));
        childRef.setValue(children);
        maritalRef.setValue(marital);
        changedRef.setValue("false");
    }
}
