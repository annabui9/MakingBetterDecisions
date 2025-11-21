package com.example.makingbetterdecisions;

public class Cell{

    private String title;
    private String details;
    private boolean expanded;

    public Cell(String title, String details){
        this.title = title;
        this.details = details;
        this.expanded = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
