package com.psk.paxos.domain.client;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Client {
  private int clientId;
  private boolean isLeader;
  private int sequenceNumber;
  private String vote;
  private List<String> votes;

  public static String getDefaultProblem(){
    return "Default problem";
  }
}
