package Entities;

import java.time.LocalDate;

public class BookingDates {
    private LocalDate checkin;
    private LocalDate checkout;

    public BookingDates(LocalDate checkin, LocalDate checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    @Override
    public String toString() {
        return "BookingDates{" +
                "checkin=" + checkin +
                ", checkout=" + checkout +
                '}';
    }
}
