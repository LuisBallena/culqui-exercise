package com.scrap.culqui.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ResultDTO.
 *
 * @author Luis Alonso Ballena Garcia
 */

public class ResultDTO {

    private String token;
    private String brand;
    @JsonProperty(value = "creation_dt")
    private String creationDt;

    public ResultDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCreationDt() {
        return creationDt;
    }

    public void setCreationDt(String creationDt) {
        this.creationDt = creationDt;
    }
}
