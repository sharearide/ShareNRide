package com.example.shikhajain.shareride.POJO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bunty on 10/5/2015.
 */
public class Each_User implements Parcelable{

    String Uid, Uname,Uwork,Useat,Ucar,Ufare,Utime,Usource,Udestination;

    public Each_User(String uid, String uame,String uwork,String useat,String ucar,String ufare,String utime,String usource,String udestination)
    {

        this.Uid = uid;
        this.Uname = uame;
        this.Uwork = uwork;
        this.Useat = useat;
        this.Ucar = ucar;
        this.Ufare = ufare;
        this.Utime = utime;
        this.Usource = usource;
        this.Udestination = udestination;

    }

    public String getUid() {
        return Uid;
    }
    public String getUname() {
        return Uname;
    }

    public String getUwork() {
        return Uwork;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public void setUwork(String uwork) {
        Uwork = uwork;
    }

    public void setUseat(String useat) {
        Useat = useat;
    }

    public void setUcar(String ucar) {
        Ucar = ucar;
    }

    public void setUfare(String ufare) {
        Ufare = ufare;
    }

    public void setUtime(String utime) {
        Utime = utime;
    }

    public void setUsource(String usource) {
        Usource = usource;
    }

    public void setUdestination(String udestination) {
        Udestination = udestination;
    }

    public String getUseat() {
        return Useat;

    }

    public String getUfare() {
        return Ufare;
    }

    public String getUtime() {
        return Utime;
    }

    public String getUsource() {
        return Usource;
    }

    public String getUdestination() {
        return Udestination;
    }

    public String getUcar() {
        return Ucar;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Uname);
        dest.writeString(Uwork);
        dest.writeString(Useat);
        dest.writeString(Ucar);
        dest.writeString(Ufare);
        dest.writeString(Utime);
        dest.writeString(Usource);
        dest.writeString(Udestination);


    }

    private Each_User(Parcel in){
        this.Uname = in.readString();
        this.Uwork = in.readString();
        this.Useat = in.readString();
        this.Ucar = in.readString();
        this.Ufare = in.readString();
        this.Utime = in.readString();
        this.Usource = in.readString();
        this.Udestination = in.readString();
    }

    public static final Creator<Each_User> CREATOR = new Creator<Each_User>() {

        @Override
        public Each_User createFromParcel(Parcel source) {
            return new Each_User(source);
        }

        @Override
        public Each_User[] newArray(int size) {
            return new Each_User[size];
        }
    };
}
