package com.psk.paxos.domain.acceptor;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class AcceptorResponse {
  private Boolean requestAccepted;
  private String currentProblem;
  private Collection<String> currentProblemVotes;
  private Integer currentSequenceNumber;
  private Integer currentFault;
}
