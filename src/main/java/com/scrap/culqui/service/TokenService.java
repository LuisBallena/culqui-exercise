package com.scrap.culqui.service;

import com.scrap.culqui.api.dto.ResultDTO;
import com.scrap.culqui.api.dto.TokenDTO;
import com.scrap.culqui.api.external.binlist.BinListApi;
import com.scrap.culqui.api.external.binlist.dto.CardInfoResultDTO;
import com.scrap.culqui.util.CardConstant;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * TokenService.
 *
 * @author Luis Alonso Ballena Garcia
 */
@Service
public class TokenService {

    private Logger logger = LoggerFactory.getLogger(BinListApi.class);

    private BinListApi binListApi;

    public TokenService(BinListApi binListApi) {
        this.binListApi = binListApi;
    }

    public ResultDTO generate(TokenDTO tokenDTO) {
        String bin = getBinCode(tokenDTO.getPan());
        logger.debug("The card bin : {}", bin);
        CardInfoResultDTO cardInfoResultDTO = binListApi.getCardInfo(bin);
        return buildResult(tokenDTO, cardInfoResultDTO.getScheme());
    }

    private String getBinCode(String pan) {
        return pan.substring(BigDecimal.ZERO.intValue(), CardConstant.BIN_LENGHT);
    }

    private String buildToken(TokenDTO tokenDTO) {
        return String.format("tkn_live_%s-%d-%d", tokenDTO.getPan(), tokenDTO.getExpYear(), tokenDTO.getExpMonth());
    }

    private ResultDTO buildResult(TokenDTO tokenDTO, String scheme) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setToken(buildToken(tokenDTO));
        resultDTO.setBrand(scheme);
        //practice case
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resultDTO.setCreationDt(simpleDateFormat.format(new Date()));
        return resultDTO;
    }

}
