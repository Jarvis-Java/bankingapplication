package com.bankingapplication.bankingapplication.mapper;

import com.bankingapplication.bankingapplication.dto.AccountDto;
import com.bankingapplication.bankingapplication.entity.Account;

public class AccountsMapper {
	
	public static AccountDto entityToDtoObject(Account account) {
		
		AccountDto accountDto = new AccountDto();
		accountDto.setId(account.getId());
		accountDto.setBalance(account.getBalance());
		accountDto.setEmail(account.getEmail());
		accountDto.setName(account.getName());
		accountDto.setAddress(account.getAddress());
		accountDto.setAccountType(account.getAccountType());
		
		return accountDto;
	}
	
	public static Account DtoToEntity(AccountDto accountDto) {
		
		Account account = new Account();
		account.setId(accountDto.getId());
		account.setBalance(accountDto.getBalance());
		account.setEmail(accountDto.getEmail());
		account.setName(accountDto.getName());
		account.setAddress(accountDto.getAddress());
		account.setAccountType(accountDto.getAccountType());
		
		return account;
	
		
	}

}
