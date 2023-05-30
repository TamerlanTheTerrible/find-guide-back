package me.timur.findguideback.entity;

import jakarta.persistence.*;
import lombok.*;
import me.timur.findguideback.mapper.ListToStringConverter;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.model.enums.SearchStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 29/05/23.
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guide_search")
public class GuideSearch extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User client;

    @Column(name = "from_date", nullable = false)
    private LocalDateTime fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDateTime toDate;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "has_car", nullable = false)
    private Boolean hasCar;

    @Column(name = "comment")
    private String comment;

    @Convert(converter = ListToStringConverter.class)
    @Column(name = "guides")
    private List<Long> guides;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SearchStatus status;

    public GuideSearch(User user, GuideFilterDto filterDto, HashSet<Long> guideIds) {
        this.client = user;
        this.fromDate = filterDto.getFromDate();
        this.toDate = filterDto.getToDate();
        this.language = filterDto.getLanguage();
        this.region = filterDto.getRegion();
        this.hasCar = filterDto.getHasCar();
        this.comment = filterDto.getComment();
        this.guides = new ArrayList<>(guideIds);
    }

    @Override
    public String toString() {
        return "GuideSearch{" +
                "client=" + client +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", language='" + language + '\'' +
                ", region='" + region + '\'' +
                ", hasCar=" + hasCar +
                ", comment='" + comment + '\'' +
                ", guides=" + guides +
                ", status=" + status +
                '}';
    }
}
