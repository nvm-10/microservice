package com.dev.accounts.functions;

import com.dev.accounts.dto.AccountsMsgDto;
import com.dev.accounts.service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountsFunctions {

    Logger logger = LoggerFactory.getLogger(AccountsFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(IAccountsService accountsService) {
        return accountNumber -> {
            logger.info("Updating communication with the details : " +  accountNumber.toString());
            accountsService.updateCommunicationStatus(accountNumber);
        };
    }
}
