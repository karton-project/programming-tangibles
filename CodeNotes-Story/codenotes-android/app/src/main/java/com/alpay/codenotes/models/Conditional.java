package com.alpay.codenotes.models;

import com.orm.SugarRecord;

public class Conditional extends SugarRecord {
    private String conditionalText;
    private int value;

    public Conditional(String conditionalText, int value) {
        this.conditionalText = conditionalText;
        this.value = value;
    }

    public Conditional() {

    }

    public String getConditionalText() {
        return conditionalText;
    }

    public int getValue() {
        return value;
    }

    public void setConditionalText(String conditionalText) {
        this.conditionalText = conditionalText;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
