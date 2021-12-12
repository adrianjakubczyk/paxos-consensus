package pl.kielce.tu.sonb.sonb.service.acceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.sonb.sonb.domain.acceptor.ConfirmNotificationCommand;
import pl.kielce.tu.sonb.sonb.domain.share.AcceptorResponse;
import pl.kielce.tu.sonb.sonb.domain.share.ProposeCommand;
import pl.kielce.tu.sonb.sonb.domain.acceptor.AcceptorDTO;
import pl.kielce.tu.sonb.sonb.domain.acceptor.VotingSessionDTO;


import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AcceptorPaxosLogicService {

    private final GlobalStateAdapter acceptorAppState;
    private BigInteger prevSeqNumber = BigInteger.valueOf(0);
    private List<Integer> seqNumberError = new LinkedList<Integer>();

    public AcceptorResponse getStateDto(Integer acceptorId) {
        AcceptorDTO acceptor = acceptorAppState.getAcceptor(acceptorId);

        prevSeqNumber = BigInteger.valueOf(acceptor.getCurrentSequenceNumber());

        //TODO: Store the previous sequence number and return it during a fault. //MM
        if (acceptor.getCurrentError() == 1) {

            seqNumberError.add(acceptor.getCurrentSequenceNumber());
            if (seqNumberError.size() > 1) {
                seqNumberError.remove(1);
            }
            return new AcceptorResponse(
                    true,
                    null,
                    null,
                    seqNumberError.get(0),
                    acceptor.getCurrentError()
            );
        } else if (acceptor.getCurrentError() == 2) {

            Random rand = new Random();
            return new AcceptorResponse(
                    true,
                    null,
                    null,
                    rand.nextInt(999),
                    acceptor.getCurrentError()
            );
        }

        VotingSessionDTO currentVotingSession = acceptorAppState.getCurrentVotingSession(acceptorId);
        if (/*acceptor.getCurrentError()!=0 ||*/ Objects.isNull(currentVotingSession)) {

            return new AcceptorResponse(
                    true,
                    null,
                    null,
                    prevSeqNumber.intValue(),
                    acceptor.getCurrentError()
            );
        }

        return new AcceptorResponse(
                true,
                currentVotingSession.getCurrentProblem(),
                currentVotingSession.getVotes(),
                acceptor.getCurrentSequenceNumber(),
                acceptor.getCurrentError()
        );

    }

    public boolean isSequenceCorrect(Integer acceptorId, ProposeCommand requestModel) {
        prevSeqNumber = BigInteger.valueOf(acceptorAppState.getAcceptor(acceptorId).getCurrentSequenceNumber());
        return acceptorAppState.getAcceptor(acceptorId).getCurrentSequenceNumber() == requestModel.getSequenceNumber();
    }

    public void acceptNewVotingSession(Integer acceptorId, ConfirmNotificationCommand accepted) {
        AcceptorDTO acceptor = acceptorAppState.getAcceptor(acceptorId);
            acceptor.getVotingSessionDTOS().add(new VotingSessionDTO(accepted.getAcceptedValue()));
            acceptor.setCurrentSequenceNumber(accepted.getNewSequenceId());
    }

    public void acceptNewVote(Integer acceptorId, ConfirmNotificationCommand accepted) {
        AcceptorDTO acceptor = acceptorAppState.getAcceptor(acceptorId);

        if (shouldAcceptMsg(accepted, acceptor)) {
            acceptor.getCurrentVotingSession().getVotes().add(accepted.getAcceptedValue());
            acceptor.setCurrentSequenceNumber(accepted.getNewSequenceId());
        }

    }

    private boolean shouldAcceptMsg(ConfirmNotificationCommand accepted, AcceptorDTO acceptor) {

        //TODO jeśli awaria to nie może przesłać głosu
/*
        if (!acceptor.getStagingArea().equals(accepted.getAcceptedValue())) {

            throw new IllegalStateException("Cannot accept request if proposed value was different");
        }
*/

        return acceptor.getCurrentSequenceNumber().equals(accepted.getNewSequenceId()) && acceptor.getCurrentError() == 0;

    }

    public void enableError(Integer acceptorId, ProposeCommand propose) {
        AcceptorDTO acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setCurrentError(Integer.valueOf(propose.getMessage()));
    }

    public void disableError(Integer acceptorId) {
        seqNumberError.clear();
        AcceptorDTO acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setCurrentError(0);
    }

    public void addToStaging(Integer acceptorId, String message) {
        AcceptorDTO acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setStagingArea(message);
    }
}