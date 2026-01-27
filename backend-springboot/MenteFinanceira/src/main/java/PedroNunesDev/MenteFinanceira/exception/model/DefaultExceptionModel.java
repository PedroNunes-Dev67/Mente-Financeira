package PedroNunesDev.MenteFinanceira.exception.model;

import java.time.Instant;
import java.util.Objects;

public class DefaultExceptionModel {

    private Instant moment;
    private String error;
    private Integer status;
    private String message;

    public DefaultExceptionModel() {
    }

    public DefaultExceptionModel(Instant moment, String error, Integer status, String message) {
        this.moment = moment;
        this.error = error;
        this.status = status;
        this.message = message;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultExceptionModel that = (DefaultExceptionModel) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(status);
    }
}
