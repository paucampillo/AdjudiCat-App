package com.adjudicat.configuration;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@WithUserDetails(value = "test", userDetailsServiceBeanName = "authHelper")
public class BaseTest {

    @Test
    public void testOk() {
        Assert.assertTrue(true);
    }
}
