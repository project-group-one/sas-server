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
import java.util.Optional;

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
    public FocalPictureDTO searchFocalPicture(Long id) {
        Optional<FocalPicture> optional = focalPictureRepository.findById(id);
        return Mappers.getMapper(FocalPictureMapper.class).fromEntity(optional.get());
    }

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
        dto.setHidden(0);
        dto.setOrdered(-1);
        FocalPicture focalPicture = FocalPictureMapper.MAPPER.toEntity(dto);
        focalPictureRepository.save(focalPicture);
    }

    @Override
    public void modifyFocalPicture(FocalPictureDTO dto, Long id) {
        focalPictureRepository.findById(id).ifPresent(focalPicture -> {
            if (StringUtils.isNotEmpty(dto.getImgUrl())) {
                focalPicture.setImgUrl(dto.getImgUrl());
            }
            if (dto.getHidden() != null) {
                focalPicture.setHidden(dto.getHidden());
            }
            if (dto.getOrdered() != null) {
                focalPicture.setOrdered(dto.getOrdered());
            }
            if (StringUtils.isNotEmpty(dto.getName())) {
                focalPicture.setName(dto.getName());
            }
            if(StringUtils.isNotEmpty(dto.getUrl())){
                focalPicture.setUrl(dto.getUrl());
            }
            focalPictureRepository.saveAndFlush(focalPicture);
        });
    }

    @Override
    public void batchDeleteFocalPicture(Long[] ids) {
        if (ids != null && ids.length > 0) {
            List<FocalPicture> list = new ArrayList<>();
            for (Long id : ids) {
                FocalPicture focalPicture = new FocalPicture();
                focalPicture.setId(id);
                list.add(focalPicture);
            }
            focalPictureRepository.deleteAll(list);
        }
    }
}
