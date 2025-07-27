package com.hongchelin.controller.signup;

import com.hongchelin.Service.signup.EmailAccessForSignUpService;
import com.hongchelin.Service.signup.EmailCheckerService;
import com.hongchelin.Service.signup.SignUpService;
import com.hongchelin.Service.signup.ValidateIdService;
import com.hongchelin.dto.Request.MemberRequestDTO;
import com.hongchelin.dto.user.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final ValidateIdService validateIdService;
    private final EmailAccessForSignUpService emailAccessForSignUpService;
    private final EmailCheckerService emailCheckerService;
    private final SignUpService signUpService;

    public SignUpController(ValidateIdService validateIdService, EmailCheckerService emailCheckerService, EmailAccessForSignUpService emailAccessForSignUpService, SignUpService signUpService) {
        this.validateIdService = validateIdService;
        this.emailAccessForSignUpService = emailAccessForSignUpService;
        this.emailCheckerService = emailCheckerService;
        this.signUpService = signUpService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> signUp(@RequestBody MemberRequestDTO memberRequestDTO) throws Exception {
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
    public ResponseEntity<ResponseDTO> emailchecker(@RequestParam("pwd") String pwd) {
        return emailCheckerService.EmailCheckerService(pwd);
    }
}