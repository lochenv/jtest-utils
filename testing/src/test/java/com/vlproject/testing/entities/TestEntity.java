package com.vlproject.testing.entities;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TestEntity {

    @NotNull
    private String name;

    @Min(0)
    @Max(150)
    private Integer age;

    @Pattern(regexp = ".*@.*")
    private String eMail;
}
