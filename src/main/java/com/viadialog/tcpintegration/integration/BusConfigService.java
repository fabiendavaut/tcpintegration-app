package com.viadialog.tcpintegration.integration;

import com.viadialog.tcpintegration.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusConfigService {

    private static final Logger logger = LoggerFactory.getLogger(BusConfigService.class);

    private ApplicationProperties applicationProperties;

    public BusConfigService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;

    }

    public List<BusConfig> getActivatedList() {

        logger.info("retrieving configurations from properties file...");

        List<BusConfig> busConfigList = new ArrayList<>();

        BusConfig busConfig = new BusConfig();
        busConfig.setHostname("localhost");
        busConfig.setPort(8080);


        busConfigList.add(new BusConfig());

        return busConfigList;
    }

}
