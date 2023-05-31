package me.timur.findguideback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Temurbek Ismoilov on 31/05/23.
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GuideSearchListDto {
    private List<GuideSearchDto> items;
}
