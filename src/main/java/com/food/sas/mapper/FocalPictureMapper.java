package com.food.sas.mapper;

import com.food.sas.data.dto.FocalPictureDTO;
import com.food.sas.data.entity.FocalPicture;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-01-08 10:10 PM
 */
@Mapper
public interface FocalPictureMapper {

    FocalPicture toEntity(FocalPictureDTO dto);

    FocalPictureDTO fromEntity(FocalPicture entity);

    List<FocalPictureDTO> fromEntitys(List<FocalPicture> entitys);

    List<FocalPicture> toEntitys(List<FocalPictureDTO> dtos);
}
