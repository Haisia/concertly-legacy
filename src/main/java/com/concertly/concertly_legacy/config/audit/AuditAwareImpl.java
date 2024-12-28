package com.concertly.concertly_legacy.config.audit;

import com.concertly.concertly_legacy.config.jwt.ConcertlyUserDetail;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() ||
      !(authentication.getPrincipal() instanceof UserDetails)) {
      return Optional.of( "AUDIT");
    } else {
      ConcertlyUserDetail userDetails = (ConcertlyUserDetail) authentication.getPrincipal();
      return Optional.of(userDetails.getUser().getId().toString());
    }
  }

}