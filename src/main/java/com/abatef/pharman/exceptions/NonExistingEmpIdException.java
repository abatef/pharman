package com.abatef.pharman.exceptions;

import com.abatef.pharman.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class NonExistingEmpIdException extends RuntimeException {
  private final Integer empId;
  public NonExistingEmpIdException(Integer empId) {
    super(Constants.Messages.NON_EXISTING_EMP_ID);
    this.empId = empId;
  }
}
