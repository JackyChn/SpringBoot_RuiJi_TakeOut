package com.itcast.reggle.dto;

import com.itcast.reggle.entity.Dish;
import com.itcast.reggle.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
