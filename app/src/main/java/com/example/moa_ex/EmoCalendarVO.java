package com.example.moa_ex;

public class EmoCalendarVO {

    private String date;
    private String emotion;
    private int emoResId;

    public EmoCalendarVO(String date, String emotion, int emoResId) {
        this.date = date;
        this.emotion = emotion;
        this.emoResId = emoResId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public int getEmoResId() {
        return emoResId;
    }

    public void setEmoResId(int emoResId) {
        this.emoResId = emoResId;
    }

    @Override
    public String toString() {
        return "EmoCalendarVO{" +
                "date='" + date + '\'' +
                ", emotion='" + emotion + '\'' +
                ", emoResId=" + emoResId +
                '}';
    }
}
