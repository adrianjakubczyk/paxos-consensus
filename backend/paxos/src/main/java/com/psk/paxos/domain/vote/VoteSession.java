package com.psk.paxos.domain.vote;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class VoteSession {
  private String presentProblem;
  private Collection<String> votes;
}
