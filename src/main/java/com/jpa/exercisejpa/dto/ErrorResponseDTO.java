package com.jpa.exercisejpa.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponseDTO {
    private String error;
    private List<String> detail = new ArrayList<>();
    public List<String> getDetail() {
        return detail;
    }

    public void setDetail(List<String> detail) {
        this.detail = detail;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
