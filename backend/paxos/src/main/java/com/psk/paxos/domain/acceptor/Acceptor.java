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
  private Integer currentSequenceNumber;
  private List<VoteSession> votingSessions;
  private String voteName;
  private Integer currentError;

  public VoteSession getCurrentVotingSession() {
    return votingSessions.isEmpty() ? null : votingSessions.get(votingSessions.size() - 1);
  }

  public static Acceptor createInstance(int acceptorId){
    return Acceptor.builder()
            .currentError(0)
            .acceptorId(acceptorId)
            .currentSequenceNumber(1)
            .votingSessions(new LinkedList<>())
            .voteName("")
            .build();
  }
}
