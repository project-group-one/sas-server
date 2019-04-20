package com.food.sas.service;

import com.food.sas.data.entity.VerificationCode;

/**
 * @author Created by ygdxd_admin at 2019-04-19 10:07 PM
 */
public interface IVerificationCodeService {

    boolean saveOrUpdateVerificationCode(VerificationCode code);

    VerificationCode findVerificationCodeByPhone(String phone);


}
