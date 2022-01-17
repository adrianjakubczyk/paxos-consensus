package com.psk.paxos.client;

import com.psk.paxos.domain.client.Client;
import com.psk.paxos.domain.client.port.ClientRepositoryPort;
import com.psk.paxos.domain.vote.port.VoteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaxosController {
  private final ClientRepositoryPort clientRepositoryPort;
  private final VoteRepositoryPort voteRepositoryPort;

  @GetMapping("/client/{id}")
  public Client getClientDetails(@PathVariable int id) {
    return clientRepositoryPort.findById(id);
  }

  @PostMapping("/create-new-vote-session")
  public void createNewVote(@RequestParam String voteName, @RequestParam Integer clientId) {
    voteRepositoryPort.createNewVoteSession(voteName, clientId);
  }
}
