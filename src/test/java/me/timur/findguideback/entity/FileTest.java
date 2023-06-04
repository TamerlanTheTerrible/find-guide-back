package me.timur.findguideback.entity;

import me.timur.findguideback.model.dto.FileCreateDto;
import me.timur.findguideback.model.enums.DocumentExtension;
import me.timur.findguideback.model.enums.DocumentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FileTest {

    @Test
    public void testFileConstructorWithValidDto() {
        FileCreateDto createDto = new FileCreateDto();
        createDto.setType(DocumentType.DIPLOMA);
        createDto.setPath("/path/to/file.pdf");
        createDto.setExtension(DocumentExtension.PDF);
        createDto.setSize(1024L);

        File file = new File(createDto);

        assertEquals(createDto.getType(), file.getType());
        assertEquals(createDto.getPath(), file.getPath());
        assertEquals(createDto.getExtension(), file.getExtension());
        assertEquals(createDto.getSize(), file.getSize());
        assertNull(file.getGuide()); // Guide is not set in the constructor
        assertEquals(false, file.getIsDeleted());
    }

    @Test
    public void testFileConstructorWithNullDto() {
        File file = new File(null);

        assertNull(file.getType());
        assertNull(file.getPath());
        assertNull(file.getExtension());
        assertNull(file.getSize());
        assertNull(file.getGuide());
        assertNull(file.getIsDeleted());
    }
}
