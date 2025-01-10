package com.roman.localIssues.complaint_service.complaint;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {

    ComplaintEntity toEntity (ComplaintDto complaintDto);

    ComplaintDto toDto (ComplaintEntity complaintEntity);

    List<ComplaintDto> entityListToDto (List<ComplaintEntity> complaintEntities);
}

