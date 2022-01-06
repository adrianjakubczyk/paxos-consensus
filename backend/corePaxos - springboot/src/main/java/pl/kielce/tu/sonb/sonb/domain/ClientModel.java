package pl.kielce.tu.sonb.sonb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClientModel {
  public static String DEFAULT_PROBLEM_DISPLAY_NAME = "No problem has ben specified by a leader";

  private boolean isLeader;
  private int sequenceNumber;
  private String currentProblemName;
  private List<String> currentProblemVotes;
}
