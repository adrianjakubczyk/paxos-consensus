package pl.kielce.tu.sonb.sonb.domain.share;

import lombok.*;

import java.util.List;

@Data
public class AcceptorDAOResponse {
  private Boolean requestAccepted;
  private String currentProblem;
  private List<String> currentProblemVotes;
  private Integer currentSequenceNumber;
  private Integer currentFault;
}
