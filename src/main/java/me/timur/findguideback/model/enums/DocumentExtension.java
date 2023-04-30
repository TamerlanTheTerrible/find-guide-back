package me.timur.findguideback.model.enums;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

public enum DocumentExtension {
    JPG("jpg"),
    PNG("png"),
    PDF("pdf"),
    DOC("doc"),
    DOCX("docx"),
    OTHER("other")
    ;

    public final String extension;

    DocumentExtension(String extension) {
        this.extension = extension;
    }
}
