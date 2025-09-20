package com.team.exeteamup;

import com.team.exeteamup.config.DotEnvConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExeTeamupApplicationTests {
    @Test
    void loadEnvForTest() {
        DotEnvConfig.loadEnv();
    }

    @Test
    void contextLoads() {
    }

}
