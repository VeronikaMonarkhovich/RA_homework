package com.taory.specification;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.taory.filters.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.with;

public class ReqresSpecs {
       public static RequestSpecification request = with()
                .filter(customLogFilter().withCustomTemplates())
                .log().all()
                .contentType(ContentType.JSON);
    }