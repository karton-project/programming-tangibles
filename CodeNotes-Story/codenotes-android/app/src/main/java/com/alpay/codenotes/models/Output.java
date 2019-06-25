package com.alpay.codenotes.models;

import com.orm.SugarRecord;

public class Output extends SugarRecord {
    private String outputText;
    private String encodedImage;

    public Output(String outputText, String encodedImage) {
        this.outputText = outputText;
        this.encodedImage = encodedImage;
    }

    public Output(){

    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}
