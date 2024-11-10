package study;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class LocalDateTimeTest {

    @Test
    void 시간_날짜_생성() {
        String start = "2024-01-01";
        String end = "2024-12-31";

        LocalDateTime startDateTime = LocalDateTime.of(
                LocalDate.parse(start), LocalTime.MIDNIGHT
        );

        assertThat(startDateTime).isAfter(LocalDateTime.of(2023, 12, 31, 23, 59, 59));
        assertThat(startDateTime).isAfterOrEqualTo(LocalDateTime.of(2023, 1, 1, 0, 0, 0));
        assertThat(startDateTime.toLocalTime()).isEqualTo("00:00:00");
    }

}
