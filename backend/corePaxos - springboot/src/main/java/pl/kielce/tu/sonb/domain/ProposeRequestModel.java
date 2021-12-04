package pl.kielce.tu.sonb.domain;

import lombok.*;


@AllArgsConstructor
public class ProposeRequestModel {
    private String message;
    private Integer sequenceNumber;
}
