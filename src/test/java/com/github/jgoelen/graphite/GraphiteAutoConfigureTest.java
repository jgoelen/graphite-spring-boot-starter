package com.github.jgoelen.graphite;

import com.codahale.metrics.MetricRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import static org.springframework.boot.test.EnvironmentTestUtils.addEnvironment;

public class GraphiteAutoConfigureTest {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    private GraphiteServerStub serverStub;

    @Before
    public void setup() throws Exception {
        serverStub = new GraphiteServerStub().start();
    }

    @After
    public void cleanup() {
        serverStub.stop();
        context.close();
    }

    @Test
    public void reporterShouldNotStartWhenDisabled(){
        initContextWith(false, 1, "test.applications.myapp", "host1");
        assertThat(this.context.getBeanNamesForType(GraphiteReportingManager.class), emptyArray());
    }

    @Test
    public void reporterShouldStartWhenEnabled(){
        initContextWith(true, 1, "test.applications.myapp", "host1");
        assertThat(this.context.getBeanNamesForType(GraphiteReportingManager.class), arrayWithSize(1));
    }

    @Test
    public void reportShouldPushMetricsWhenEnabled() throws Exception {
        final int intervalInSeconds = 1;
        final String prefix = "test.applications.myapp";
        final String source = "host1";
        final String counterName = "tests";
        initContextWith(true, intervalInSeconds, prefix, source);
        MetricRegistry metricRegistry = this.context.getBean(MetricRegistry.class);
        metricRegistry.counter(counterName).inc();
        String metric = serverStub.pollMetric(intervalInSeconds * 2);
        assertThat(metric, startsWith(prefix + "." + source + "." + counterName + ".count 1"));
    }


    private void initContextWith(boolean enabled, int intervalInSeconds, String prefix, String source) {
        addEnvironment(this.context, "graphite.enabled:" + enabled,
                "graphite.host:localhost",
                "graphite.port:" + serverStub.getPort(),
                "graphite.reportInterval:" + intervalInSeconds,
                "graphite.prefix:" + prefix,
                "graphite.sourceId:" + source);
        context.register(MetricsDropwizardAutoConfiguration.class,GraphiteAutoConfigure.class);
        context.refresh();
    }

    private static class GraphiteServerStub implements Runnable {

        private DatagramSocket serverSocket;
        private volatile boolean  keepRunning;
        private LinkedBlockingQueue<String> queue;

        public GraphiteServerStub() throws Exception {
            serverSocket = new DatagramSocket();
            serverSocket.setSoTimeout(5*1000); //wait for max 5 seconds
            keepRunning = false;
            queue = new LinkedBlockingQueue<>();
        }

        @Override
        public void run(){
            while (keepRunning) {
                byte[] bytez = new byte[1024];
                DatagramPacket datagram = new DatagramPacket(bytez, bytez.length);
                try {
                    serverSocket.receive(datagram);
                    queue.offer(new String(bytez));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public int getPort(){
            return serverSocket.getLocalPort();
        }

        public GraphiteServerStub start() throws Exception{
            keepRunning = true;
            new Thread(this).start();
            return this;
        }

        public void stop(){
            serverSocket.close();
            keepRunning = false;
        }

        public String pollMetric(int timeOut) throws InterruptedException {
            try {
                return queue.poll(timeOut, TimeUnit.SECONDS);
            } catch (InterruptedException timedOut){
                return "poll timed out";
            }
        }

    }

}