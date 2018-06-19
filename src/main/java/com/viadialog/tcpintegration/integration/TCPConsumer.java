package com.viadialog.tcpintegration.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.context.IntegrationFlowRegistration;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.connection.TcpNioClientConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 *
 */

@Service
public class TCPConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TCPConsumer.class);

    private IntegrationFlowContext flowContext;

    private MyMessageHandler myMessageHandler;

    public TCPConsumer(IntegrationFlowContext flowContext, MyMessageHandler myMessageHandler) {
        this.flowContext = flowContext;
        this.myMessageHandler = myMessageHandler;
    }

    private final LinkedHashMap<String, IntegrationFlowRegistration> integrationFlowRegistrations = new LinkedHashMap<String, IntegrationFlowRegistration>() {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
    };

    public synchronized void create(BusConfig busConfig) {

        logger.info("create consumer for busConfig" + busConfig);

        IntegrationFlowRegistration integrationFlowRegistration = integrationFlowRegistrations
            .get(String.valueOf(busConfig.getId()));

        if (integrationFlowRegistration == null) {

            logger.debug("Create new TCP connector flow [id=" + busConfig + "]");

            IntegrationFlow flow = IntegrationFlows.from(tcpReceivingChannelAdapter(busConfig))
                .handle(this.myMessageHandler)
                .get();

            integrationFlowRegistration = this.flowContext.registration(flow).register();

            this.integrationFlowRegistrations.put(String.valueOf(busConfig.getId()),
                integrationFlowRegistration);
        }
    }

    public TcpReceivingChannelAdapter tcpReceivingChannelAdapter(BusConfig busConfig) {

        TcpNioClientConnectionFactory tcpNioClientConnectionFactory = new TcpNioClientConnectionFactory(busConfig.getHostname(), busConfig.getPort());

 //       tcpNioClientConnectionFactory.setApplicationEventPublisher();

        TcpReceivingChannelAdapter tcpReceivingChannelAdapter = new TcpReceivingChannelAdapter();

        tcpReceivingChannelAdapter.setConnectionFactory(tcpNioClientConnectionFactory);

        tcpReceivingChannelAdapter.setAutoStartup(true);
        tcpReceivingChannelAdapter.setClientMode(true);

        return tcpReceivingChannelAdapter;
    }


    public synchronized void remove(BusConfig busConfig) {
        IntegrationFlowRegistration integrationFlowRegistration = integrationFlowRegistrations
            .get(String.valueOf(busConfig.getId()));

        if (integrationFlowRegistration != null) {
            this.flowContext.remove(integrationFlowRegistration.getId());
        }
    }

}
