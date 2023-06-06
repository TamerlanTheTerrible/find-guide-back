package me.timur.findguideback.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class,
})
@Slf4j
public class TestIntegrationConfiguration {

}
