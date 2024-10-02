package com.email.sendEmail.entities;

import org.springframework.http.HttpStatus;

public class CustomResponse {
    private String message;
    private HttpStatus httpStatus;
    private boolean success = false;

    // Getters and Setters
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Constructor
    public CustomResponse(String message, HttpStatus httpStatus, boolean success) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.success = success;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private HttpStatus httpStatus;
        private boolean success;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public CustomResponse build() {
            return new CustomResponse(message, httpStatus, success);
        }
    }

    @Override
    public String toString() {
        return "CustomResponse [message=" + message + ", httpStatus=" + httpStatus + ", success=" + success + "]";
    }
}
