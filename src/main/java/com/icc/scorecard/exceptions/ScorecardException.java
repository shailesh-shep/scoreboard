package com.icc.scorecard.exceptions;

public class ScorecardException extends RuntimeException {
   private String message;

   public ScorecardException(String message) {
      this.message = message;
   }

   public String getMessage() {
      return message;
   }
}