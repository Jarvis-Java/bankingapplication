package com.bankingapplication.bankingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

	 private Long id;
	 private String name;
	 private String email;
	 private String phoneNumber;
	 private double balance;
	 private String address;
	 private String accountType;
}