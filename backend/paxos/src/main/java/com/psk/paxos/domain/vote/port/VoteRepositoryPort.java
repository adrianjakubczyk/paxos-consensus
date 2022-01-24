package com.psk.paxos.domain.vote.port;

public interface VoteRepositoryPort {
  void createNewVoteSession(String voteName, Integer clientId);

  void createNewVote(String voteName, Integer clientId);
}
