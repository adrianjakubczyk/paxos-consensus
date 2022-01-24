package com.psk.paxos.domain.acceptor.port;

import com.psk.paxos.domain.acceptor.AcceptorResponse;

import java.util.List;

public interface AcceptorCommunicationPort {
  AcceptorResponse createNewVote(Integer acceptorId, String voteName, Integer seq);

  void acceptedNewProposeVote(Integer acceptorId, Integer newSeq, String acceptedValue);

  List<AcceptorResponse> findAcceptors();

  AcceptorResponse findAcceptorResponseById(Integer acceptorId);

  void acceptedNewVote(int acceptorId, int sequenceNumber, String voteName);
}
