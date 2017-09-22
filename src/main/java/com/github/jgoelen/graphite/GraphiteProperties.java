package com.github.jgoelen.graphite;

import com.codahale.metrics.MetricFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ClassUtils;

import static com.github.jgoelen.graphite.GraphiteSenderType.udp;

@ConfigurationProperties(prefix = "graphite")
public class GraphiteProperties {
    private static final Logger logger = LoggerFactory.getLogger(GraphiteProperties.class);


    /**
     * Hostname of the Graphite server.
     */
    private String host;

    /**
     * UDP Port of the Graphite server.
     */
    private int port;

    /**
     * The reporting interval in seconds.
     */
    private int reportInterval;

    /**
     * Prefix for all metric names that get send to the graphite server, e.g.
     * production.applications.user_service.host
     */
    private String prefix;

    /**
     */
    private GraphiteSenderType senderType = udp;

    /***
     * Feature flag
     */
    private boolean enabled = true;

    /**
     * MetricFilter to use
     */
    private MetricFilter metricFilter = MetricFilter.ALL;


    public GraphiteProperties() {

    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getReportInterval() {
        return reportInterval;
    }

    public void setReportInterval(int reportInterval) {
        this.reportInterval = reportInterval;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public GraphiteSenderType getSenderType() {
        return senderType;
    }

    public void setSenderType(GraphiteSenderType senderType) {
        this.senderType = senderType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public MetricFilter getMetricFilter() {
        return metricFilter;
    }

    public void setMetricFilter(String metricFilterClassName) {
        try {
            Class<?> metricFilterClass = ClassUtils.forName(metricFilterClassName, null);
            Object metricFilter = metricFilterClass.newInstance();
            this.metricFilter = (MetricFilter) metricFilter;
        } catch (Exception e) {
            logger.error(metricFilterClassName + " is invalid MetricFilter!", e);
        }
    }
}
