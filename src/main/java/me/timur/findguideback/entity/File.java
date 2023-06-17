package me.timur.findguideback.entity;

import lombok.*;
import me.timur.findguideback.model.dto.FileCreateDto;
import me.timur.findguideback.model.enums.DocumentExtension;
import me.timur.findguideback.model.enums.DocumentType;

import javax.persistence.*;

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
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted;

    public File(FileCreateDto createDto) {
        if (createDto == null) {
            return;
        }
        this.type = createDto.getType();
        this.path = createDto.getPath();
        this.extension = createDto.getExtension();
        this.size = createDto.getSize();
        this.isDeleted = false;
    }
}
