package com.javahub.repository;

import com.javahub.model.JavaVersion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JavaVersionRepository extends MongoRepository<JavaVersion, String> {
    Optional<JavaVersion> findByVersionNumber(int versionNumber);
}
