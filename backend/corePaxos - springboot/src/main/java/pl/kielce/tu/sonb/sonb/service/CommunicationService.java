package pl.kielce.tu.sonb.sonb.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.sonb.sonb.domain.share.VoteHistoryDTO;
import pl.kielce.tu.sonb.sonb.service.acceptor.core.CoreAcceptorPort;
import pl.kielce.tu.sonb.sonb.service.acceptor.core.CoreAcceptorVotePort;

import java.util.List;
import java.util.Objects;

@Service
public class CommunicationService {

  @Autowired
  private CoreAcceptorPort coreAcceptorPort;

  @Autowired
  private CoreAcceptorVotePort coreAcceptorVotePort;

  private List<VoteHistoryDTO> listOfVoteHistoryDTOS;

  public List<VoteHistoryDTO> readHistory() {
    return listOfVoteHistoryDTOS;
  }

  public void readSingleHistory() {
    VoteHistoryDTO voteHistoryDTO = coreAcceptorVotePort.getHistory();
    if (!Objects.isNull(voteHistoryDTO)) {
      listOfVoteHistoryDTOS.add(voteHistoryDTO);
    }
  }

  @Getter
  @AllArgsConstructor
  public enum AcceptorApi {
    ADD_NEW_PROBLEM("http://localhost:2120/acceptor/:acceptorId/add-new-problem", "http://localhost:2120/acceptor/:acceptorId/accepted-new-problem"),
    ADD_NEW_VOTE("http://localhost:2120/acceptor/:acceptorId/add-new-vote",
        "http://localhost:2120/acceptor/:acceptorId/accepted-new-vote"),
    ENABLE_ERROR("http://localhost:2120/acceptor/:acceptorId/enable-error", null),
    DISABLE_ERROR("http://localhost:2120/acceptor/:acceptorId/disable-error", null),
    FETCH_ACCEPTOR_STATE("http://localhost:2120/acceptor/:acceptorId/fetch-acceptor-state", null),
    VOTE_HISTORY("http://localhost:2120/acceptor/vote/history", null);

    private String proposeUrl;

    private String acceptedUrl;
  }
}
