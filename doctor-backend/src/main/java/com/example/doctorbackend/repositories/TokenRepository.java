package com.example.doctorbackend.repositories;

import com.example.doctorbackend.entities.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public  interface TokenRepository extends MongoRepository<Token, String> {

    @Query(value = """
          {'user.id': ?0, $or: [{'expired': false}, {'revoked': false}]}
          """)
    List<Token> findAllValidTokenByUser(String id);

    Optional<Token> findByToken(String token);
}