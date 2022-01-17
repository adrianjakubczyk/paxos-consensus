package com.psk.paxos.persistance.vote;

import com.psk.paxos.domain.acceptor.port.AcceptorGatePort;
import com.psk.paxos.domain.vote.port.VoteRepositoryPort;
import com.psk.paxos.provider.SequenceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteRepositoryAdapter implements VoteRepositoryPort {
  private final SequenceProvider seqProvider;
  private final AcceptorGatePort acceptorGatePort;

  @Override
  public void createNewVoteSession(String voteName, Integer clientId) {
    final int currentSeq = seqProvider.getSeq();
    seqProvider.seqNextValue();

    for (int i = 0; i < 3; i++) {
      acceptorGatePort.sendPropose(i, voteName,currentSeq);
      acceptorGatePort.sendAcceptedPropose(i, clientId, voteName);
    }
  }
}
