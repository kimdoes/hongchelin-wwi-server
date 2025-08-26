package com.hongchelin.controller.signup;

import com.hongchelin.exceptions.UnauthorizedException;
import com.hongchelin.service.login.OAuth.registerOAuthMainService;
import com.hongchelin.service.login.OAuth.registerOAuthService;
import com.hongchelin.service.signup.*;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@Controller
@ControllerAdvice
@RequestMapping("/signup")
public class SignUpController {
    @Value("${spring.jwt.secret}")
    private String secret;

    private final ValidateIdService validateIdService;
    private final EmailAccessForSignUpService emailAccessForSignUpService;
    private final EmailCheckerService emailCheckerService;
    private final SignUpService signUpService;
    private final CheckNameService checkNameService;
    private final registerOAuthService registerOAuthService;
    private final registerOAuthMainService registerOAuthMainService;

    public SignUpController(ValidateIdService validateIdService,
                            EmailCheckerService emailCheckerService,
                            EmailAccessForSignUpService emailAccessForSignUpService,
                            SignUpService signUpService,
                            CheckNameService checkNameService,
                            registerOAuthService registerOAuthService,
                            registerOAuthMainService registerOAuthMainService) {
        this.validateIdService = validateIdService;
        this.emailAccessForSignUpService = emailAccessForSignUpService;
        this.emailCheckerService = emailCheckerService;
        this.signUpService = signUpService;
        this.checkNameService = checkNameService;
        this.registerOAuthService = registerOAuthService;
        this.registerOAuthMainService = registerOAuthMainService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> signUp(@RequestBody @Valid MemberRequestDTO memberRequestDTO) throws Exception {
                return signUpService.signUp(memberRequestDTO);
    }

    @GetMapping("/validateId")
    public ResponseEntity<ResponseDTO> validateId(@RequestParam("id") String id) {
        return validateIdService.ValidateIdService(id);
    }

    @PostMapping("/email")
    public ResponseEntity<ResponseDTO> emailAccess(@RequestBody Map<String, Object> request) throws Exception {
        String email = request.get("email").toString();

        if (!email.contains("@")) {
            throw new UnauthorizedException();
        }

        System.out.println(request + email);
        return emailAccessForSignUpService.EAFSS(email);
    }

    @PostMapping("/email/verify")
    public ResponseEntity<ResponseDTO> emailchecker(@RequestBody Map<String, Object> request) {
        String pwd = request.get("code").toString();

        if (pwd == null) {
            throw new UnauthorizedException();
        }

        return emailCheckerService.EmailCheckerService(pwd);
    }

    @GetMapping("/name")
    public ResponseEntity<ResponseDTO> name(@RequestParam("name") String name) {
        return checkNameService.CheckName(name);
    }

    @GetMapping("/{oauthprovider}")
    public ResponseEntity<?> oauthRegisterDBController(
            @PathVariable("oauthprovider") int oauthprovider,
            HttpServletRequest request,
            HttpServletResponse response) {

        return registerOAuthService.registerOAuthService(oauthprovider, secret, request, response);
    }

    @GetMapping("/socialAccounts")
    public ResponseEntity<ResponseDTO> socialAccounts(
            @AuthenticationPrincipal OAuth2User oauth2user,
            OAuth2AuthenticationToken oauth2request,
            HttpServletRequest request
            ) {
        return registerOAuthMainService.registerOAuth(secret, oauth2user, request, oauth2request);
    }
}