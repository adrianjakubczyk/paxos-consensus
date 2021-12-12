package pl.kielce.tu.sonb.sonb.service.acceptor.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.sonb.sonb.domain.share.VoteHistoryDTO;
import pl.kielce.tu.sonb.sonb.service.acceptor.VoteResultService;

@Service
public class CoreAcceptorVotePort {
  @Autowired
  private VoteResultService voteResultService;


  public VoteHistoryDTO getHistory() {
    return voteResultService.getVoteResult();
  }
}
