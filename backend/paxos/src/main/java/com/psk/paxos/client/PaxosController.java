package com.psk.paxos.client;

import com.psk.paxos.domain.acceptor.AcceptorResponse;
import com.psk.paxos.domain.acceptor.port.AcceptorCommunicationPort;
import com.psk.paxos.domain.client.Client;
import com.psk.paxos.domain.client.port.ClientRepositoryPort;
import com.psk.paxos.domain.vote.port.VoteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaxosController {

  private final VoteRepositoryPort voteRepositoryPort;
  private final AcceptorCommunicationPort acceptorCommunicationPort;

//  @GetMapping("/client/{id}")
//  public Client getClientDetails(@PathVariable int id) {
//    return clientRepositoryPort.findById(id);
//  }

  @PostMapping("/create-new-vote-session")
  public void createNewVoteSession(@RequestParam String voteName, @RequestParam Integer clientId) {
    voteRepositoryPort.createNewVoteSession(voteName, clientId);
  }

  @GetMapping("/acceptors")
  public List<AcceptorResponse> getAcceptors() {
    return acceptorCommunicationPort.findAcceptors();
  }

  @PostMapping("/create-vote")
  public void createNewVote(@RequestParam String vote, @RequestParam Integer clientId) {
    voteRepositoryPort.createNewVote(vote, clientId);
  }
}
