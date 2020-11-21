package com.icc.scorecard.cricket;

import com.icc.scorecard.enums.InningType;
import com.icc.scorecard.enums.PlayerType;
import com.icc.scorecard.exceptions.ScorecardException;
import com.icc.scorecard.over.Ball;
import com.icc.scorecard.over.Over;
import com.icc.scorecard.team.Player;
import com.icc.scorecard.team.Team;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.icc.scorecard.util.Constants.PAYER_OP_FORMAT;

public class Inning {
   Team team;
   Queue<Player> yetToBat;
   List<Player> played;
   List<Over> overs;
   int runs;
   int wickets;

   Player batsman1;
   Player batsman2;
   Player onStrike;
   InningType inningStatus;
   int totalOvers;

   public Inning(Team team, int totalOvers) {
      // Initialize overs
      this.totalOvers = totalOvers;
      overs = new ArrayList<Over>();

      // Initialize team
      this.team = team;
      this.yetToBat = new LinkedList<Player>(team.getPlayers());
      played = new ArrayList<Player>();
      callNewBatsman();
      onStrike = batsman1;
      inningStatus = InningType.IN_PROGRESS;
   }

   private void callNewBatsman() {
      if (!yetToBat.isEmpty() && (batsman1 == null
            || batsman1.getPlayerType() == PlayerType.OUT)) {
         batsman1 = yetToBat.poll();
         played.add(batsman1);
         onStrike = batsman1;
      }
      if (!yetToBat.isEmpty() && (batsman2 == null
            || batsman2.getPlayerType() == PlayerType.OUT)) {
         batsman2 = yetToBat.poll();
         played.add(batsman2);
         onStrike = batsman2;
      }
   }

   private void changeStrike() {
      if (onStrike.getName().equals(batsman1.getName())) {
         onStrike = batsman2;
      } else {
         onStrike = batsman1;
      }
   }

   public void addOver(Over over) {
      if (overs.size() == totalOvers) {
         throw new ScorecardException("Inining is over");
      }
      for (Ball ball : over.getBalls()) {
         if (iningsFinished()) {
            throw new ScorecardException("Inining is finished");
         }
         switch (ball.getBallType()) {
         case W:
            wickets++;
            onStrike.faceBall(ball);
            callNewBatsman();
            break;
         case R:
            onStrike.faceBall(ball);
            runs += ball.getRuns();
            if (ball.getRuns() % 2 == 1)
               changeStrike();
            break;
         case WD:
            runs += ball.getRuns();
            break;
         case NB:
            runs += ball.getRuns();
            break;
         }
      }
      overs.add(over);
      changeStrike();
      updateStatus();
   }

   private void updateStatus() {
      if (batsman1.getPlayerType() == PlayerType.OUT
            || batsman2.getPlayerType() == PlayerType.OUT || totalOvers == overs
            .size()) {
         inningStatus = InningType.FINISHED;
      }
   }

   public Boolean iningsFinished() {
      return inningStatus == InningType.FINISHED;
   }

   public int getRuns() {
      return runs;
   }

   public int getWickets() {
      return wickets;
   }

   public Team getTeam() {
      return team;
   }

   private String getOvers() {
      int balls = 0;
      for (Over ov : overs) {
         balls += ov.getValidBalls();
      }
      int complete = balls / 6;
      int extra = balls % 6;
      if (extra == 0)
         return Integer.toString(complete);
      return String.format("%s.%s", complete, extra);
   }

   public void print() {
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println(
            String.format(PAYER_OP_FORMAT, "Name", "Score", "Fours",
                  "Sixes", "Balls"));
      for (Player player : played) {
         player.print();
      }
      for (Player player : yetToBat) {
         player.print();
      }
      System.out.println(String.format("Total:%s/%s", runs, wickets));
      System.out.println(String.format("Overs:%s", getOvers()));
      System.out.println(String.format("Inning:%s", inningStatus));
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
   }
}
