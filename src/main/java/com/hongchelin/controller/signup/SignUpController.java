package com.hongchelin.controller.signup;

import com.hongchelin.Service.signup.EmailAccessForSignUpService;
import com.hongchelin.Service.signup.EmailCheckerService;
import com.hongchelin.Service.signup.ValidateIdService;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final ValidateIdService validateIdService;
    private final EmailAccessForSignUpService emailAccessForSignUpService;
    private final EmailCheckerService emailCheckerService;

    public SignUpController(ValidateIdService validateIdService, EmailCheckerService emailCheckerService, EmailAccessForSignUpService emailAccessForSignUpService) {
        this.validateIdService = validateIdService;
        this.emailAccessForSignUpService = emailAccessForSignUpService;
        this.emailCheckerService = emailCheckerService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> signUp() {
        return null;
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
    public ResponseEntity<ResponseDTO> emailchecker(@RequestParam("pwd") String pwd) {
        return emailCheckerService.EmailCheckerService(pwd);
    }
}
