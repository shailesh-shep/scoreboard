package com.icc.scorecard;

import com.icc.scorecard.cricket.Game;
import com.icc.scorecard.exceptions.ScorecardException;
import com.icc.scorecard.over.Over;
import com.icc.scorecard.team.Player;
import com.icc.scorecard.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandExecutor {
   private static CommandExecutor commandExecutor;

   private CommandExecutor() {

   }

   public static CommandExecutor getInstance() {
      if (commandExecutor == null) {
         commandExecutor = new CommandExecutor();
      }
      return commandExecutor;
   }

   private CommandType getCommandType(String commandString) {
      CommandType commandType = null;
      if (commandString == null) {
         System.out.println("Not a valid input");
      } else {
         String[] commandStringArray = commandString.split(" ");
         try {
            commandType = CommandType.valueOf(commandStringArray[0]);
         } catch (Exception e) {
            System.out.println("Unknown Command");
         }
      }
      return commandType;
   }

   public boolean execute(String commandString) {
      CommandType commandType = getCommandType(commandString);
      System.out.println(String.format("Executing: %s", commandString));

      if (commandType == null) {
         return false;
      }
      String[] commandStringArray = commandString.split(" ");
      Command command;

      switch (commandType) {
      case players_per_team:
         command = new PlayersPerTeamCommand(commandStringArray);
         break;
      case total_overs:
         command = new TotalOversCommand(commandStringArray);
         break;
      case batting_order:
         command = new BattingOrderCommand(commandStringArray);
         break;
      case over:
         command = new OverCommand(commandStringArray);
         break;
      case scorecard:
         command = new ScoreCommand(commandStringArray);
         break;
      default:
         System.out.println("Unknown Command");
         return false;
      }
      try {
         command.execute();
      } catch (ScorecardException e) {
         System.out.println(e.getMessage());
      } catch(Exception e) {
         System.out.println("Unknown System Issue");
         e.printStackTrace();
         return false;
      }
      return true;
   }

   private enum CommandType {
      players_per_team,
      total_overs,
      batting_order,
      over,
      scorecard
   }

   private interface Command {
      void execute();
   }

   private class PlayersPerTeamCommand implements Command {
      private String[] commandStringArray;

      PlayersPerTeamCommand(String[] s) {
         commandStringArray = s;
      }
      public void execute() {
         Game game = Game.getInstance();
         game.setPlayerPerTeam(Integer.parseInt(commandStringArray[1]));
      }
   }

   private class TotalOversCommand implements Command {
      private String[] commandStringArray;

      TotalOversCommand(String[] s) {
         commandStringArray = s;
      }
      public void execute() {
         Game game = Game.getInstance();
         game.setTotalOvers(Integer.parseInt(commandStringArray[1]));
      }
   }

   private class BattingOrderCommand implements Command {
      private String[] commandStringArray;

      BattingOrderCommand(String[] s) {
         commandStringArray = s;
      }

      public void execute() {
         Game game = Game.getInstance();
         Team team = new Team(commandStringArray[1]);
         for (int i=2; i<commandStringArray.length; i++) {
            team.addPlayer(commandStringArray[i]);
         }
         game.playTeam(team);
      }
   }

   private class OverCommand implements Command {
      private String[] commandStringArray;

      OverCommand(String[] s) {
         commandStringArray = s;
      }

      public void execute() {
         Game game = Game.getInstance();
         Over over = new Over();
         for (int i=1; i<commandStringArray.length; i++) {
            over.bowl(commandStringArray[i]);
         }
         game.bowlOver(over);
      }
   }

   private class ScoreCommand implements Command {
      private String[] commandStringArray;

      ScoreCommand(String[] s) {
         commandStringArray = s;
      }

      public void execute() {
         Game game = Game.getInstance();
         game.printTeam(commandStringArray[1]);
      }
   }
}