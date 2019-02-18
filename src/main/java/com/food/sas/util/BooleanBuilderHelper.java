package com.food.sas.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Created by ygdxd_admin at 2019-02-18 8:10 PM
 */
public class BooleanBuilderHelper {

    private static final String PERCENT_STR = "%";

    private BooleanBuilder builder;

    private BooleanBuilderHelper() {
        this.builder = new BooleanBuilder();
    }

    public static BooleanBuilderHelper newBuilder() {
        return new BooleanBuilderHelper();
    }

    public BooleanBuilderHelper andStringLike(StringExpression expression, String param) {
        return andStringLike(expression, param, false);
    }


    public BooleanBuilderHelper andStringLike(StringExpression expression, String param, boolean left) {
        if (StringUtils.isNotEmpty(param)) {
            this.builder.and(expression.like(left ? param.concat(PERCENT_STR) : PERCENT_STR.concat(param).concat(PERCENT_STR)));
        }
        return this;
    }

    public <T extends Integer> BooleanBuilderHelper andIntegerEq(NumberPath<T> path, T param) {
        if (param != null) {
            this.builder.and(path.eq(param));
        }
        return this;
    }

    public BooleanBuilder build() {
        return this.builder;
    }
}
