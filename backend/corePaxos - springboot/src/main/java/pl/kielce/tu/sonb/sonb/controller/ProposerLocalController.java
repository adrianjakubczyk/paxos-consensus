package pl.kielce.tu.sonb.sonb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.sonb.sonb.domain.share.VoteHistoryDTO;
import pl.kielce.tu.sonb.sonb.service.CommunicationService;

import java.util.List;

@RestController
@RequestMapping("/proposer")
@RequiredArgsConstructor
public class ProposerLocalController {
  @Autowired
  private CommunicationService communicationService;

  @GetMapping("/vote-history")
  public List<VoteHistoryDTO> getVoteHistory() {
    return communicationService.readHistory();
  }

  @GetMapping("/get-single-history")
  public void getSingleHistory() {
    communicationService.readSingleHistory();
  }
}