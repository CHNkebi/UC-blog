package com.ty.domain.param;

import lombok.Data;

@Data
public class PageParams {
    private int page = 1;
    private int size = 10;

}
