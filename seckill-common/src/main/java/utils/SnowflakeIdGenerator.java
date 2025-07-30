package utils;

import cn.hutool.core.lang.Snowflake;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {
    private final Snowflake snowflake = new Snowflake(1, 1);
    public long nextId() {
        return snowflake.nextId();
    }
}
