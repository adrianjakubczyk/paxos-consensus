package pl.kielce.tu.sonb.sonb.domain.share;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ProposeCommand {
  private String message;
  private Integer sequenceNumber;
}
