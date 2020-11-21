package com.icc.scorecard.enums;

public enum BallType {
   WD, NB, W, R;

   public static BallType fromString(String str) {
      for (BallType b : BallType.values()) {
         if (b.toString().equals(str))
            return b;
      }
      return R;
   }
}