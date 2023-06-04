package me.timur.findguideback.entity;

import jakarta.persistence.*;
import lombok.*;
import me.timur.findguideback.model.dto.GuideCreateOrUpdateDto;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "guide_language",
            joinColumns = @JoinColumn(name = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<Language> languages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "guide_region",
            joinColumns = @JoinColumn(name = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"))
    private Set<Region> regions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "guide_transport",
            joinColumns = @JoinColumn(name = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "transport_id"))
    private Set<Transport> transports;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "guide_file",
            joinColumns = @JoinColumn(name = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<File> files;

    @Column(name = "description")
    private String description;

    @Column(name = "is_verified", nullable = false, columnDefinition = "boolean default false")
    private Boolean isVerified;

    @Column(name = "has_car", nullable = false, columnDefinition = "boolean default false")
    private Boolean hasCar;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "boolean default false")
    private Boolean isBlocked;

    public Guide(GuideCreateOrUpdateDto createDto, User user, Set<Language> languages, Set<Region> regions, Set<Transport> transports, Set<File> files) {
        this.user = user;
        this.languages = languages;
        this.regions = regions;
        this.files = files;
        this.description = createDto.getDescription();
        this.isVerified = false;
        this.hasCar = createDto.getHasCar();
        this.transports = transports;
        this.isActive = true;
        this.isBlocked = false;
    }

    public Set<String> getLanguageNames() {
        if (languages == null) return null;
        return languages.stream().map(Language::getEngName).collect(Collectors.toSet());
    }

    public Set<String> getRegionNames() {
        if (regions == null) return null;
        return regions.stream().map(Region::getEngName).collect(Collectors.toSet());
    }
}
