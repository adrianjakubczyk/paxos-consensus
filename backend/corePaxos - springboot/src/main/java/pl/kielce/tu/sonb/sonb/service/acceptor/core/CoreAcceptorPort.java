package pl.kielce.tu.sonb.sonb.service.acceptor.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.kielce.tu.sonb.sonb.domain.acceptor.ConfirmNotificationCommand;
import pl.kielce.tu.sonb.sonb.domain.share.AcceptorResponse;
import pl.kielce.tu.sonb.sonb.domain.share.ProposeCommand;
import pl.kielce.tu.sonb.sonb.service.acceptor.AcceptorPaxosLogicService;

@Service
public class CoreAcceptorPort {

  @Autowired
  private AcceptorPaxosLogicService acceptorLogic;

  public AcceptorResponse addNewProblem(@PathVariable Integer acceptorId, @RequestBody ProposeCommand propose) {
    if (acceptorLogic.isSequenceCorrect(acceptorId, propose)) {
      acceptorLogic.addToStaging(acceptorId, propose.getMessage());
      return responseSimpleAccepted();
    }

    return responseSimpleReject();
  }


  public void acceptedNewProblem(@PathVariable Integer acceptorId, @RequestBody ConfirmNotificationCommand accepted) {
    acceptorLogic.acceptNewVotingSession(acceptorId, accepted);
  }


  public AcceptorResponse addNewVote(@PathVariable Integer acceptorId, @RequestBody ProposeCommand propose) {
    if (acceptorLogic.isSequenceCorrect(acceptorId, propose)) {
      acceptorLogic.addToStaging(acceptorId, propose.getMessage());
      return responseSimpleAccepted();
    }

    return responseSimpleReject();
  }


  public void acceptedNewVote(@PathVariable Integer acceptorId, @RequestBody ConfirmNotificationCommand accepted) {
    acceptorLogic.acceptNewVote(acceptorId, accepted);
  }


  public void enableError(@PathVariable Integer acceptorId, @RequestBody ProposeCommand propose) {
    acceptorLogic.enableError(acceptorId, propose);
  }


  public void disableError(@PathVariable Integer acceptorId) {
    acceptorLogic.disableError(acceptorId);
  }


  public AcceptorResponse fetchState(@PathVariable Integer acceptorId) {
    return acceptorLogic.getStateDto(acceptorId);
  }

  private AcceptorResponse responseSimpleAccepted() {
    return new AcceptorResponse(true, null, null, null, null);
  }

  private AcceptorResponse responseSimpleReject() {
    return new AcceptorResponse(false, null, null, null, null);
  }
}
