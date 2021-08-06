package com.coding.challenge.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Challenge2Dto {
    @JsonProperty("k")
    private String key;

    @JsonProperty("list")
    private List<String> stringList;
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }


}
