package me.timur.findguideback.entity;

import jakarta.persistence.*;
import lombok.*;
import me.timur.findguideback.model.dto.GuideCreateDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guide")
public class Guide extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "guide_language",
            joinColumns = @JoinColumn(name = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<Language> languages;

    @ManyToMany
    @JoinTable(name = "guide_region",
            joinColumns = @JoinColumn(name = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    private Set<Region> regions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "guide_file",
            joinColumns = @JoinColumn(name = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    private Set<File> files;

    @Column(name = "description")
    private String description;

    @Column(name = "is_verified", nullable = false, columnDefinition = "boolean default false")
    private Boolean isVerified;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "boolean default false")
    private Boolean isBlocked;

    public Guide(GuideCreateDto createDto, Set<Language> languages, Set<Region> regions, Set<File> files) {
        this.user = new User(createDto.getUser());
        this.languages = languages;
        this.regions = regions;
        this.files = files;
        this.description = createDto.getDescription();
        this.isVerified = false;
        this.isActive = true;
        this.isBlocked = false;
    }

    public Set<String> getLanguageNames() {
        return languages.stream().map(Language::getEngName).collect(Collectors.toSet());
    }

    public Set<String> getRegionNames() {
        return regions.stream().map(Region::getEngName).collect(Collectors.toSet());
    }
}
