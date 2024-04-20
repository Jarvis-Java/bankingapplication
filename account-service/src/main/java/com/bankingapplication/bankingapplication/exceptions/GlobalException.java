package com.bankingapplication.bankingapplication.exceptions;


public class GlobalException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 9222952368695926126L;

	private final String errorCode;

    private final String errorMessage;

    public GlobalException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}