package com.icc.scorecard;

import sun.misc.ClassLoaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
   public static void main(String[] args) throws IOException {
      ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      InputStream inputStream = classloader.getResourceAsStream("input");

      CommandExecutor commandExecutor = CommandExecutor.getInstance();
      InputStreamReader
            streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
      BufferedReader reader = new BufferedReader(streamReader);
      for (String line; (line = reader.readLine()) != null;) {
         commandExecutor.execute(line);
      }
   }
}
