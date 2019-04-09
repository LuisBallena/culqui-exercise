package com.scrap.culqui.api;

import com.scrap.culqui.api.dto.ResultDTO;
import com.scrap.culqui.api.dto.TokenDTO;
import com.scrap.culqui.service.TokenService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TokenApi.
 *
 * @author Luis Alonso Ballena Garcia
 */
@RestController
public class TokenApi {

    private TokenService tokenService;

    public TokenApi(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/tokens")
    public ResultDTO generateToken(@RequestBody @Valid TokenDTO tokenDTO) {
        return tokenService.generate(tokenDTO);
    }


}
