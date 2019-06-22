package com.abror.diplom.data;

public class Presentation {

    private String fileName;
    private String title;

    public Presentation(String fileName, String title) {
        this.fileName = fileName;
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTitle() {
        return title;
    }
}
