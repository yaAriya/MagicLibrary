package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"invoker", "service", "validator", "dao", "reader", "writer", "converter"})
public class ApplicationConfig {


}
