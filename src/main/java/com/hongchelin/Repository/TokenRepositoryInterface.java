package com.hongchelin.Repository;

import com.hongchelin.Domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepositoryInterface extends CrudRepository<Token, String> {
    Token findByRefreshToken(String refreshToken);
}
