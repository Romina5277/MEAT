package ch.axa.meatbackend.mongo.repository;

import ch.axa.meatbackend.mongo.model.Application;
import ch.axa.meatbackend.mongo.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    Optional<Application> findByCode(String code);
    Optional<Application> findById(String id);
    long deleteApplicationById(String id);

}
