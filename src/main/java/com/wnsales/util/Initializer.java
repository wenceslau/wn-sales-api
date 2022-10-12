package com.wnsales.util;

import com.wnsales.model.Account;
import com.wnsales.model.Product;
import com.wnsales.model.User;
import com.wnsales.service.AccountService;
import com.wnsales.service.ProductService;
import com.wnsales.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Service
public class Initializer {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    public void init(){

        String reg = "^PT[0-9]{2}[0-9]{21}$";
            String vlr = "PT10003800015623391998072";
        System.out.println(Pattern.matches(reg, vlr));

        User user1 = new User();
        user1.setName("Jon Dingle");
        user1.setEmail("jon@dingle.com");
        userService.save(user1);

        User user2 = new User();
        user2.setName("Barbara Gordon");
        user2.setEmail("barbara@gordon.com");
        userService.save(user2);

        Account ac1 = new Account();
        ac1.setUser(user1);
        ac1.setAccountName("Account Jon 1");
        ac1.setIban(vlr);
        accountService.save(ac1);

        Account ac2 = new Account();
        ac2.setUser(user1);
        ac2.setAccountName("Account Jon 2");
        ac2.setIban(vlr);
        accountService.save(ac2);

        Product pr1 = new  Product();
        pr1.setAccount(ac1);
        pr1.setName("Product 1");
        pr1.setPrice(new BigDecimal(10.99));
        productService.save(pr1);

        Product pr2 =new  Product();
        pr2.setAccount(ac2);
        pr2.setName("Product 2");
        pr2.setPrice(new BigDecimal(90.00));
        productService.save(pr2);

    }
}
