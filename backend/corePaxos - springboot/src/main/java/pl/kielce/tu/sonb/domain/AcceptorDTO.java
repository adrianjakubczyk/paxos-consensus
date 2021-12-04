package pl.kielce.tu.sonb.domain;


import lombok.*;

import java.util.List;

@Data
public class AcceptorDTO {
    private Boolean requestAccepted;
    private String currentProblem;
    private List<String> currentProblemVotes;
    private int currentSequenceNumber;
    private int currentFault;
}
