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
@Table(name = "region")
public class Region {
    @Id
    @Column(name = "en_name")
    String engName;

    @Column(name = "ru_name")
    String ruName;

    @Column(name = "uz_name")
    String uzName;

    public Region(String engName) {
        this.engName = engName;
    }
}
