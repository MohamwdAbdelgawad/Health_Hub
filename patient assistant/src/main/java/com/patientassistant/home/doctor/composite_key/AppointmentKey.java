package com.patientassistant.home.doctor.composite_key;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Embeddable
public class AppointmentKey implements Serializable {
    private long id;
    private Long clinicId;
    private DayOfWeek day;
    private LocalTime startTime;

    // Default constructor
    public AppointmentKey() {}

    // Parameterized constructor
    public AppointmentKey(long id, Long clinicId, DayOfWeek day, LocalTime startTime) {
        this.id = id;
        this.clinicId = clinicId;
        this.day = day;
        this.startTime = startTime;
    }

    // equals() and hashCode() methods (important for composite keys)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppointmentKey that = (AppointmentKey) o;

        if (id != that.id) return false;
        if (!clinicId.equals(that.clinicId)) return false;
        if (day != that.day) return false;
        return startTime.equals(that.startTime);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + clinicId.hashCode();
        result = 31 * result + day.hashCode();
        result = 31 * result + startTime.hashCode();
        return result;
    }
}
