package com.example.phonebookproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView contactList;
    ArrayList<Contact> contactArray;
    ImageView profileImage;
    TextView name;
    TextView number;
    TextView bio;

    int pos;
    String KEY_POSITION = "1234";
    String KEY_CONTACTS = "5678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = findViewById(R.id.id_listView);
        contactArray = new ArrayList<Contact>();

        pos = -1;

        ArrayList<Contact> temp = new ArrayList<Contact>();
        contactArray.add(new Contact(("Drunk-Dazed"), R.drawable.drunkdazed, "113-021-0426", "Drunk-Dazed is a song by ENHYPEN from their album BORDER:CARNIVAL. The song takes place at a chaotic party with a vampiric theme."));
        contactArray.add(new Contact(("Blessed-Cursed"), R.drawable.dimensionanswer,"113-022-0110" , "Blessed-Cursed is a song by ENHYPEN from their album DIMENSION:ANSWER. ENHYPEN also won their first music show trophy for Blessed-Cursed."));
        contactArray.add(new Contact(("Tamed-Dashed"), R.drawable.dimensiondilemma, "113-021-1012", "Tamed-Dashed is a song by ENHYPEN from their album DIMENSION:DILEMMA. This song is an upbeat pop song."));
        contactArray.add(new Contact(("Chaconne"), R.drawable.darkblood, "113-023-0522", "Chaconne is a song by ENHYPEN from their album DARK BLOOD. It's one of my favorite songs from this album."));
        contactArray.add(new Contact(("Sweet Venom"), R.drawable.orangeblood, "113-023-1117", "Sweet Venom is a song by ENHYPEN from their album ORANGE BLOOD. This song is their title track for their latest album."));
        contactArray.add(new Contact(("Queencard"), R.drawable.queencard, "050-223-0515", "Queencard is a song by (G) I-DLE from their album i feel. Queencard is a song about self-love and confidence, no matter what you look like."));
        contactArray.add(new Contact(("Love Dive"), R.drawable.lovedive, "012-121-0405", "Love Dive is a song by IVE from their album LOVE DIVE. This song has exceeded 300 million streams on Spotify."));
        contactArray.add(new Contact(("You & Me"), R.drawable.youandme, "080-622-1015", "You & Me is a single by Jennie Kim from BLACKPINK. She first performed it at the start of BLACKPINK's Born Pink world tour."));
        contactArray.add(new Contact(("Dahlia"), R.drawable.dahlia, "050-221-0111", "Dahlia is a song by (G) I-DLE from their album i burn. It was released on January 11th and has 16 million streams on Spotify."));


        if (savedInstanceState != null) {
            contactArray = (ArrayList<Contact>) savedInstanceState.getSerializable(KEY_CONTACTS);
            pos = savedInstanceState.getInt(KEY_POSITION);
        }



        //---------------------------------PORTRAIT---------------------------------------------
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            CustomAdapter customAdapter = new CustomAdapter(this, R.layout.adapter_layout, contactArray);
            contactList.setAdapter(customAdapter);

            contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                    Toast.makeText(MainActivity.this, "You have selected " + contactArray.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        //---------------------------------LANDSCAPE---------------------------------------------
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CustomAdapter customAdapter = new CustomAdapter(this, R.layout.adapter_layout, contactArray);
            contactList.setAdapter(customAdapter);

            if (pos >= 0) {
                Contact currentContact = contactArray.get(pos);
                profileImage = findViewById(R.id.id_land_profile_image);
                profileImage.setImageResource(currentContact.getProfilePicture());

                name = findViewById(R.id.id_land_name);
                name.setText(currentContact.getName());

                number = findViewById(R.id.id_land_phone_number);
                number.setText("" + currentContact.getPhoneNumber());

                bio = findViewById(R.id.id_land_bio);
                bio.setText(currentContact.getBio());

            }


            contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                    if (pos >= 0) {
                        Contact currentContact = contactArray.get(pos);
                        profileImage = findViewById(R.id.id_land_profile_image);
                        profileImage.setImageResource(currentContact.getProfilePicture());
                        name = findViewById(R.id.id_land_name);
                        name.setText(currentContact.getName());

                        number = findViewById(R.id.id_land_phone_number);
                        number.setText("" + currentContact.getPhoneNumber());

                        bio = findViewById(R.id.id_land_bio);
                        bio.setText(currentContact.getBio());
                    }
                    Toast.makeText(MainActivity.this, "You have selected " + contactArray.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CONTACTS, contactArray);
        outState.putSerializable(KEY_POSITION, pos);
    }

}