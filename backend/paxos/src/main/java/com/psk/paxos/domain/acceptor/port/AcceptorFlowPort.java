package com.psk.paxos.domain.acceptor.port;

public interface AcceptorFlowPort {
  boolean isSequenceCorrect(Integer acceptorId, String voteName, Integer seq);

  void updateVoteName(Integer acceptorId, String voteName);

  void acceptNewVoteSession(Integer acceptorId, Integer newSeq, String acceptedValue);
}
