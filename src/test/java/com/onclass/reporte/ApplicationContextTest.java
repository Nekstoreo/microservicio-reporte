package com.onclass.reporte;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Requires MongoDB to be running for full context loading")
class ApplicationContextTest {

    @Test
    void contextLoads() {
    }
}
