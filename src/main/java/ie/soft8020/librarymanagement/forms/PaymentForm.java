package ie.soft8020.librarymanagement.forms;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class PaymentForm {

    @NotNull(message = "please enter member id")
    @Range(min = 0l, message = "Please select positive numbers Only")
    @Digits(integer=1, fraction=0, message = "member id can only be 1 digit")
    private Integer memberID;

    @Range(min = 0l, message = "Please select positive numbers Only")
    private double finesOutstanding;

    private String name;

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFinesOutstanding() {
        return finesOutstanding;
    }

    public void setFinesOutstanding(double finesOutstanding) {
        this.finesOutstanding = finesOutstanding;
    }
}
