package com.acme.dbo;

import com.acme.dbo.config.Config;
import com.acme.dbo.controller.AccountController;
import com.acme.dbo.controller.AccountNotFoundException;
import com.acme.dbo.domain.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * see {@link org.springframework.test.context.junit.jupiter.SpringJUnitConfig} annotation
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class AccountCrudSystemIT {
    @Autowired
    private AccountController accountController;

    @Test
    public void shouldGetNoAccountsWhenNoCreated() {
        assertTrue(accountController.findAll().isEmpty());
    }

    @Test
    public void shouldCreateAccountWhenEmptyRepository() {

        Account acc = accountController.create(new Account(new BigDecimal("10.10")));

        assertEquals(1, accountController.findAll().size());
        assertEquals(0, acc.getId());
        assertEquals(new BigDecimal("10.10"), acc.getAmount());
    }


    @Test
    public void shouldFindAccountById() throws AccountNotFoundException {
        Account acc1 = accountController.create(new Account(12, new BigDecimal("10.10")));
        Account acc2 = accountController.create(new Account(13, new BigDecimal("20.20")));
        Account result = accountController.findById(12);
        assertEquals(12, result.getId());
        assertEquals(new BigDecimal("10.10"), result.getAmount());
    }

    @Test
    public void shouldNotFindAccountById() {
        assertThrows(AccountNotFoundException.class, () -> accountController.findById(12));
    }

}

//    public Account create(Account accountData) {
//        return accounts.create(accountData);
//    }
//
//    public Account findById(Integer id) {
//        return accounts.findById(id);
//    }
//
//    public Collection<Account> findAll() {
//        return accounts.findAll();
//    }

