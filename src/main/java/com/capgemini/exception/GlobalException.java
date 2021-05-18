package com.capgemini.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // provided to write global code that can be applied to wide range of
					// controllers.
public class GlobalException {

	@ExceptionHandler(value = Exception.class) // mechanism to treat exception.
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = BrandNotFoundException.class)
	public ResponseEntity<String> handleException(BrandNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = VehicleIdNotFoundException.class)
	public ResponseEntity<String> handleException(VehicleIdNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = UserIdNotFoundException.class)
	public ResponseEntity<String> handleException(UserIdNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = VehicleAlreadyBookedException.class)
	public ResponseEntity<String> handleException(VehicleAlreadyBookedException v) {
		return new ResponseEntity<String>(v.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = VehicleAvailableException.class)
	public ResponseEntity<String> handleException(VehicleAvailableException v) {
		return new ResponseEntity<String>(v.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = QueryIdMismatchException.class)
	public ResponseEntity<String> handleException(QueryIdMismatchException q) {
		return new ResponseEntity<String>(q.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ContactIdNotFoundException.class)
	public ResponseEntity<String> handleException(ContactIdNotFoundException q) {
		return new ResponseEntity<String>(q.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ListIsEmptyException.class)
	public ResponseEntity<String> handleException(ListIsEmptyException l) {
		return new ResponseEntity<String>(l.getMessage(), HttpStatus.NOT_FOUND);
	}
}
