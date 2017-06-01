
package com.devbd.devmukul.e_notify.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectResponse {

    @SerializedName("item")
    @Expose
    private List<Item> item = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProjectResponse() {
    }

    /**
     * 
     * @param item
     */
    public ProjectResponse(List<Item> item) {
        super();
        this.item = item;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

}
