package com.psk.paxos.persistance.acceptor;

import com.psk.paxos.domain.acceptor.Acceptor;
import com.psk.paxos.domain.acceptor.port.AcceptorRepositoryPort;
import com.psk.paxos.domain.vote.VoteSession;
import com.psk.paxos.persistance.client.ClientRepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class AcceptorRepositoryAdapter implements AcceptorRepositoryPort {

  @Autowired
  public AcceptorRepositoryAdapter() {
    this.acceptors = fetchData();
  }

  private Collection<Acceptor> acceptors;

  @Override
  public VoteSession findPresentVotingSession(int acceptorId) {
    List<VoteSession> votingSessions = findById(acceptorId).getVotingSessions();
    return votingSessions.isEmpty() ? null : votingSessions.get(votingSessions.size() - 1);
  }

  @Override
  public Acceptor findById(int acceptorId) {
    return acceptors.stream()
        .filter(a -> Objects.equals(a.getAcceptorId(), acceptorId))
        .findFirst()
        .orElseThrow(() -> new ClientRepositoryException("Client where id= " + acceptorId + "not exists"));
  }

  Collection<Acceptor> fetchData() {
    return Arrays.asList(Acceptor.builder().acceptorId(0).build(), Acceptor.builder().acceptorId(1).build(), Acceptor.builder().acceptorId(2).build());
  }
}
