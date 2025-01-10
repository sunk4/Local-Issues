package com.roman.localIssues.complaint_service.claimer;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClaimerMapper {

    ClaimerEntity toEntity(ClaimerDto claimerDto);
    ClaimerDto toDto(ClaimerEntity claimerEntity);
}
