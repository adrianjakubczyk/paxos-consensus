package com.psk.paxos.domain.client.port;

import com.psk.paxos.domain.client.Client;

public interface ClientRepositoryPort {
  Client findById(int id);
}
