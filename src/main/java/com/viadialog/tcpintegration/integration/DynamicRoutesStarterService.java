package com.viadialog.tcpintegration.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DynamicRoutesStarterService implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DynamicRoutesStarterService.class);

    private BusConfigService busConfigService;

    private TCPConsummer tcpConsummer;

    public DynamicRoutesStarterService(BusConfigService busConfigService, TCPConsummer tcpConsummer) {
        this.busConfigService = busConfigService;
        this.tcpConsummer = tcpConsummer;
    }

    @Override
    public void run(ApplicationArguments arg0) throws Exception {

        logger.info("XXXXXX Start Dynamic Routes");

        /**
         * Start EIP consummer
         */
        List<BusConfig> busConfigs = busConfigService.getActivatedList();

        busConfigs.forEach(e -> {
            tcpConsummer.create((BusConfig) e);
        });
    }

}

