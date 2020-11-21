package com.icc.scorecard.over;

import com.icc.scorecard.exceptions.ScorecardException;

import java.util.ArrayList;
import java.util.List;

import static com.icc.scorecard.util.Constants.BALLS_PER_OVER;

public class Over {
   int validBalls;
   List<Ball> balls;

   public Over() {
      balls = new ArrayList<Ball>();
   }

   public void bowl(String Str) {
      if (finished()) {
         throw new ScorecardException("Over is finished, can't bowl more");
      }
      Ball ball = new Ball(Str);
      balls.add(ball);
      switch (ball.getBallType()) {
      case R:
         validBalls++;
         break;
      case W:
         validBalls++;
         break;
      }
   }

   public boolean finished() {
      if (validBalls==BALLS_PER_OVER)
         return true;
      return false;
   }

   public List<Ball> getBalls() {
      return balls;
   }

   public int getValidBalls() {
      return validBalls;
   }

   public Ball getLastBall() {
      if (balls.size()==0) {
         throw new ScorecardException("No balls are played");
      }
      return balls.get(balls.size()-1);
   }
}
