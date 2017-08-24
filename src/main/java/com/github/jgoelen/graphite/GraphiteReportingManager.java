package com.github.jgoelen.graphite;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;


/**
 * This component manages the lifecycle of a GraphiteReporter.
 */
public class GraphiteReportingManager implements DisposableBean, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(GraphiteReportingManager.class);


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

    protected GraphiteReporter create(GraphiteProperties props, MetricRegistry registry) {
        return GraphiteReporter.forRegistry(registry)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .filter(props.getMetricFilter())
            .prefixedWith(props.getPrefix())
            .build(createSender(props));
    }

    private GraphiteSender createSender(GraphiteProperties props){
        switch (props.getSenderType()){
            case udp:
                return new GraphiteUDP(props.getHost(),props.getPort());
            case tcp:
                return new Graphite(props.getHost(),props.getPort());
            case pickled:
                return new PickledGraphite(props.getHost(),props.getPort());
            default:
                return new GraphiteUDP(props.getHost(),props.getPort());
        }
    }

}
