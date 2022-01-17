package com.psk.paxos.domain.acceptor;

import com.psk.paxos.domain.vote.VoteSession;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class Acceptor {
  private int acceptorId;
  private Integer currentError;
  private Integer currentSequenceNumber;
  private List<VoteSession> votingSessions;
  private String voteName;

  public VoteSession getCurrentVotingSession() {
    return votingSessions.isEmpty() ? null : votingSessions.get(votingSessions.size() - 1);
  }

  public static Acceptor createInstance(int acceptorId){
    return Acceptor.builder()
            .acceptorId(acceptorId)
            .currentError(0)
            .currentSequenceNumber(1)
            .votingSessions(new LinkedList<>())
            .voteName("")
            .build();
  }
}
