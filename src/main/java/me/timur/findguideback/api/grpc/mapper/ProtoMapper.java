package me.timur.findguideback.api.grpc.mapper;

import com.proto.ProtoGuideDto;
import com.proto.ProtoGuideDtoList;
import com.proto.ProtoUserDto;
import me.timur.findguideback.model.dto.GuideDto;
import me.timur.findguideback.model.dto.SearchResultDto;
import me.timur.findguideback.model.dto.UserDto;
import me.timur.findguideback.util.LocalDateTimeUtil;
import me.timur.findguideback.util.StringUtil;

/**
 * Created by Temurbek Ismoilov on 30/05/23.
 */

public class ProtoMapper {

    public static ProtoUserDto toProtoUserDto(UserDto userDto) {
        return ProtoUserDto.newBuilder()
                .setId(userDto.getId())
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setTelegramUsername(userDto.getTelegramUsername())
                .setTelegramId(userDto.getTelegramId())
                .setPhoneNumbers(userDto.getPhoneNumbers() == null ? "" : String.join(",", userDto.getPhoneNumbers()))
                .setIsActive(userDto.getIsActive())
                .setIsBlocked(userDto.getIsBlocked())
                .setDateCreated(LocalDateTimeUtil.toString(userDto.getDateCreated()))
                .setDateUpdated(LocalDateTimeUtil.toString(userDto.getDateUpdated()))
                .build();
    }

    public static ProtoGuideDtoList toProtoGuideDtoList(SearchResultDto<GuideDto> result) {
        return ProtoGuideDtoList.newBuilder()
                .addAllItems(
                        result.getResultList().stream().map(ProtoMapper::toProtoGuideDto).toList()
                )
                .setTotalCount(result.getCount())
                .build();
    }

    public static ProtoGuideDto toProtoGuideDto(GuideDto guideDto) {
        return ProtoGuideDto.newBuilder()
                .setId(guideDto.getId())
                .setUser(
                        ProtoUserDto.newBuilder()
                                .setId(guideDto.getUser().getId())
                                .setFirstName(guideDto.getUser().getFirstName())
                                .setLastName(guideDto.getUser().getLastName())
                                .setBirthDate(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getBirthDate()))
                                .setDateCreated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateCreated()))
                                .setDateUpdated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateUpdated()))
                                .setPhoneNumbers(StringUtil.join(guideDto.getUser().getPhoneNumbers()))
                                .setTelegramUsername(guideDto.getUser().getTelegramUsername())
                                .setTelegramId(guideDto.getUser().getTelegramId())
                                .setIsActive(guideDto.getUser().getIsActive())
                                .setIsBlocked(guideDto.getUser().getIsBlocked())
                                .setDateCreated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateCreated()))
                                .setDateUpdated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateUpdated()))
                                .build()
                )
                .setLanguageNames(StringUtil.join(guideDto.getLanguageNames()))
                .setRegionNames(StringUtil.join(guideDto.getRegionNames()))
                .setFiles(guideDto.getFiles() == null ? "" : StringUtil.join(guideDto.getFiles().stream().map(f -> String.valueOf(f.getId())).toList()))
                .setDescription(guideDto.getDescription())
                .setIsVerified(guideDto.getIsVerified())
                .setHasCar(guideDto.getHasCar())
                .setIsActive(guideDto.getIsActive())
                .setIsBlocked(guideDto.getIsBlocked())
                .setDateCreated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateCreated()))
                .setDateUpdated(LocalDateTimeUtil.toStringOrBlankIfNull(guideDto.getUser().getDateUpdated()))
                .build();
    }
}
