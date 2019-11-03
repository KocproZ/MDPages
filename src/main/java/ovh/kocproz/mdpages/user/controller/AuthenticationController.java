package ovh.kocproz.mdpages.user.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ovh.kocproz.mdpages.exception.UnauthorizedException;
import ovh.kocproz.mdpages.service.UserService;
import ovh.kocproz.mdpages.user.dto.JwtAutheticationResponse;
import ovh.kocproz.mdpages.user.dto.RefreshTokenDTO;
import ovh.kocproz.mdpages.user.dto.SignInDTO;
import ovh.kocproz.mdpages.user.service.JwtTokenService;

@RestController
@RequestMapping("/rest/auth")
public class AuthenticationController {

    private JwtTokenService tokenService;
    private UserService userService;

    public AuthenticationController(JwtTokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register() {
        //TODO implement
    }

    @PostMapping("/activate")
    public void activate() {
        //TODO implement
    }

    @PostMapping("/signIn")
    public JwtAutheticationResponse signIn(@RequestParam @Validated SignInDTO signInDTO) {
        if (userService.verifyUserCredentials(signInDTO))
            return new JwtAutheticationResponse(tokenService.issueRefreshToken(userService.getUser(signInDTO.getUsername())));
        else throw new UnauthorizedException();
    }

    @PostMapping("/refresh")
    public JwtAutheticationResponse refresh(@RequestParam @Validated RefreshTokenDTO refreshTokenDTO) {
        return new JwtAutheticationResponse(tokenService.refreshRefreshToken(refreshTokenDTO.getRefreshToken()));
    }

    @PostMapping("/getAccessToken")
    public JwtAutheticationResponse getAccessToken(@RequestParam @Validated RefreshTokenDTO refreshTokenDTO) {
        return new JwtAutheticationResponse(tokenService.issueAccessToken(refreshTokenDTO.getRefreshToken()));
    }

}
