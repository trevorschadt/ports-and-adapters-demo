package com.dsg.demo.portsandadapters;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PortsAndAdaptersDemoApplicationTest {

    /**
     * This will test that the application context properly loads,
     * regardless of anything else.
     *
     * No logic is tested, it is just, "have I forgotten to wire
     * anything up?"
     */
    @Test
    void contextLoads() {
        assertThat(true).isTrue();
    }

}