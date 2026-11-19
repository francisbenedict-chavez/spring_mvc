package com.spring.spring_rest.exception;

/**
 * Indicates that a requested student could not be found.
 */
public class StudentNotFoundException extends RuntimeException {

  private static final Long serialVersionUID = -2690100046087976236L;

  /**
   * Creates an exception with a message and a cause.
   *
   * @param message description of the missing student
   * @param cause the underlying exception
   */
  public StudentNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates an exception with a message.
   *
   * @param message description of the missing student
   */
  public StudentNotFoundException(String message) {
    super(message);
  }

  /**
   * Creates an exception with an underlying cause.
   *
   * @param cause the underlying exception
   */
  public StudentNotFoundException(Throwable cause) {
    super(cause);
  }
}
