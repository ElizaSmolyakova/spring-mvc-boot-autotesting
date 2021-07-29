package com.acme.dbo;

import com.acme.dbo.config.Config;
import com.acme.dbo.config.TestConfig;
import com.acme.dbo.controller.AccountController;
import com.acme.dbo.controller.AccountNotFoundException;
import com.acme.dbo.dao.AccountRepository;
import com.acme.dbo.domain.Account;
import com.acme.dbo.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//controller -> service -> repository (int) -> (mock) mapBackend

/**
 * see {@link org.springframework.test.context.junit.jupiter.SpringJUnitConfig} annotation
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource("classpath:application-test.properties")
public class AccountCrudIT {
    @Autowired  private AccountRepository accountRepositoryStub;
    @Autowired private AccountController accountController;

    @Test
    public void shouldGetNoAccountsWhenNoCreated() {
        when(accountRepositoryStub.findAll()).thenReturn(null);

        assertNull(accountController.findAll());
    }

    @Test
    public void shouldReturnAllAccountsWhenAskFindAll() {
        when(accountRepositoryStub.findAll()).thenReturn(
                asList(new Account(3, new BigDecimal("3.33")), new Account(4, new BigDecimal("4.44"))));

        assertEquals(2, accountController.findAll().size());

        assertTrue(accountController.findAll().contains(
                new Account(3, new BigDecimal("3.33"))));

        assertTrue(accountController.findAll().contains(
                new Account(4, new BigDecimal("4.44"))));
    }

    @Test
    public void shouldReturnAccountWhenAskFindById() throws AccountNotFoundException {
        when(accountRepositoryStub.findById(3)).thenReturn(
                new Account(3, new BigDecimal("3.33")));

        assertEquals(3, accountController.findById(3).getId());
        assertEquals(new BigDecimal("3.33"), accountController.findById(3).getAmount());
    }

    @Test
    public void shouldAskRepositoryWithParamsWhenCreate() throws AccountNotFoundException {
       // verify(accountRepositoryStub).accountController.create( new Account(3, new BigDecimal("3.33");

    }

}
