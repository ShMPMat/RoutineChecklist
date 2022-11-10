package org.tashtabash.routinechecklist;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@ComponentScan
@EnableWebMvc
public class RoutineChecklistConfiguration {
    @Bean
    public SessionFactory getSessionFactory() {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        Metadata metadata = new MetadataSources(standardServiceRegistry)
                .buildMetadata();

        return metadata.buildSessionFactory();
    }
}
