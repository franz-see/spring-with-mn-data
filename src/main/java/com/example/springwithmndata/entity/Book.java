package com.example.springwithmndata.entity;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

@MappedEntity
@Data
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;
    private int pages;
    
}
