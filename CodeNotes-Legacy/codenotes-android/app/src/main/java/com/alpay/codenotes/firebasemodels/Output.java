package com.alpay.codenotes.firebasemodels;

public class Output {
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
