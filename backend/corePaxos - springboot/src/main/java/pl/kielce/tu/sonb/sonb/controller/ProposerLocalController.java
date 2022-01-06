package pl.kielce.tu.sonb.sonb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.sonb.sonb.domain.ClientModel;
import pl.kielce.tu.sonb.sonb.domain.share.AcceptorDAOResponse;
import pl.kielce.tu.sonb.sonb.domain.share.VoteHistoryDTO;
import pl.kielce.tu.sonb.sonb.service.AcceptorDAO;
import pl.kielce.tu.sonb.sonb.service.ProposerService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/proposer")
@RequiredArgsConstructor
public class ProposerLocalController {
  @Autowired
  private AcceptorDAO acceptorDAO;

  @Autowired
  private final ProposerService proposerService;

  @GetMapping("/vote-history")
  public List<VoteHistoryDTO> getVoteHistory() {
    return acceptorDAO.readHistory();
  }

  @GetMapping("/get-single-history")
  public void getSingleHistory() {
    acceptorDAO.readSingleHistory();
  }


  @GetMapping("/client/{clientId}")
  public ClientModel getClientData(@PathVariable Integer clientId) {
    return proposerService.getClientData(clientId);
  }

  @GetMapping("/state-of-acceptors")
  public List<AcceptorDAOResponse> getStateOfAcceptors() {
    return Stream.of(acceptorDAO.readStateOfAcceptor(0), acceptorDAO.readStateOfAcceptor(1), acceptorDAO.readStateOfAcceptor(2)).collect(Collectors.toList());
  }

  @GetMapping("/initialize-clients")
  public void initializeClients() {
    proposerService.addNewClients();
  }

  @PostMapping("/start-new-voting-problem")
  public void startNewVotingProblem(@RequestParam String problemName, @RequestParam Integer clientId) {
    proposerService.startNewVotingProblem(problemName, clientId);
  }

  @PostMapping("/add-new-vote")
  public void addNewVote(@RequestParam String vote, @RequestParam Integer clientId) {
    proposerService.addNewVote(vote, clientId);
  }

  @PostMapping("/enable-acceptor-error")
  public void enableAcceptorError(@RequestParam Integer acceptorId, @RequestParam Integer errorType) {
    acceptorDAO.sendEnableErrorRequest(acceptorId, errorType);
  }

  @PostMapping("/disable-acceptor-error")
  public void enableAcceptorError(@RequestParam Integer acceptorId) {
    acceptorDAO.sendDisableErrorRequest(acceptorId);
  }
}