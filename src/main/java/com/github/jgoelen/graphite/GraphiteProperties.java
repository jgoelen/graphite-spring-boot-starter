package com.github.jgoelen.graphite;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "graphite")
public class GraphiteProperties {

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
     * production.applications.user_service
     */
    private String prefix;

    /**
     * A unique string that uniquely identifies the source of a metric,
     * in practice this will be the hostname and/or container name.
     */
    private String sourceId;

    /***
     * Feature flag
     */
    private boolean enabled = true;


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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
