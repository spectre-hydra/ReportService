package softwareplant.Model.Report;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReportResponse {

    private ReportRequest reportRequest;
    private List<Report> results;

    public ReportResponse(){}

    public ReportResponse(ReportRequest reportRequest, List<Report> results) {
        this.reportRequest = reportRequest;
        this.results = results;
    }

    @JsonProperty("criteria")
    public ReportRequest getReportRequest() {
        return reportRequest;
    }

    public void setReportRequest(ReportRequest reportRequest) {
        this.reportRequest = reportRequest;
    }

    @JsonProperty("results")
    public List<Report> getResults() {
        return results;
    }

    public void setResults(List<Report> results) {
        this.results = results;
    }
}
