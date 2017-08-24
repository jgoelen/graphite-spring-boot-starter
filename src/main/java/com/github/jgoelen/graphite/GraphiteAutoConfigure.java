package com.github.jgoelen.graphite;

import com.codahale.metrics.MetricRegistry;
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AutoConfigureAfter(MetricsDropwizardAutoConfiguration.class)
@ConditionalOnProperty(prefix = "graphite", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(GraphiteProperties.class)
public class GraphiteAutoConfigure {

    @Bean
    public GraphiteReportingManager reportingManager(MetricRegistry metricRegistry, GraphiteProperties properties){
        return new GraphiteReportingManager(metricRegistry,properties);
    }

}
