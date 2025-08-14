package com.hongchelin.controller.signup;

import com.hongchelin.service.signup.*;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ControllerAdvice
@RequestMapping("/signup")
public class SignUpController {
    private final ValidateIdService validateIdService;
    private final EmailAccessForSignUpService emailAccessForSignUpService;
    private final EmailCheckerService emailCheckerService;
    private final SignUpService signUpService;
    private final CheckNameService checkNameService;

    public SignUpController(ValidateIdService validateIdService,
                            EmailCheckerService emailCheckerService,
                            EmailAccessForSignUpService emailAccessForSignUpService,
                            SignUpService signUpService,
                            CheckNameService checkNameService) {
        this.validateIdService = validateIdService;
        this.emailAccessForSignUpService = emailAccessForSignUpService;
        this.emailCheckerService = emailCheckerService;
        this.signUpService = signUpService;
        this.checkNameService = checkNameService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> signUp(@RequestBody @Valid MemberRequestDTO memberRequestDTO) throws Exception {
                return signUpService.signUp(memberRequestDTO);
    }

    @GetMapping("/validateId")
    public ResponseEntity<ResponseDTO> validateId(@RequestParam("id") String id) {
        return validateIdService.ValidateIdService(id);
    }

    @GetMapping("/email")
    public ResponseEntity<ResponseDTO> emailAccess(@RequestParam("email") String email) throws Exception {
        return emailAccessForSignUpService.EAFSS(email);
    }

    @GetMapping("/email/check")
    public ResponseEntity<ResponseDTO> emailchecker(@RequestParam("pwd") Integer pwd) {
        return emailCheckerService.EmailCheckerService(pwd);
    }

    @GetMapping("/name")
    public ResponseEntity<ResponseDTO> name(@RequestParam("name") String name) {
        return checkNameService.CheckName(name);
    }
}