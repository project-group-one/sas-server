package com.food.sas.data.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 * Created by zj on 2018/12/21
 */
@Getter
@Setter
public class BaseResult<T> {

    private T data;

    private Pagination pagination;

    @Setter
    @Getter
    static class Pagination {
        private Integer current;
        private Integer pageSize;
        private Integer total;

        Pagination(Integer current, Integer pageSize, Integer total) {
            this.current = current;
            this.pageSize = pageSize;
            this.total = total;
        }
    }

    public BaseResult(T data, Page page) {
        this.data = data;
        this.pagination = new Pagination(page.getNumber() + 1, page.getSize(), Integer.valueOf(String.valueOf(page.getTotalElements())));
    }

    public BaseResult(T data) {
        this.data = data;
    }

}
