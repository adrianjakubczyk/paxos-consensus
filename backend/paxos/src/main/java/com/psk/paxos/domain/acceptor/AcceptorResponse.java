package com.psk.paxos.domain.acceptor;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class AcceptorResponse {
  private Integer currentError;
  private Boolean isAcceptedProposeVote;
  private String presentVote;
  private Collection<String> presentVotes;
  private Integer currentSequenceNumber;
}
