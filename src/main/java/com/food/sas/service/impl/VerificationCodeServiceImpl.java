package com.food.sas.service.impl;

import com.food.sas.data.entity.VerificationCode;
import com.food.sas.data.repository.IVerificationCodeRepository;
import com.food.sas.service.IVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by ygdxd_admin at 2019-04-19 10:17 PM
 */
@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService {

    @Autowired
    private IVerificationCodeRepository verificationCodeRepository;

    @Override
    public boolean saveOrUpdateVerificationCode(VerificationCode code) {
        verificationCodeRepository.saveAndFlush(code);
        return true;
    }

    @Override
    public VerificationCode findVerificationCodeByPhone(String phone) {
        return verificationCodeRepository.findFirstByPhoneEquals(phone);
    }

}
