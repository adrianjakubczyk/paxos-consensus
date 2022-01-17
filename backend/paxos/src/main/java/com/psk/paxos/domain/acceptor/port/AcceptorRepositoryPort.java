package com.psk.paxos.domain.acceptor.port;

import com.psk.paxos.domain.acceptor.Acceptor;
import com.psk.paxos.domain.vote.VoteSession;

public interface AcceptorRepositoryPort {
  VoteSession findPresentVotingSession(int acceptorId);

  Acceptor findById(int acceptorId);
}
