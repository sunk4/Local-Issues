package com.roman.localIssues.complaint_service.complaint;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {

    ComplaintEntity toEntity (ComplaintDto complaintDto);

    ComplaintDto toDto (ComplaintEntity complaintEntity);
}

