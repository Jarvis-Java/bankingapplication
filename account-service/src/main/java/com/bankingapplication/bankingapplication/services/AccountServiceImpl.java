package com.bankingapplication.bankingapplication.services;

//AccountService.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bankingapplication.bankingapplication.dto.AccountDto;
import com.bankingapplication.bankingapplication.entity.Account;
import com.bankingapplication.bankingapplication.feignClient.NotificationClient;
import com.bankingapplication.bankingapplication.mapper.AccountsMapper;
import com.bankingapplication.bankingapplication.repositories.AccountRepository;
import com.bankingapplication.bankingapplication.request.NotificationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

 @Autowired
 private AccountRepository accountRepository;

 @Autowired
 private final NotificationClient notificationService;


@Override
public ResponseEntity<String> createAccount(AccountDto accountDTO) {

	Account account = AccountsMapper.DtoToEntity(accountDTO);
    
    accountRepository.save(account);
    
    NotificationRequest notification = new NotificationRequest(account.getEmail(),"Your Account created successfully.");

    // Send notification
   notificationService.sendNotification(notification);

    return ResponseEntity.ok("Account Service : Account created successfully.");
}

@Override
public ResponseEntity<Double> getAccountBalance(String accountNumber) {
    // Retrieve account from repository
    Account account = accountRepository.findById(Long.valueOf(accountNumber)).orElse(null);

    if (account != null) {
        return ResponseEntity.ok(account.getBalance());
    } else {
        return ResponseEntity.notFound().build();
    }
}

@Override
public ResponseEntity<String> deposit(String accountNumber, double amount) {

    // Retrieve account from repository
    Account account = accountRepository.findById(Long.valueOf(accountNumber)).orElse(null);

    if (account != null) {
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        accountRepository.save(account);

        NotificationRequest notification = new NotificationRequest(account.getEmail(),"Your Account deposited "+amount);

        // Send notification
       notificationService.sendNotification(notification);

        return ResponseEntity.ok("Account Service : Deposit successful. New balance: $" + newBalance);
    } else {
        return ResponseEntity.notFound().build();
    }

}

@Override
public ResponseEntity<String> withdraw(String accountNumber, Double amount) {
    // Retrieve account from repository
    Account account = accountRepository.findById(Long.valueOf(accountNumber)).orElse(null);

    if (account != null) {
        if (account.getBalance() >= amount) {
            double newBalance = account.getBalance() - amount;
            account.setBalance(newBalance);
            accountRepository.save(account);

            NotificationRequest notification = new NotificationRequest(account.getEmail(),"Your account debited "+amount);

            // Send notification
           notificationService.sendNotification(notification);

            return ResponseEntity.ok("Withdrawal successful. New balance: $" + newBalance);
        } else {
            return ResponseEntity.badRequest().body("Insufficient balance.");
        }
    } else {
        return ResponseEntity.notFound().build();
    }
}
}
