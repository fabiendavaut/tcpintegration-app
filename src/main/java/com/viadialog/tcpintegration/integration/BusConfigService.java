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

        logger.info("retrieving configurations ...");

        List<BusConfig> busConfigList = new ArrayList<>();

        BusConfig busConfig = new BusConfig();
        busConfig.setId(1L);
        busConfig.setHostname("vlz2-viapika-dev.velizy.lan");
        busConfig.setPort(9000);
        busConfig.setModule("APP_TEST");
        busConfig.setFamily("VIACOMAPPS2");
        busConfigList.add(busConfig);

        return busConfigList;
    }

}
