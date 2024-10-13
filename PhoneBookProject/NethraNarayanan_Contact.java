package com.example.phonebookproject;

public class Contact {
    private String name;
    private int profilePicture;
    private String phoneNumber;
    private String bio;

    private boolean isDeleted;

    public Contact(){
        name = "";
        profilePicture = 0;
        phoneNumber = "";
        bio = "";
        isDeleted = false;
    }

    public Contact(String cName, int cProfilePicture, String cPhoneNumber, String cBio){
        name = cName;
        profilePicture = cProfilePicture;
        phoneNumber = cPhoneNumber;
        bio = cBio;
        isDeleted = false;
    }

    public String getName(){
        return name;
    }

    public int getProfilePicture(){
        return profilePicture;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getBio(){
        return bio;
    }

    public String toString(){
        return name;
    }
    public boolean getIsDeleted(){
        return isDeleted;
    }
    public void setIsDeleted(boolean is){
        isDeleted = is;
    }

}
