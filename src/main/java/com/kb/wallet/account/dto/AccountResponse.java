package com.kb.wallet.account.dto;

import com.kb.wallet.account.domain.Account;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.*;

@Setter
@Getter
@Builder
@NonNull
public class AccountResponse {

  private Long id;
  private String accountNumber;
  private Integer balance;
  private LocalDateTime createdAt;

  public static List<AccountResponse> toAccounts(List<Account> accounts) {
    return accounts.stream()
        .map(account ->
            AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .build()
        )
        .collect(Collectors.toList());
  }

  public static AccountResponse toAccount(Account account) {
    return AccountResponse.builder()
        .id(account.getId())
        .accountNumber(account.getAccountNumber())
        .balance(account.getBalance())
        .build();
  }
}
