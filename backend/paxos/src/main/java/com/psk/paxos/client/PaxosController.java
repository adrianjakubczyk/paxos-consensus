package com.psk.paxos.client;

import com.psk.paxos.domain.acceptor.AcceptorResponse;
import com.psk.paxos.domain.acceptor.port.AcceptorCommunicationPort;
import com.psk.paxos.domain.vote.Vote;
import com.psk.paxos.domain.vote.port.VoteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public Collection<AcceptorResponse> getAcceptors() {
        return acceptorCommunicationPort.findAcceptors();
    }

    @PostMapping("/create-vote")
    public void createNewVote(@RequestParam String vote, @RequestParam Integer clientId) {
        voteRepositoryPort.createNewVote(vote, clientId);
    }

    @GetMapping("/votes")
    public Collection<Vote> getVoteHistory() {
        return voteRepositoryPort.findAllHistoryVote();
    }

    @PostMapping("/create-error")
    public void enableAcceptorError(@RequestParam Integer acceptorId, @RequestParam Integer errorType) {
        acceptorCommunicationPort.createErrorOnAcceptor(acceptorId, errorType);
    }

    @PostMapping("/delete-error")
    public void disableAcceptorError(@RequestParam Integer acceptorId) {
        acceptorCommunicationPort.removeErrorOnAcceptor(acceptorId);
    }
}
