package com.gpt.Dto;

import com.gpt.Entity.SetmealDishEntity;
import com.gpt.Entity.SetmealEntity;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends SetmealEntity {

    private List<SetmealDishEntity> setmealDishes;

    private String categoryName;
}
