package com.basic.mapper;

import com.basic.dto.InformationDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InformationMapper {

    void addInformationDTO(InformationDTO informationDTO);
}
