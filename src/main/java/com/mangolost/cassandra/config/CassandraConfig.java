package com.mangolost.cassandra.config;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.core.cql.session.DefaultSessionFactory;

/**
 *
 */
@Configuration
public class CassandraConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConfig.class);

    @Value("${cassandra.datasource.order.contact-points:localhost}")
    private String contactPoints;

    @Value("${cassandra.datasource.order.port:9042}")
    private Integer port;

    @Value("${cassandra.datasource.order.keyspace:db_test}")
    private String keyspace;

    @Value("${cassandra.datasource.order.username:}")
    private String username;

    @Value("${cassandra.datasource.order.password:}")
    private String password;

    @Value("${cassandra.datasource.order.max-connection-pool-size:10}")
    private Integer maxConnectionPoolSize;

    @Value("${cassandra.datasource.order.core-connection-pool-size:5}")
    private Integer coreConnectionPoolSize;

    @Bean("orderCassandraDatasource")
    @Primary
    public Session orderCassandraDatasource() {

        PoolingOptions poolingOptions = new PoolingOptions();
        poolingOptions
                .setCoreConnectionsPerHost(HostDistance.LOCAL, coreConnectionPoolSize)
                .setMaxConnectionsPerHost(HostDistance.LOCAL, maxConnectionPoolSize)
                .setCoreConnectionsPerHost(HostDistance.REMOTE, coreConnectionPoolSize)
                .setMaxConnectionsPerHost(HostDistance.REMOTE, maxConnectionPoolSize);

        String[] contactPointsArr = contactPoints.split(",");

        Cluster cluster = Cluster.builder()
                .addContactPoints(contactPointsArr)
                .withPort(port)
                .withCredentials(username, password)
                .withPoolingOptions(poolingOptions)
                .build();

        Session session = cluster.connect(keyspace);

        Metadata metadata = cluster.getMetadata();
        LOGGER.info("Connected to cluster: {} ", metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            LOGGER.info("Datacenter: {}; Host: {}; Rack: {} ", host.getDatacenter(), host.getAddress(), host.getRack());
        }

        return session;
    }

    @Bean("orderCqlTemplate")
    @Primary
    public CqlTemplate orderCqlTemplate(@Qualifier("orderCassandraDatasource") Session session) {

        CqlTemplate cqlTemplate = new CqlTemplate(new DefaultSessionFactory(session));

        return cqlTemplate;

    }

}
