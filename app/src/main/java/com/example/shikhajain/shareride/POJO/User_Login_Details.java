package com.example.shikhajain.shareride.POJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bunty on 11/1/2015.
 */
public class User_Login_Details implements Parcelable {

    int Uid;
    String Uname, Unumber, Uimage, Uemail, Uaddress, Ucompany;

    public User_Login_Details(int id, String name, String number, String image, String email, String address, String company) {
        this.Uid = id;
        this.Uname = name;
        this.Unumber = number;
        this.Uimage = image;
        this.Uemail = email;
        this.Uaddress = address;
        this.Ucompany = company;

    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getUnumber() {
        return Unumber;
    }

    public void setUnumber(String unumber) {
        Unumber = unumber;
    }

    public String getUimage() {
        return Uimage;
    }

    public void setUimage(String uimage) {
        Uimage = uimage;
    }

    public String getUemail() {
        return Uemail;
    }

    public void setUemail(String uemail) {
        Uemail = uemail;
    }

    public String getUaddress() {
        return Uaddress;
    }

    public void setUaddress(String uaddress) {
        Uaddress = uaddress;
    }

    public String getUcompany() {
        return Ucompany;
    }

    public void setUcompany(String ucompany) {
        Ucompany = ucompany;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    private User_Login_Details(Parcel in) {
        this.Uid = in.readInt();
        this.Uname = in.readString();
        this.Unumber =in.readString();
        this.Uimage = in.readString();
        this.Uemail = in.readString();
        this.Uaddress = in.readString();
        this.Ucompany = in.readString();

    }

    public static final Creator<User_Login_Details> CREATOR = new Creator<User_Login_Details>() {

        @Override
        public User_Login_Details createFromParcel(Parcel source) {
            return new User_Login_Details(source);
        }

        @Override
        public User_Login_Details[] newArray(int size) {
            return new User_Login_Details[size];
        }
    };
}
