package com.bankingapplication.bankingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDTO {

	 private Long id;

	 private String accountId;
	 private double amount;
	 private String type;

}
