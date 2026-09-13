package com.example.wechatcontact.model;

/**
 * Generic list item used in RecyclerView
 * It can represent entry, header or contact
 */
public class ListItem {

    public static final int TYPE_ENTRY = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_CONTACT = 2;

    private int type;
    private String text;
    private String letter;

    public ListItem(int type,String text,String letter){
        this.type = type;
        this.text = text;
        this.letter = letter;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getLetter() {
        return letter;
    }
}