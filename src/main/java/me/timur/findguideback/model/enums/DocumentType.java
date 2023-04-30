package me.timur.findguideback.model.enums;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

public enum DocumentType {
    PASSPORT("passport/id_card", "паспорт/удостоверение личности"),
    CERTIFICATE("certificate", "сертификат"),
    LICENSE("license", "лицензия"),
    PHOTO("photo", "фото"),
    DIPLOMA("diploma", "диплом"),
    OTHER("other", "другое")
    ;

    public final String engName;
    public final String ruName;

    DocumentType(String engName, String ruName) {
        this.engName = engName;
        this.ruName = ruName;
    }
}
