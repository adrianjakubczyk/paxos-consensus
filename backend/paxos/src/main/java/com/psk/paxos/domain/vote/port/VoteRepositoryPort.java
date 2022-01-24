package com.psk.paxos.domain.vote.port;

import com.psk.paxos.domain.vote.Vote;

import java.util.Collection;

public interface VoteRepositoryPort {
  void createNewVoteSession(String voteName, Integer clientId);

  void createNewVote(String voteName, Integer clientId);

  void createNewVoteHistory(Vote vote);

  Collection<Vote> findAll();
}
