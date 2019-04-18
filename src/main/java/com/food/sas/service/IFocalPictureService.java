package com.food.sas.service;

import com.food.sas.data.dto.FocalPictureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Created by ygdxd_admin at 2019-01-08 9:47 PM
 */
public interface IFocalPictureService {

    Page<FocalPictureDTO> searchFocalPicture(String name, Pageable pageable);

    void createFocalPicture(FocalPictureDTO dto);

    void modifyFocalPicture(FocalPictureDTO dto, Long id);

    void batchDeleteFocalPicture(Long[] ids);
}
