package com.github.jgoelen.graphite;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteUDP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;


/**
 * This component manages the lifecycle of a GraphiteReporter.
 */
class GraphiteReportingManager implements DisposableBean, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private GraphiteReporter reporter;
    private GraphiteProperties props;


    public GraphiteReportingManager(MetricRegistry registry, GraphiteProperties props) {
        this.reporter = create(props, registry);
        this.props = props;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            reporter.start(props.getReportInterval(), TimeUnit.SECONDS);
            logger.debug("Started reporter");
        } catch (Exception e) {
            logger.warn("Failed to start reporter", e);
        }
    }

    @Override
    public void destroy() throws Exception {
        reporter.stop();
        logger.debug("Stopped reporter");
    }

    private GraphiteReporter create(GraphiteProperties props, MetricRegistry registry) {
        return GraphiteReporter.forRegistry(registry)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .prefixedWith(props.getPrefix() + "." + props.getSourceId())
                .build(new GraphiteUDP(props.getHost(), props.getPort()));
    }

}
