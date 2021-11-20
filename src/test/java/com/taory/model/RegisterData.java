package com.taory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class RegisterData {
        private String email;
        private String password;
}