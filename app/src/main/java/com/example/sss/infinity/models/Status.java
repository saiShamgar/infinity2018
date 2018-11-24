package com.example.sss.infinity.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
public class Status {
    @SerializedName("status")
    private List<String> status;
    public List<String> getStatus() {
        return status;
    }
}
