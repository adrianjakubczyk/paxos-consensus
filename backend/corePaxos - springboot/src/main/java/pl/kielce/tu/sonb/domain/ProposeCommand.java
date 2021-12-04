package pl.kielce.tu.sonb.domain;

import lombok.*;


@AllArgsConstructor
public class ProposeCommand {
    private String message;
    private Integer sequenceNumber;
}
