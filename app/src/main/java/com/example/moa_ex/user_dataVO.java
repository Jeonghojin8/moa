package com.example.moa_ex;

public class user_dataVO {

    private int imgId;
    private String S_id;
    private String S_name;
    private String S_birth;
    private String S_phone;


    public user_dataVO(String S_id, String S_name, String S_birth, String S_phone) {

//        this.imgId = imgId;
        this.S_id = S_id;
        this.S_name = S_name;
        this.S_birth = S_birth;
        this.S_phone = S_phone;
    }



    public int getImgId() {
        return imgId;
    }

    public String getS_id() { return S_id; }

    public void setS_id(String S_id) {this.S_id = S_id;}

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getS_name() {
        return S_name;
    }

    public void setS_name(String S_name) {
        this.S_name = S_name;
    }

    public String getS_birth() {
        return S_birth;
    }

    public void setS_birth(String S_birth) {
        this.S_birth = S_birth;
    }

    public String getS_phone() {
        return S_phone;
    }

    public void setS_phone(String S_phone) {
        this.S_phone = S_phone;
    }

    @Override
    public String toString() {
        return "user_data{" +
                "imgId=" + imgId +
                ", S_ID'" + S_id + '\'' +
                ", S_NAME=" + S_name + '\'' +
                ", S_BIRTH'" + S_birth + '\'' +
                ", S_PHONE'" + S_phone + '\'' +
                '}';
    }
}
