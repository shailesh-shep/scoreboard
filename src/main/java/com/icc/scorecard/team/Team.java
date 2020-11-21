package com.icc.scorecard.team;

import java.util.ArrayList;
import java.util.List;

public class Team {
   String name;
   List<Player> players;

   public Team(String name) {
      this.name = name;
      players = new ArrayList<Player>();
   }

   public void addPlayer(String name) {
      players.add(new Player(name));
   }

   public List<Player> getPlayers() {
      return players;
   }

   public String getName() {
      return name;
   }
}
