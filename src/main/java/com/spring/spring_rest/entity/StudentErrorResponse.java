package com.spring.spring_rest.entity;

/**
 * Represents the error details returned by the REST API.
 */
public class StudentErrorResponse {

  private int status;
  private String message;
  private Long timeStamp;

  public StudentErrorResponse() {}

  /**
   * Creates a student error response.
   *
   * @param status the HTTP status code
   * @param message the error message
   * @param timeStamp the time the error occurred
   */
  public StudentErrorResponse(int status, String message, Long timeStamp) {
    this.status = status;
    this.message = message;
    this.timeStamp = timeStamp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Long timeStamp) {
    this.timeStamp = timeStamp;
  }
}
