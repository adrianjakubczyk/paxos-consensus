package pl.kielce.tu.sonb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptedNotificationModel {

    private Integer newSequenceId;

    private String acceptedValue;

}
