package pl.kielce.tu.sonb.sonb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.sonb.sonb.domain.AcceptedCommand;
import pl.kielce.tu.sonb.sonb.domain.ClientModel;
import pl.kielce.tu.sonb.sonb.domain.share.AcceptorDAOResponse;
import pl.kielce.tu.sonb.sonb.domain.share.ProposeCommand;
import pl.kielce.tu.sonb.sonb.service.AcceptorDAO.AcceptorMicroserviceAPI;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProposerService {

  @Autowired
  private final ProposerCache proposerCache;
  @Autowired
  private final AcceptorDAO acceptorDAO;
  private Integer seqNumber = 0;

  public void startNewVotingProblem(String problemName, Integer clientId) {
    ProposeCommand proposeRequestModel = new ProposeCommand(problemName, seqNumber);
    seqNumber++;
    sendProposeAndHandlePaxos(clientId, AcceptorMicroserviceAPI.ADD_NEW_PROBLEM, proposeRequestModel);
  }

  public void addNewVote(String vote, Integer clientId) {
    ProposeCommand proposeRequestModel = new ProposeCommand(vote,
        proposerCache.getCurrentClient(clientId).getSequenceNumber());

    sendProposeAndHandlePaxos(clientId, AcceptorMicroserviceAPI.ADD_NEW_VOTE, proposeRequestModel);
  }

  private void sendProposeAndHandlePaxos(Integer clientId, AcceptorMicroserviceAPI operation, ProposeCommand proposeRequestModel) {
    for (int i = 0; i < 3; i++) {
      sendProposeToAcceptor(i, operation, proposeRequestModel);
      proposeRequestsAccepted(i, clientId, operation, proposeRequestModel.getMessage());
    }
  }

  private boolean sendProposeToAcceptor(int acceptorId, AcceptorMicroserviceAPI operation, ProposeCommand proposeRequestModel) {
    String effectiveUrl = acceptorDAO.processToEffectiveUrl(operation.getProposeUrl(), acceptorId);
    return acceptorDAO.sendProposeAndAwaitResponse(effectiveUrl, proposeRequestModel);
  }

  private void proposeRequestsAccepted(int acceptorId, Integer clientId, AcceptorMicroserviceAPI operation, String acceptedMessage) {
    ClientModel currentClient = proposerCache.getCurrentClient(clientId);
    currentClient.setSequenceNumber(seqNumber);
    String effectiveUrl = acceptorDAO.processToEffectiveUrl(operation.getAcceptedUrl(), acceptorId);

    AcceptedCommand acceptedCommand = new AcceptedCommand(currentClient.getSequenceNumber(), acceptedMessage);
    acceptorDAO.sendAccepted(effectiveUrl, acceptedCommand);
  }

  public ClientModel getClientData(Integer clientId) {
    AcceptorDAOResponse model = acceptorDAO.readStateOfAcceptor(0);
    ClientModel currentClient = proposerCache.getClients().get(clientId);
    currentClient.setCurrentProblemName(model.getCurrentProblem());
    currentClient.setCurrentProblemVotes(model.getCurrentProblemVotes());
    return currentClient;
  }

  public void addNewClients() {
    proposerCache.getClients().clear();
    List<ClientModel> clients = new ArrayList<>();
    clients.add(new ClientModel(true, seqNumber, ClientModel.DEFAULT_PROBLEM_DISPLAY_NAME, new ArrayList<>()));
    clients.add(new ClientModel(true, seqNumber, ClientModel.DEFAULT_PROBLEM_DISPLAY_NAME, new ArrayList<>()));
    clients.add(new ClientModel(true, seqNumber, ClientModel.DEFAULT_PROBLEM_DISPLAY_NAME, new ArrayList<>()));
    proposerCache.setClients(clients);
  }
}
