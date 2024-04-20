package com.bankingapplication.bankingapplication.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bankingapplication.bankingapplication.dto.AccountDto;
import com.bankingapplication.bankingapplication.services.AccountServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AccountServiceImpl accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void testCreateAccount() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(123456L);
        accountDto.setBalance(1000.00);

        when(accountService.createAccount(any(AccountDto.class))).thenReturn(ResponseEntity.ok("Account created successfully"));

        mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"123456\",\"balance\":1000.00}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Account created successfully"));
    }

    @Test
    public void testGetAccountBalance() throws Exception {
        when(accountService.getAccountBalance("123456")).thenReturn(ResponseEntity.ok(1000.00));

        mockMvc.perform(get("/api/v1/accounts/123456/balance")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1000.0"));
    }

    @Test
    public void testDeposit() throws Exception {
        when(accountService.deposit("123456", 500.00)).thenReturn(ResponseEntity.ok("Amount deposited successfully"));

        mockMvc.perform(post("/api/v1/accounts/123456/deposit/amount")
                .param("amount", "500.00")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Amount deposited successfully"));
    }

    @Test
    public void testWithdraw() throws Exception {
        when(accountService.withdraw("123456", 200.00)).thenReturn(ResponseEntity.ok("Amount withdrawn successfully"));

        mockMvc.perform(post("/api/v1/accounts/123456/withdraw/amount")
                .param("amount", "200.00")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Amount withdrawn successfully"));
    }
}
