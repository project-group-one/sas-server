package com.food.sas.data.repository;

import com.food.sas.data.entity.VerificationCode;

/**
 * @author Created by ygdxd_admin at 2019-04-19 9:40 PM
 */
public interface IVerificationCodeRepository extends MyRepository<VerificationCode, Long> {

    VerificationCode findFirstByPhoneEquals(String phone);
}
