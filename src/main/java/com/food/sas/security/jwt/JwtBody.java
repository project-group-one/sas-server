package com.food.sas.security.jwt;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2019-01-15 8:31 PM
 */
@Data
public class JwtBody implements Serializable {

    private String accessToken;

    private Integer userId;

    private Integer userType;

    private Long expired;

    private String refreshToken;

    public JwtBody() {
    }

    public JwtBody(Builder builder) {
        this.accessToken = builder.accessToken;
        this.userId = builder.userId;
        this.userType = builder.userType;
        this.expired = builder.expired;
        this.refreshToken = builder.refreshToken;
    }

    public static Builder newBuiler() {
        return new Builder();
    }

    public static class Builder {

        private String accessToken;

        private Integer userId;

        private Integer userType;

        private Long expired;

        private String refreshToken;

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUserType(Integer userType) {
            this.userType = userType;
            return this;
        }

        public Builder setExpired(Long expired) {
            this.expired = expired;
            return this;
        }

        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public JwtBody builde() {
            return new JwtBody(this);
        }
    }

}
