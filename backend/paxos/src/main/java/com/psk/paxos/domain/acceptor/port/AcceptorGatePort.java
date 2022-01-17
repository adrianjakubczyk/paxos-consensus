package com.psk.paxos.domain.acceptor.port;

public interface AcceptorGatePort {
  void sendPropose(int acceptorId, String voteName, int currentSeq);

  void sendAcceptedPropose(int acceptorId, Integer clientId, String voteName);
}
