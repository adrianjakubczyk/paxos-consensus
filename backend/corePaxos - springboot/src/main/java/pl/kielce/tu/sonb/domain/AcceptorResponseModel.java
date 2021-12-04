package pl.kielce.tu.sonb.domain;

import lombok.*;

import java.util.List;

@Data
public class AcceptorResponseModel {
    private boolean requestAccepted;
    private String currentProblem;
    private List<String> currentProblemVotes;
    private int currentSequenceNumber;
    private int currentFault;
}
