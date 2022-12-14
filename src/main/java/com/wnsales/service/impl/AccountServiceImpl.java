package com.wnsales.service.impl;

import com.wnsales.model.Account;
import com.wnsales.repository.AccountRepository;
import com.wnsales.repository.UserRepository;
import com.wnsales.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends _DefaultService implements AccountService {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Optional<Account> findById(Long userId) {
        Optional<Account> optionalUser = accountRepository.findById(userId);

        if (optionalUser.isEmpty()){
            throw new EmptyResultDataAccessException("Resource not found", 1);
        }

        return optionalUser;
    }

    @Override
    public void delete(Long userId) {
        Optional<Account> optionalUser = accountRepository.findById(userId);

        if (optionalUser.isPresent()) {
            accountRepository.delete(optionalUser.get());
        }
    }

    @Override
    @Transactional
    public Account save(Account account) {

        if (account.getUser() == null){
            throw new RuntimeException("Users is required");
        }

        if (!userRepository.existsById(account.getUser().getId())){
            throw new RuntimeException("Invalid user");
        }

        return accountRepository.save(account);
    }

    @Override
    public Account edit(Long accountId, Account account) {

        if (account.getUser() == null){
            throw new RuntimeException("Users is required");
        }

        if (!userRepository.existsById(account.getUser().getId())) {
            throw new RuntimeException("Invalid user");
        }

        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if(accountOptional.isEmpty()){
            throw new RuntimeException("Invalid account");
        }

        Account target = accountOptional.get();
        BeanUtils.copyProperties(account, target, "id");
        return accountRepository.save(target);
    }

    @Override
    public Account partialEdit(Long accountId, Account account) {

        if (account.getUser() != null){
            if (!userRepository.existsById(account.getUser().getId())){
                throw new RuntimeException("Invalid account");
            }
        }
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if(accountOptional.isEmpty()){
            throw new RuntimeException("Invalid account");
        }

        Account target = accountOptional.get();

        var ignore = new ArrayList<>(List.of("uid"));
        addPropertyNullToIgnore(account, ignore);

        BeanUtils.copyProperties(account, target, ignore.toArray(String[]::new));

        return accountRepository.save(target);
    }

}
