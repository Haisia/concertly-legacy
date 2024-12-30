package com.concertly.concertly_legacy.commons.utils;

public class Stopwatch {
  private Long startTime = 0L;
  private Long endTime = 0L;

  public void start() {
    startTime = System.currentTimeMillis();
    endTime = 0L;
  }

  public Long stop() {
    if (startTime == 0L) {
      throw new IllegalStateException("스탑워치를 먼저 시작해주세요.");
    }
    endTime = System.currentTimeMillis();
    return getElapsedTime();
  }

  public Long getElapsedTime() {
    if (startTime == 0L) {
      throw new IllegalStateException("스탑워치를 먼저 시작해주세요.");
    }
    if (endTime == 0L) {
      return System.currentTimeMillis() - startTime;
    }
    return endTime - startTime;
  }

  public void reset() {
    startTime = 0L;
    endTime = 0L;
  }
}