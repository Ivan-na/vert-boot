/**  
 * @Title: Configurations.java
 * @Package: com.moma.vertxboot.beans
 * @author: Ivan
 * @date: Jun 12, 2017 2:51:05 PM
 * @version: V1.0  
 */

package com.moma.vertxboot.beans;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>Company: itic</p>
 * 
 * @author: Ivan
 * @date: Jun 12, 2017 2:51:05 PM
 * @version: V1.0
 */

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class ConfigurationsDB {
    /**
     * Creates a datasource using an in-memory embedded database
     * 
     * @return An instance of {@link DataSource} representing a connection pool
     *         to the database
     */
    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }

    /**
     * Creates a JPA {@link EntityManagerFactory} for use in Spring Data JPA
     * 
     * @return An instance of {@link EntityManagerFactory}
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.moma.vertxboot.data.entity");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    /**
     * Sets up transaction management for Spring Data JPA so that database
     * operations are transactional
     * 
     * @param emf The {@link javax.persistence.EntityManagerFactory} created
     *            above
     * @return An instance of {@link PlatformTransactionManager} based on JPA
     */
    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }

}
