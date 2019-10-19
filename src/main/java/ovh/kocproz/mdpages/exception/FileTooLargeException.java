package ovh.kocproz.mdpages.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author KocproZ
 * Created 2018-01-15 at 07:58
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class FileTooLargeException extends RuntimeException {
}
