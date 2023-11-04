package com.ticketsystem.model.dto;

import java.util.List;

public class ListDTO {
    private List<?> data;
    private Long total;

    public List<?> getData() {
        return data;
    }

    public Long getTotal() {
        return total;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
