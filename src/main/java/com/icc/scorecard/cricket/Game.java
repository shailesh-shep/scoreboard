package com.icc.scorecard.cricket;

import com.icc.scorecard.exceptions.ScorecardException;
import com.icc.scorecard.over.Over;
import com.icc.scorecard.team.Team;

import java.util.ArrayList;
import java.util.List;

public class Game {
   Inning currentInnings;
   Inning firstInning;
   Inning secondInning;

   int playersPerTeam;
   int totalOvers;
   List<Team> teams;
   private static Game game;

   public static Game getInstance() {
      if (game == null) {
         game = new Game();
      }
      return game;
   }

   private Game() {
      teams = new ArrayList<Team>();
   }

   public void playTeam(Team team) {
      teams.add(team);
      currentInnings = new Inning(team, totalOvers);
      if (firstInning == null) {
         firstInning = currentInnings;
      } else {
         secondInning = currentInnings;
      }
   }

   public void bowlOver(Over over) {
      if (currentInnings==null)
         throw new ScorecardException("No inning is in play");
      currentInnings.addOver(over);
   }

   public void setPlayerPerTeam(int playersPerTeam) {
      this.playersPerTeam = playersPerTeam;
   }

   public void setTotalOvers(int totalOvers) {
      this.totalOvers = totalOvers;
   }

   public int getTotalOvers() {
      return totalOvers;
   }

   public void printTeam(String teamName) {
      if (firstInning != null && teamName
            .equals(firstInning.getTeam().getName())) {
         firstInning.print();
      }
      if (secondInning != null && teamName
            .equals(secondInning.getTeam().getName())) {
         secondInning.print();
      }
      printStatus();
   }

   public void printStatus() {
      if (secondInning == null || !secondInning.iningsFinished()) {
         System.out.println("Game is in progress");
      } else {
         if (secondInning.getRuns() == firstInning.getRuns()) {
            System.out.println("Game is a draw");
         } else if (secondInning.getRuns() > firstInning.getRuns()) {
            System.out.println(String.format("Team %s won by %s wickets",
                  secondInning.getTeam().getName(),
                  (playersPerTeam - secondInning.getWickets())));
         } else {
            System.out.println(String.format("Team %s won by %s run",
                  firstInning.getTeam().getName(),
                  (firstInning.getRuns() - secondInning.getRuns())));
         }
      }
   }
}
