package pl.kielce.tu.sonb.sonb.domain;

import lombok.Data;

@Data
public class AcceptorStateSimplifiedModel {
    private int currentError;
    private int currentSequenceNumber = 1;
}