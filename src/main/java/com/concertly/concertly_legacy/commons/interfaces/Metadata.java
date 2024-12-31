package com.concertly.concertly_legacy.commons.interfaces;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Metadata {
  UUID getId();
  String getDeleted();
  String getCreatedBy();
  LocalDateTime getCreatedAt();
  String getUpdatedBy();
  LocalDateTime getUpdatedAt();
}
