package pl.kielce.tu.sonb.domain;

import lombok.*;

@Data
public class ConfirmRequestCommand {
    private int newSequenceId;
    private String acceptedValue;
}