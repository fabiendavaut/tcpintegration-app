package com.viadialog.tcpintegration.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.context.IntegrationFlowRegistration;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class TCPConsummer {

    private static final Logger logger = LoggerFactory.getLogger(TCPConsummer.class);

    @Autowired
    private IntegrationFlowContext flowContext;

    @Autowired
    private MyMessageHandler myMessageHandler;

    private final LinkedHashMap<String, IntegrationFlowRegistration> integrationFlowRegistrations = new LinkedHashMap<String, IntegrationFlowRegistration>() {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

    };

    public synchronized void create(BusConfig busConfig) {

        logger.info("create consummer for busConfig" + busConfig);

        IntegrationFlowRegistration integrationFlowRegistration = integrationFlowRegistrations
            .get(String.valueOf(busConfig.getId()));

        if (integrationFlowRegistration == null) {

            logger.debug("Create new TCP connector flow [id=" + busConfig.getId() + "]");

            IntegrationFlow flow = IntegrationFlows.from(tcpReceivingChannelAdapter(busConfig))
                .handle(this.myMessageHandler)
                .get();

            integrationFlowRegistration = this.flowContext.registration(flow).register();

            this.integrationFlowRegistrations.put(String.valueOf(busConfig.getId()),
                integrationFlowRegistration);
        }
    }

    public TcpReceivingChannelAdapter tcpReceivingChannelAdapter(BusConfig busConfig) {

        TcpReceivingChannelAdapter tcpReceivingChannelAdapter = new TcpReceivingChannelAdapter();


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