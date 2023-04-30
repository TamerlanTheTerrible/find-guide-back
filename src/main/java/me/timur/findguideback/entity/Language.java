package me.timur.findguideback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Entity
@Table(name = "language")
public class Language {
    @Id
    String name;
}
