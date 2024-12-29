package com.concertly.concertly_legacy.commons.constants;

public class DtoMetadataConstants {
  // === 공통 ===
  public static final String ID_SCHEMA_DESCRIPTION = "데이타 식별 값";
  public static final String ID_SCHEMA_EXAMPLE = "123e4567-e89b-12d3-a456-426614174000";
  public static final String ID_PATTERN_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
  public static final String ID_PATTERN_MESSAGE = "ID 필드의 형식이 올바르지 않습니다.";

  public static final String CREATED_AT_DESCRIPTION = "유저 정보 생성 시각";

  public static final String UPDATED_AT_DESCRIPTION = "유저 정보 수정 시각";

  public static final String JWT_SCHEMA_DESCRIPTION = "JWT 토큰";
  public static final String JWT_SCHEMA_EXAMPLE = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

  // === 유저 정보 ===
  public static final String USER_EMAIL_SCHEMA_DESCRIPTION = "유저의 이메일 정보.";
  public static final String USER_EMAIL_SCHEMA_EXAMPLE = "test@test.com";
  public static final String USER_EMAIL_PATTERN_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
  public static final String USER_EMAIL_PATTERN_MESSAGE = "email 필드의 형식이 올바르지 않습니다.";

  public static final String USER_PASSWORD_SCHEMA_DESCRIPTION = "유저의 비밀번호 정보.";
  public static final String USER_PASSWORD_SCHEMA_EXAMPLE = "123123";
  public static final String USER_PASSWORD_PATTERN_REGEX = ".{6,}";
  public static final String USER_PASSWORD_PATTERN_MESSAGE = "password 필드는 6글자 이상이어야 합니다.";

  public static final String USER_POINT_SCHEMA_DESCRIPTION = "유저의 보유 포인트 정보.";
  public static final String USER_POINT_SCHEMA_EXAMPLE = "1000";
  public static final String USER_POINT_PATTERN_REGEX = "^[1-9][0-9]*$";
  public static final String USER_POINT_PATTERN_MESSAGE = "포인트 필드는 0보다 큰 정수여야 합니다.";

  // === 콘서트 정보 ===
  public static final String CONCERT_TITLE_SCHEMA_DESCRIPTION = "콘서트 이름 정보.";
  public static final String CONCERT_TITLE_SCHEMA_EXAMPLE = "［2024-25 Theatre 이문세］ - 울산";
  public static final String CONCERT_TITLE_PATTERN_REGEX = "^.{2,}$";
  public static final String CONCERT_TITLE_PATTERN_MESSAGE = "콘서트 이름은 2글자 이상이어야 합니다.";

  public static final String CONCERT_LOCATION_SCHEMA_DESCRIPTION = "콘서트 장소 정보.";
  public static final String CONCERT_LOCATION_SCHEMA_EXAMPLE = "서울 올림픽공원 88잔디마당";
  public static final String CONCERT_LOCATION_PATTERN_REGEX = "^.{2,}$";
  public static final String CONCERT_LOCATION_PATTERN_MESSAGE = "콘서트 장소는 2글자 이상이어야 합니다.";

  public static final String CONCERT_SEAT_NUMBER_SCHEMA_DESCRIPTION = "콘서트 좌석 번호 정보.";
  public static final String CONCERT_SEAT_NUMBER_SCHEMA_EXAMPLE = "A1";
  public static final String CONCERT_SEAT_NUMBER_PATTERN_REGEX = "^[A-Z][1-9][0-9]*$";
  public static final String CONCERT_SEAT_NUMBER_PATTERN_MESSAGE = "좌석 번호는 대문자 알파벳 1개와 0이 아닌 숫자의 조합이어야 합니다.";

  public static final String CONCERT_SEAT_PRICE_SCHEMA_DESCRIPTION = "콘서트 좌석 가격 정보.";
  public static final String CONCERT_SEAT_PRICE_SCHEMA_EXAMPLE = "50000";
  public static final String CONCERT_SEAT_PRICE_PATTERN_REGEX = "^[1-9][0-9]*$";
  public static final String CONCERT_SEAT_PRICE_PATTERN_MESSAGE = "좌석 가격은 0보다 큰 숫자여야 합니다.";

  public static final String CONCERT_START_TIME_SCHEMA_DESCRIPTION = "콘서트 시작 시간 정보.";
  public static final String CONCERT_START_TIME_SCHEMA_EXAMPLE = "2024-12-31T20:00:00";
  public static final String CONCERT_START_TIME_PATTERN_MESSAGE = "시작 시간은 'yyyy-MM-ddTHH:mm:ss' 형식이며, 현재보다 미래여야 합니다.";

  public static final String CONCERT_END_TIME_SCHEMA_DESCRIPTION = "콘서트 종료 시간 정보.";
  public static final String CONCERT_END_TIME_SCHEMA_EXAMPLE = "2024-12-31T23:00:00";
  public static final String CONCERT_END_TIME_PATTERN_MESSAGE = "종료 시간은 'yyyy-MM-ddTHH:mm:ss' 형식이며, 현재보다 미래여야 합니다.";

  public static final String CONCERT_COMMENT_SCHEMA_DESCRIPTION = "콘서트 댓글 정보.";
  public static final String CONCERT_COMMENT_SCHEMA_EXAMPLE = "굉장히 좋은 공연이었습니다!";
  public static final String CONCERT_COMMENT_PATTERN_REGEX = "^.{2,}$";
  public static final String CONCERT_COMMENT_PATTERN_MESSAGE = "댓글은 최소 2글자 이상이어야 합니다.";

  // seatLabel 관련 상수
  public static final String SEAT_LABEL_SCHEMA_DESCRIPTION = "좌석 구역 정보 (A-Z)";
  public static final String SEAT_LABEL_SCHEMA_EXAMPLE = "A";
  public static final String SEAT_LABEL_PATTERN_REGEX = "^[A-Z]$"; // A-Z 중 하나
  public static final String SEAT_LABEL_PATTERN_MESSAGE = "좌석 구역 이름은 대문자 A에서 Z 중 하나여야 합니다.";

  public static final String SEAT_MAX_LINE_NUMBER_SCHEMA_DESCRIPTION = "좌석 행의 최대 번호 (1 이상)";
  public static final String SEAT_MAX_LINE_NUMBER_SCHEMA_EXAMPLE = "10";
  public static final long SEAT_MAX_LINE_NUMBER_MIN_VALUE = 1;
  public static final String SEAT_MAX_LINE_NUMBER_MIN_MESSAGE = "좌석의 최대 행 번호는 1 이상이어야 합니다.";

  // === 프로젝트 정보 ====
  public static final String RESERVATION_AT_SCHEMA_DESCRIPTION = "예약 시간 정보.";
  public static final String RESERVATION_AT_SCHEMA_EXAMPLE = "2024-12-31T15:00:00";
  public static final String RESERVATION_AT_PATTERN_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";
  public static final String RESERVATION_AT_PATTERN_MESSAGE = "예약 시간은 'yyyy-MM-ddTHH:mm:ss' 형식이어야 합니다.";

  private DtoMetadataConstants() {
  }
}
