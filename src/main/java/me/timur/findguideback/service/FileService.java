package me.timur.findguideback.service;

import me.timur.findguideback.model.dto.FileCreateDto;
import me.timur.findguideback.model.dto.FileDto;

/**
 * Created by Temurbek Ismoilov on 01/05/23.
 */

public interface FileService {
    FileDto save(FileCreateDto createDto);
}
