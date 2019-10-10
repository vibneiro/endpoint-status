package com.ivan.endpointstats.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ThreadSafe
@JsonIgnoreProperties(ignoreUnknown=true)
public class Status {
    private Integer requests_count;
    private String application;
    private String version;
    private Integer success_count;
    private Integer error_count;

    public Integer getRequests_count() {
        return requests_count;
    }

    public void setRequests_count(Integer requests_count) {
        this.requests_count = requests_count;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getSuccess_count() {
        return success_count;
    }

    public void setSuccess_count(Integer success_count) {
        this.success_count = success_count;
    }

    public Integer getError_count() {
        return error_count;
    }

    public void setError_count(Integer error_count) {
        this.error_count = error_count;
    }

    @Override public String toString() {
        return "Status{" +
                "requests_count=" + requests_count +
                ", application='" + application + '\'' +
                ", version='" + version + '\'' +
                ", success_count=" + success_count +
                ", error_count=" + error_count +
                '}';
    }
}
