package com.ronnachate.inventory.shared.pagination;

import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.reflect.TypeToken;

import org.modelmapper.ModelMapper;
import lombok.Data;

@Data
public class Resultset<T, DTO> {

    private int page;
    private int rows;
    private int total;
    private int totalPage;
    private List<DTO> items;

    public Resultset(ModelMapper modelMapper, int page, int rows, int total, int totalPage, List<T> items, Class<DTO> dtoClass) {
        this.page = page;
        this.rows = rows;
        this.total = total;
        this.totalPage = totalPage;
        //jave refrection, get type of DTO as object
        //need to declare type of DTO from constructor instead
        Type type = TypeToken.getParameterized(List.class, dtoClass).getType();
        this.items = modelMapper.map(items, type);
        ;
        
    }
}

