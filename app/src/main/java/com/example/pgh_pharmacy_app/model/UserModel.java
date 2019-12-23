package com.example.pgh_pharmacy_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {

    private String lastname;
    private String firstname;
    private String username;
    private String password;
    private String id;
    private String type;
    private String address;
    private String number;

    public UserModel() {
    }

    public UserModel(String username, String number){
        this.username = username;
        this.number = number;
        this.address = "No Address";
        this.password = "password";
        this.firstname = "No FirstName";
        this.lastname = "No LastName";
        this.id = "00000";
        this.type = "onetime";
    }

    public UserModel(String lastname, String firstname, String username, String password, String id, String type, String address, String number) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.password = password;
        this.id = id;
        this.type = type;
        this.address = address;
        this.number = number;
    }


    protected UserModel(Parcel in) {
        lastname = in.readString();
        firstname = in.readString();
        username = in.readString();
        password = in.readString();
        id = in.readString();
        type = in.readString();
        address = in.readString();
        number = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lastname);
        dest.writeString(firstname);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(address);
        dest.writeString(number);
    }
}
