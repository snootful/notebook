package com.example.notebook;

/**
 * Created by john on 11/28/15.
 */
public class Item implements java.io.Serializable{
    private long id;
    private long datetime;
    private String title;
    private String content;

    private boolean selected;

    public Item() {
        title = "";
        content = "";
    }

    public Item(long id, String title, String content, long datetime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.datetime = datetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
