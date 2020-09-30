package ch.axa.meatbackend.config;

import ch.axa.meatbackend.graphql.resolver.Mutation;
import ch.axa.meatbackend.graphql.resolver.Query;
import ch.axa.meatbackend.mongo.repository.*;
import ch.axa.meatbackend.service.ApplicationService;
import ch.axa.meatbackend.service.impl.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IoCConfig {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Bean
    public ApplicationService applicationService() {
        return new ApplicationServiceImpl(applicationRepository);
    }

    @Bean
    public Query queryResolver() {
        return new Query(applicationService());
    }

    @Bean
    public Mutation mutationResolver() {
        return new Mutation(applicationService());
    }

}
