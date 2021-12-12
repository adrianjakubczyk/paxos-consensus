package pl.kielce.tu.sonb.sonb.domain.acceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmNotificationCommand {
    private Integer newSequenceId;
    private String acceptedValue;
}
