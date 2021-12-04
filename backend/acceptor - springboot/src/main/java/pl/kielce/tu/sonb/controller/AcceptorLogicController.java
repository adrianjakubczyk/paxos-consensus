package pl.kielce.tu.sonb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.sonb.domain.ConfirmNotificationCommand;
import pl.kielce.tu.sonb.domain.AcceptorResponse;
import pl.kielce.tu.sonb.domain.ProposeCommand;
import pl.kielce.tu.sonb.service.PaxosAcceptorAdapter;

@RestController
@RequestMapping("/acceptor/{acceptorId}")
@RequiredArgsConstructor
public class AcceptorLogicController {

    private final PaxosAcceptorAdapter paxosAcceptorAdapter;

    @PostMapping("/create-problem")
    public AcceptorResponse createNewProblem(@PathVariable Integer acceptorId, @RequestBody ProposeCommand propose) {
        if (paxosAcceptorAdapter.isSequenceCorrect(acceptorId, propose)) {
            paxosAcceptorAdapter.persistOnStage(acceptorId, propose.getMessage());
            return responseConfirm();
        }

        return responseReject();
    }

    @PostMapping("/confirm-problem")
    public void confirmNewProblem(@PathVariable Integer acceptorId, @RequestBody ConfirmNotificationCommand accepted) {
        paxosAcceptorAdapter.confirmNewVotingSession(acceptorId, accepted);
    }

    @PostMapping("/create-vote")
    public AcceptorResponse createNewVote(@PathVariable Integer acceptorId, @RequestBody ProposeCommand propose) {
        if (paxosAcceptorAdapter.isSequenceCorrect(acceptorId, propose)) {
            paxosAcceptorAdapter.persistOnStage(acceptorId, propose.getMessage());
            return responseConfirm();
        }

        return responseReject();
    }

    @PostMapping("/confirm-vote")
    public void confirmNewVote(@PathVariable Integer acceptorId, @RequestBody ConfirmNotificationCommand accepted) {
        paxosAcceptorAdapter.confirmNewVote(acceptorId, accepted);
    }

    private AcceptorResponse responseConfirm() {
        return new AcceptorResponse(true, null, null,null,null);
    }

    private AcceptorResponse responseReject() {
        return new AcceptorResponse(false, null, null,null,null);
    }
}
