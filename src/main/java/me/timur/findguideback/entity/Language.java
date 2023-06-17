package me.timur.findguideback.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "language")
public class Language {
    @Id
    @Column(name = "eng_name")
    String engName;

    @Column(name = "ru_name")
    String ruName;

    @Column(name = "uz_name")
    String uzName;

    public Language(String engName) {
        this.engName = engName;
    }
}
