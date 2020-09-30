package ch.axa.meatbackend.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    private static final String MONGO_URI = "mongodb://%s:%s/%s";

    @Value("${DB_HOST}")
    private String dbHost;
    @Value("${DB_PORT}")
    private String dbPort;
    @Value("${DB_DBNAME}")
    private String dbname;

    @Override
    public MongoClient mongoClient() {
        String uri = String.format(MONGO_URI, dbHost, dbPort, dbname);

        return new MongoClient(new MongoClientURI(uri,
                MongoClientOptions.builder()
                        .maxConnectionIdleTime(60000 * 3)
                        .socketTimeout(60000)
                        .connectTimeout(15000)));
    }

    @Override
    protected String getDatabaseName() {
        return dbname;
    }
}
