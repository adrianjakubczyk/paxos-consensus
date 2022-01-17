package com.psk.paxos.persistance.client;

import com.psk.paxos.domain.client.Client;
import com.psk.paxos.domain.client.port.ClientRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Service
public class ClientRepositoryAdapter implements ClientRepositoryPort {

  @Autowired
  public ClientRepositoryAdapter() {
    this.clients = fetchData();
  }

  private Collection<Client> clients;

  @Override
  public Client findById(int id) {
    return clients.stream()
        .filter(c -> Objects.equals(c.getClientId(), id))
        .findFirst()
        .orElseThrow(() -> new ClientRepositoryException("Client where id= " + id + "not exists"));
  }

  private Collection<Client> fetchData() {
    Client c1 = Client.builder().clientId(0).sequenceNumber(0).isLeader(true).problemName(Client.getDefaultProblem()).problemVotes(Collections.EMPTY_LIST).build();
    Client c2 = Client.builder().clientId(1).sequenceNumber(0).isLeader(true).problemName(Client.getDefaultProblem()).problemVotes(Collections.EMPTY_LIST).build();
    Client c3 = Client.builder().clientId(2).sequenceNumber(0).isLeader(true).problemName(Client.getDefaultProblem()).problemVotes(Collections.EMPTY_LIST).build();
    return Arrays.asList(c1, c2, c3);
  }
}
