package com.psk.paxos.domain.acceptor;

import com.psk.paxos.domain.vote.VoteSession;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Acceptor {
  private int acceptorId;
  private Integer currentError = 0;
  private Integer currentSequenceNumber = 1;
  private List<VoteSession> votingSessions = new ArrayList<>();

  private String voteName = "";

  public VoteSession getCurrentVotingSession() {
    return votingSessions.isEmpty() ? null : votingSessions.get(votingSessions.size() - 1);
  }

}
