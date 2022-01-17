package com.psk.paxos.domain.acceptor.port;

import com.psk.paxos.domain.acceptor.AcceptorResponse;

import java.util.List;

public interface AcceptorCommunicationPort {
  AcceptorResponse addNewProblem(Integer acceptorId, String voteName, Integer seq);

  void acceptedNewProblem(Integer acceptorId, Integer newSeq, String acceptedValue);

  List<AcceptorResponse> findAcceptors();
}
