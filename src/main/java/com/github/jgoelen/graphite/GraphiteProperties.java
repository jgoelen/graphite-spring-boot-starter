package com.github.jgoelen.graphite;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.jgoelen.graphite.GraphiteSenderType.*;

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
}
