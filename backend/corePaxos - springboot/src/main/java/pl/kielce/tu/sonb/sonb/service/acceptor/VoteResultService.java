package pl.kielce.tu.sonb.sonb.service.acceptor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.sonb.sonb.domain.share.AcceptorResponse;
import pl.kielce.tu.sonb.sonb.domain.share.VoteHistoryDTO;
import pl.kielce.tu.sonb.sonb.service.acceptor.core.CoreAcceptorPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class VoteResultService {

  private CoreAcceptorPort coreAcceptorPort;

  public VoteHistoryDTO getVoteResult() {
    List<String> votes = new ArrayList<>();
    VoteHistoryDTO resultModel = new VoteHistoryDTO();
    for (int i = 0; i < 3; i++) {
      AcceptorResponse acceptorResponseModel = coreAcceptorPort.fetchState(i);
      if (!Objects.isNull(acceptorResponseModel.getCurrentProblemVotes())) {
        votes.addAll(acceptorResponseModel.getCurrentProblemVotes());
      }
      if (!Objects.isNull(acceptorResponseModel.getCurrentProblem())) {
        resultModel.setCurrentProblem(acceptorResponseModel.getCurrentProblem());
      } else {
        resultModel.setCurrentProblem("");
      }
    }
    resultModel.setCurrentProblemVotes(votes);
    resultModel.setFinalVoteOfAcceptors(compareQuorumOfVotes(votes));
    return resultModel;
  }

  private String compareQuorumOfVotes(List<String> votes) {
    long voteYes = votes.stream().filter("Tak"::equals).count();
    long voteNo = votes.stream().filter("Nie"::equals).count();

    if (voteYes > voteNo) {
      return "Tak";
    } else if (voteNo > voteYes) {
      return "Nie";
    } else {
      return "Nie rozstrzygnięto";
    }
  }
}
