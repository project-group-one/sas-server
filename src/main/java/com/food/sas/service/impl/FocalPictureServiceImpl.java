package com.food.sas.service.impl;

import com.food.sas.data.dto.FocalPictureDTO;
import com.food.sas.data.entity.FocalPicture;
import com.food.sas.data.entity.QFocalPicture;
import com.food.sas.data.repository.FocalPictureRepository;
import com.food.sas.mapper.FocalPictureMapper;
import com.food.sas.service.IFocalPictureService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-01-08 9:51 PM
 */
@Service
public class FocalPictureServiceImpl implements IFocalPictureService {

    @Autowired
    private FocalPictureRepository focalPictureRepository;

    @Autowired
    private EntityManager em;

    @Override
    public Page<FocalPictureDTO> searchFocalPicture(String name, Pageable pageable) {
        QFocalPicture qFocalPicture = QFocalPicture.focalPicture;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(name)) {
            builder.and(qFocalPicture.name.like(name));
        }
        Page<FocalPicture> page = focalPictureRepository.findAll(builder, pageable);
        return new PageImpl<>(Mappers.getMapper(FocalPictureMapper.class).fromEntitys(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    public void createFocalPicture(FocalPictureDTO dto) {
        focalPictureRepository.saveAndFlush(Mappers.getMapper(FocalPictureMapper.class).toEntity(dto));
    }

    @Override
    public void modifyFocalPicture(FocalPictureDTO dto, Integer id) {
        focalPictureRepository.findById(id).ifPresent(focalPicture -> {
            if (StringUtils.isNotEmpty(dto.getImgUrl())) {
                focalPicture.setImgUrl(dto.getImgUrl());
            }

            if (StringUtils.isNotEmpty(dto.getName())) {
                focalPicture.setName(dto.getName());
            }
            focalPictureRepository.saveAndFlush(focalPicture);
        });
    }

    @Override
    public void batchDeleteFocalPicture(Integer[] ids) {
        if (ids != null && ids.length > 0) {
            List<FocalPicture> list = new ArrayList<>();
            for (Integer id : ids) {
                FocalPicture focalPicture = new FocalPicture();
                focalPicture.setId(id);
                list.add(focalPicture);
            }
            focalPictureRepository.deleteAll(list);
        }
    }
}
