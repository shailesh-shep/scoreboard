package com.icc.scorecard.team;

import com.icc.scorecard.enums.PlayerType;
import com.icc.scorecard.over.Ball;

import static com.icc.scorecard.util.Constants.PAYER_OP_FORMAT;

public class Player {
   String name;
   int ballsFaced;
   int runsScored;
   int fours;
   int sixes;
   PlayerType playerType;

   public Player() {
      this.playerType = PlayerType.YET_TO_BAT;
   }


   public Player(String name) {
      this.name = name;
      this.playerType = PlayerType.YET_TO_BAT;
   }

   public void faceBall(Ball ball) {
      if (playerType == PlayerType.YET_TO_BAT)
         playerType = PlayerType.BATTING;

      switch (ball.getBallType()) {
      case R:
         runsScored += ball.getRuns();
         if (ball.getRuns()==4)
            fours++;
         if (ball.getRuns()==6)
            sixes++;
         break;
      case W:
         playerType = PlayerType.OUT;
         break;
      }
      ballsFaced++;
   }

   public String getName() {
      return name;
   }

   public int getBallsFaced() {
      return ballsFaced;
   }

   public int getRunsScored() {
      return runsScored;
   }

   public int getFours() {
      return fours;
   }

   public int getSixes() {
      return sixes;
   }

   public PlayerType getPlayerType() {
      return playerType;
   }

   public void setPlayerType(PlayerType playerType) {
      this.playerType = playerType;
   }

   public void print() {
      String astr = (playerType.equals(PlayerType.BATTING))?"*":"";
      System.out.println(
            String.format(PAYER_OP_FORMAT, name+astr, runsScored, fours, sixes, ballsFaced));
   }
}
