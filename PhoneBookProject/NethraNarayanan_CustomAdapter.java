package com.example.phonebookproject;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Contact> {
    Context mainContext;
    int adapterXML;
    List<Contact> contacts;



    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        mainContext = context;
        adapterXML = resource;
        contacts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        ImageView contactPhoto;
        TextView name;
        TextView number;
        TextView delete;

        LayoutInflater layoutInflater = LayoutInflater.from(mainContext);
        View view = layoutInflater.inflate(adapterXML, null);

        contactPhoto = view.findViewById(R.id.id_contact_image);
        name = view.findViewById(R.id.id_contact_name);

        number = view.findViewById(R.id.id_contact_number);

        contactPhoto.setImageResource(contacts.get(position).getProfilePicture());

        name.setText(contacts.get(position).getName());
        number.setText(contacts.get(position).getPhoneNumber());

        delete = view.findViewById(R.id.id_delete_x);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts.get(position).setIsDeleted(true);
                contacts.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;

    }


}
