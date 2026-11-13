package com.spring.spring_rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides a simple endpoint for verifying that the REST application is
 * responding.
 */
@RestController
@RequestMapping("/")
public class DemoRestController {

  /**
   * Returns a greeting.
   *
   * @return the greeting text
   */
  @GetMapping("/")
  public String sayHello() {
    return "Hello World!";
  }
}
