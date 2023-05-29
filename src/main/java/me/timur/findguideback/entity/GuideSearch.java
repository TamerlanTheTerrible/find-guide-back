package me.timur.findguideback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
@Table(name = "guide")
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

    @Convert
    @Column(name = "guides")
    private List<Integer> guides;


}
