package com.gpt.Dto;


import com.gpt.Entity.DishEntity;
import com.gpt.Entity.DishFlavorEntity;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends DishEntity {

    private List<DishFlavorEntity> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
