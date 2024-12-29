package com.concertly.concertly_legacy.commons.validators;

import com.concertly.concertly_legacy.commons.annotations.ValidUUID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<ValidUUID, UUID> {

  @Override
  public boolean isValid(UUID value, ConstraintValidatorContext context) {
    try {
      UUID.fromString(value.toString());
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}