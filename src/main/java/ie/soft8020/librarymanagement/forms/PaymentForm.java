package ie.soft8020.librarymanagement.forms;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class PaymentForm {

    @NotNull(message = "please enter member id")
    @Range(min = 1, max = 9, message = "Please select positive numbers only, in the range of 1 - 9")
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
