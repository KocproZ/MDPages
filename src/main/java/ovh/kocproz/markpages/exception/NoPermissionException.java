package ovh.kocproz.markpages.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Hubertus
 * Created 26.01.2018
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NoPermissionException extends RuntimeException{

}
