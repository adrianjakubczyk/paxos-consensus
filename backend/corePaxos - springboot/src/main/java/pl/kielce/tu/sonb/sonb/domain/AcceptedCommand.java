package pl.kielce.tu.sonb.sonb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AcceptedCommand {
  private Integer newSequenceId;
  private String acceptedValue;
}