package com.taory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class UserWithJob {
        private String name;
        private String job;
    }