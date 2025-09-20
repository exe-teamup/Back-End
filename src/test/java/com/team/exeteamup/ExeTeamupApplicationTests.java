package com.team.exeteamup;

import com.team.exeteamup.config.DotEnvConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExeTeamupApplicationTests {
    @BeforeAll
    static void loadEnvForTest() {
        DotEnvConfig.loadEnv();
    }

    @Test
    void contextLoads() {
    }

}
