package com.cec.doctorapp.ui.search_dialog;

import androidx.annotation.NonNull;


@SuppressWarnings({"unused", "RedundantSuppression"})
public class SearchListItem {
    public Integer id;
    public String title;
    public String subtitle;
    public String image;
    public boolean isSelected = false;


    public boolean getChecked() {
        return isSelected;
    }

    public SearchListItem(String title, Integer id) {
        this.id = id;
        this.title = title;
    }

    public SearchListItem(String title, String subtitle, Integer id) {
        this.title = title;
        this.subtitle = subtitle;
        this.id = id;
    }

    public SearchListItem(String title, String email, Integer id, String image) {
        this.title = title;
        this.subtitle = email;
        this.id = id;
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return "id::" + this.id +
                "\ttitle::" + this.title +
                "\tsubtitle::" + this.subtitle +
                "\timage::" + this.image +
                "\tisSelected::" + this.isSelected;
    }
}