package com.hongchelin.controller;

import com.hongchelin.dto.user.ResponseDTO;
import com.hongchelin.exceptions.CannotFoundDbElementException;
import com.hongchelin.exceptions.CookieNotFoundExcpetion;
import com.hongchelin.exceptions.UnauthorizedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(400)
                .message(ex.getBindingResult().getFieldError().getDefaultMessage())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseDTO> httpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(405)
                .message(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<ResponseDTO> badSqlGrammarException(BadSqlGrammarException ex) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(500)
                .message(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseDTO> noResourceFoundException(NoResourceFoundException ex) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(404)
                .message(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseDTO> missingServletRequestParameterException(
            MissingServletRequestParameterException ex
    ) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(400)
                .message(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDTO> methodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex
    ) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(400)
                .message(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ResponseDTO> httpMessageConversionException(
            HttpMessageConversionException ex
    ) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(500)
                .message(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResponseDTO> emptyResultDataAccessException(
            EmptyResultDataAccessException ex
    ){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(500)
                .message(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDTO> unauthorizedException(
            UnauthorizedException ex
    ){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(401)
                .message("권한이 없습니다.")
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO> illegalArgumentException(
            IllegalArgumentException ex
    ) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(500)
                .message(ex.getLocalizedMessage())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CookieNotFoundExcpetion.class)
    public ResponseEntity<ResponseDTO> cookieNotFoundException(
            CookieNotFoundExcpetion ex
    ) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(400)
                .message("데이터가 존재하지 않습니다.")
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotFoundDbElementException.class)
    public ResponseEntity<ResponseDTO> cannotFoundDbElementException(
            CookieNotFoundExcpetion ex
    ) {
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(400)
                .message("데이터베이스 내 입력된 데이터가 존재하지 않습니다.")
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}