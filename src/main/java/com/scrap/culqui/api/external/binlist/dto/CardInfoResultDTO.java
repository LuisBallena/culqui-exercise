package com.scrap.culqui.api.external.binlist.dto;

/**
 * CardInfoResultDTO.
 *
 * @author Luis Alonso Ballena Garcia
 */

public class CardInfoResultDTO {
    private String scheme;

    public CardInfoResultDTO() {
    }

    /***
     could be replaced with Lombok
     **/
    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
