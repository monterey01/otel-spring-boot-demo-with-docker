package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import org.springframework.context.annotation.Bean;
import io.micrometer.core.instrument.binder.MeterBinder;
import java.util.List;

@SpringBootApplication
public class DemoApplication {



    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

    }

  @Bean
  public List<MeterBinder> systemMetrics() {
      return List.of(
          new JvmMemoryMetrics(),
          new JvmThreadMetrics(),
          new ProcessorMetrics(),
          new UptimeMetrics()
      );
  }
}

@RestController
@RequestMapping("/api")
class HelloController {
   private static final Logger logger = LogManager.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "world") String name) {
        logger.info("This is a test log " + name);
        return "Hello, " + name + "!";
    }

    @GetMapping("/error-test")
    public String error() {
        throw new RuntimeException("Simulated error for tracing/logging");
    }
}
