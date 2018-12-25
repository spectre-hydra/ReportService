package softwareplant.Controller;

import com.weddini.throttling.Throttling;
import com.weddini.throttling.ThrottlingType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import softwareplant.Configuration.MappingNames;
import softwareplant.ErrorHandler.ValidationError;
import softwareplant.ErrorHandler.ValidationErrorBuilder;
import softwareplant.Model.Report.ReportRequest;
import softwareplant.Model.Report.ReportResponse;
import softwareplant.Service.ReportService;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(MappingNames.REPORT_URL)
public class ReportController {

    private static final Logger log = LogManager.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @Throttling(type = ThrottlingType.RemoteAddr, limit = 10, timeUnit = TimeUnit.SECONDS)
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void putReport(@Validated @RequestBody ReportRequest reportRequest, HttpServletRequest httpRequest) {

        log.info("putReport: " + reportRequest);
        reportService.generateReport(reportRequest);
    }

    @Throttling(type = ThrottlingType.RemoteAddr, limit = 10, timeUnit = TimeUnit.SECONDS)
    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteReport(@Validated ReportRequest reportRequest, HttpServletRequest httpRequest) {

        log.info("deleteReport: " + reportRequest);
        reportService.deleteReport(reportRequest);
    }

    @Throttling(type = ThrottlingType.RemoteAddr, limit = 10, timeUnit = TimeUnit.SECONDS)
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportResponse getReport(@Validated ReportRequest reportRequest, HttpServletRequest httpRequest) {

        log.info("getReport: " + reportRequest);

        ReportResponse response = new ReportResponse();
        response.setReportRequest(reportRequest);
        response.setResults(reportService.getReports(reportRequest));

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(MethodArgumentNotValidException exception) {
        return createValidationError(exception);
    }

    private ValidationError createValidationError(MethodArgumentNotValidException ex) {
        return ValidationErrorBuilder.fromBindingErrors(ex.getBindingResult());
    }
}
