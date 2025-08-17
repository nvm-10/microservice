package com.dev.accounts.mapper;

import com.dev.accounts.dto.AccountsDto;
import com.dev.accounts.entity.Accounts;

public class AccountsMapper {

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts account) {
        account.setAccountNumber(accountsDto.getAccountNumber());
        account.setBranchAddress(accountsDto.getBranchAddress());
        account.setAccountType(accountsDto.getAccountType());
        return account;
    }

    public static AccountsDto mapToAccountDto(Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }
}
