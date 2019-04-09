package com.scrap.culqui.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * TokenDTO.
 *
 * @author Luis Alonso Ballena Garcia
 */
public class TokenDTO {

    @NotBlank
    @Size(min = 6 , message = "{javax.validation.constraints.Size.Min.message}")
    private String pan;
    // not valid because not use for logical operation
    @JsonProperty(value = "exp_year")
    private Integer expYear;
    // not valid because not use for logical operation
    @JsonProperty(value = "exp_month")
    private Integer expMonth;

    public TokenDTO() {
    }

    /***
        could be replaced with Lombok
    **/
    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Integer getExpYear() {
        return expYear;
    }

    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public Integer getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(Integer expMonth) {
        this.expMonth = expMonth;
    }
}
