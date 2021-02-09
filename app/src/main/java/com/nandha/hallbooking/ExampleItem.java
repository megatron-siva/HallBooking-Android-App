package com.nandha.hallbooking;

public class ExampleItem {
private int mImageResource;
private String mText1;
private String mText2;
private String mText3;
private String mText4;
private String mText5;
private String mText6;
private String mText7;
private String mText8;
private String mText9;
private String mText10;
private int mImageResource1;
private int mImageResource2;
private int mImageResource3;
private int mImageResource4;

public ExampleItem(int imageResource, String text1, String text2, String text3, String text4, String text5, String text6, String text7, String text8,String text9,
                   String text10) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mText3=text3;
        mText4=text4;
        mText5=text5;
        mText6=text6;
        mText7=text7;
        mText8=text8;
        mText9=text9;
        mText10=text10;
        }

public void changeText1(String text) {
        mText1 = text;
        }

public int getImageResource() {
        return mImageResource;
        }

public String getText1() {
        return mText1;
        }

public String getText2() {
        return mText2;
        }

public String getText3() {
        return mText3;
        }

public String getText4() {
        return mText4;
        }

public String getText5() {
        return mText5;
        }

public String getText6() {
        return mText6;
        }

public String getText7() {
        return mText7;
        }

public String getText8() {
        return mText8;
        }

public String getText9() {
        return mText9;
    }

public String getText10() { return mText10;}

}