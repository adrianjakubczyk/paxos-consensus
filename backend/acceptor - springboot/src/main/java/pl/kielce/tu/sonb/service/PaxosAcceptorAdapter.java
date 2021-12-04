package pl.kielce.tu.sonb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.sonb.domain.*;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaxosAcceptorAdapter {

  private final GlobalStateAdapter globalStateAdapter;
  private BigInteger prevSeqNumber = BigInteger.valueOf(0);
  private List<Integer> seqNumberError = new LinkedList<Integer>();

  public AcceptorResponse getStateDto(Integer acceptorId) {
    AcceptorDTO acceptor = globalStateAdapter.getAcceptor(acceptorId);

    prevSeqNumber = BigInteger.valueOf(acceptor.getCurrentSequenceNumber());
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

    VotingSessionDTO currentVotingSessionDTO = globalStateAdapter.getCurrentVotingSession(acceptorId);
    if (Objects.isNull(currentVotingSessionDTO)) {

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
        currentVotingSessionDTO.getCurrentProblem(),
        currentVotingSessionDTO.getVotes(),
        acceptor.getCurrentSequenceNumber(),
        acceptor.getCurrentError()
    );

  }

  public boolean isSequenceCorrect(Integer acceptorId, ProposeCommand requestModel) {
    prevSeqNumber = BigInteger.valueOf(globalStateAdapter.getAcceptor(acceptorId).getCurrentSequenceNumber());
    return globalStateAdapter.getAcceptor(acceptorId).getCurrentSequenceNumber() == requestModel.getSequenceNumber();
  }

  public void confirmNewVotingSession(Integer acceptorId, ConfirmNotificationCommand accepted) {
    AcceptorDTO acceptor = globalStateAdapter.getAcceptor(acceptorId);

    acceptor.getVotingSessionDTOS().add(new VotingSessionDTO(accepted.getAcceptedValue()));
    acceptor.setCurrentSequenceNumber(accepted.getNewSequenceId());
  }

  public void confirmNewVote(Integer acceptorId, ConfirmNotificationCommand accepted) {
    AcceptorDTO acceptor = globalStateAdapter.getAcceptor(acceptorId);

    if (isCorrectMessage(accepted, acceptor)) {
      acceptor.getCurrentVotingSession().getVotes().add(accepted.getAcceptedValue());
      acceptor.setCurrentSequenceNumber(accepted.getNewSequenceId());
    }
  }

  private boolean isCorrectMessage(ConfirmNotificationCommand accepted, AcceptorDTO acceptor) {
    return acceptor.getCurrentSequenceNumber().equals(accepted.getNewSequenceId()) && acceptor.getCurrentError() == 0;
  }

  public void persistOnStage(Integer acceptorId, String message) {
    AcceptorDTO acceptor = globalStateAdapter.getAcceptor(acceptorId);
    acceptor.setStagingArea(message);
  }
}
