package com.psk.paxos.persistance.acceptor;

import com.psk.paxos.domain.acceptor.AcceptorResponse;
import com.psk.paxos.domain.acceptor.port.AcceptorFlowPort;
import com.psk.paxos.domain.acceptor.port.AcceptorCommunicationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcceptorCommunicationAdapter implements AcceptorCommunicationPort {
  private final AcceptorFlowPort acceptorFlowPort;


  @Override
  public AcceptorResponse addNewProblem(Integer acceptorId, String voteName, Integer seq) {
    if (acceptorFlowPort.isSequenceCorrect(acceptorId,voteName,seq)) {
      acceptorFlowPort.updateVoteName(acceptorId, voteName);
      return responseSimpleAccepted();
    }

    return responseSimpleReject();
  }

  @Override
  public void acceptedNewProblem(Integer acceptorId, Integer newSeq, String acceptedValue) {
    acceptorFlowPort.acceptNewVoteSession(acceptorId, newSeq,acceptedValue);
  }

  private AcceptorResponse responseSimpleAccepted() {
    return AcceptorResponse.builder().requestAccepted(true).build();
  }

  private AcceptorResponse responseSimpleReject() {
    return AcceptorResponse.builder().requestAccepted(false).build();
  }
}
