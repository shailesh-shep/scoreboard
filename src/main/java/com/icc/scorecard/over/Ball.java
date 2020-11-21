package com.icc.scorecard.over;

import com.icc.scorecard.enums.BallType;

public class Ball {
   int runs;
   BallType ballType;

   public Ball(String str) {
      ballType = BallType.fromString(str);
      switch (ballType) {
      case R:
         runs = Integer.parseInt(str);
         break;
      case WD:
         runs = 1;
         break;
      case NB:
         runs = 1;
         break;
      }
   }

   public int getRuns() {
      return runs;
   }

   public BallType getBallType() {
      return ballType;
   }
}
