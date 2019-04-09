package com.scrap.culqui.api.external.binlist;

import com.scrap.culqui.api.external.binlist.dto.CardInfoResultDTO;
import com.scrap.culqui.exception.CulquiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * WhateverApi.
 *
 * @author Luis Alonso Ballena Garcia
 */
@Component
public class BinListApi {

    private Logger logger = LoggerFactory.getLogger(BinListApi.class);

    private final RestTemplate restTemplate;
    private final String urlGetCardInfo;

    public BinListApi(RestTemplate restTemplate,
                      //practice case
                      @Value("${api.external.binlist.getCardInfo}") String urlGetCardInfo) {
        this.restTemplate = restTemplate;
        this.urlGetCardInfo = urlGetCardInfo;
    }

    public CardInfoResultDTO getCardInfo(String bin) {
        try {
            ResponseEntity<CardInfoResultDTO> response = restTemplate.getForEntity(this.urlGetCardInfo,
                    CardInfoResultDTO.class, bin);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("Error has ocurred when send data external service : {}", e.getMessage(), e);
            throw new CulquiException(HttpStatus.INTERNAL_SERVER_ERROR, evalHttpClientError(e));
        } catch (HttpServerErrorException e) {
            logger.error("Error has ocurred with external service : {}", e.getMessage(), e);
            throw new CulquiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error has ocurred with external service");
        }
    }

    private String evalHttpClientError(HttpClientErrorException e) {
        String message;
        if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
            message = "is not obtained card information";
        } else {
            message = "Error has ocurred when send data external service";
        }
        return message;
    }

}
