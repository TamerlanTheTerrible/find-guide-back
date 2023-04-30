package me.timur.findguideback.entity;

import jakarta.persistence.*;
import lombok.*;
import me.timur.findguideback.model.enums.DocumentExtension;
import me.timur.findguideback.model.enums.DocumentType;

/**
 * Created by Temurbek Ismoilov on 30/04/23.
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file")
public class File extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DocumentType type;

    @Column(name = "path", nullable = false)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name = "extension", nullable = false)
    private DocumentExtension extension;

    @Column(name = "size")
    private Long size;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;
}
