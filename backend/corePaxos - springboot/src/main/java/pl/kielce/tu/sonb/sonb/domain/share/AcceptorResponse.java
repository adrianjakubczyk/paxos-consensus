package pl.kielce.tu.sonb.sonb.domain.share;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptorResponse {
    private Boolean requestAccepted;
    private String currentProblem;
    private List<String> currentProblemVotes;
    private Integer currentSequenceNumber;
    private Integer currentFault;
}
