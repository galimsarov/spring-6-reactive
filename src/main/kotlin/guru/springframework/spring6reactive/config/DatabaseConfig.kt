package guru.springframework.spring6reactive.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
@Suppress("unused")
class DatabaseConfig {
    @Value("classpath:/schema.sql")
    private lateinit var resource: Resource

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setDatabasePopulator(ResourceDatabasePopulator(resource))
        }
        return initializer
    }

}