package com.arka.supplier_service.infraestructure.config;


import com.arka.supplier_service.domain.exception.SupplierReceiptFilterNoMatchException;
import com.arka.supplier_service.domain.exception.SupplierReceiptNotFoundBySupplierNameException;
import com.arka.supplier_service.domain.exception.supplier.SupplierNotFoundByNameException;
import com.arka.supplier_service.domain.exception.supplier.SupplierNotFoundException;
import com.arka.supplier_service.domain.exception.supplierOrder.*;
import com.arka.supplier_service.domain.exception.supplierOrderRecipt.DuplicateSupplierReceiptForOrderException;
import com.arka.supplier_service.domain.exception.supplierOrderRecipt.SupplierReceiptNotFoundByDateRangeException;
import com.arka.supplier_service.domain.exception.supplierOrderRecipt.SupplierReceiptNotFoundByOrderCodeException;
import com.arka.supplier_service.domain.exception.supplierOrderRecipt.SupplierReceiptNotFoundByReceivedByException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SupplierReceiptNotFoundByOrderCodeException.class)
    public ResponseEntity<ErrorResponse> handleReceiptNotFoundByOrderCode(SupplierReceiptNotFoundByOrderCodeException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIERRECEIPT-ORDERCODE-NOTFOUND-404")
                .validationErrors(null)
                .build());
    }

    @ExceptionHandler(SupplierReceiptNotFoundByReceivedByException.class)
    public ResponseEntity<ErrorResponse> handleReceiptNotFoundByReceivedBy(SupplierReceiptNotFoundByReceivedByException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIERRECEIPT-RECEIVEDBY-NOTFOUND-404")
                .validationErrors(null)
                .build());
    }

    @ExceptionHandler(SupplierReceiptNotFoundByDateRangeException.class)
    public ResponseEntity<ErrorResponse> handleReceiptNotFoundByDateRange(SupplierReceiptNotFoundByDateRangeException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIERRECEIPT-DATE-RANGE-NOTFOUND-404")
                .validationErrors(null)
                .build());
    }

    @ExceptionHandler(SupplierReceiptNotFoundBySupplierNameException.class)
    public ResponseEntity<ErrorResponse> handleReceiptNotFoundBySupplierName(SupplierReceiptNotFoundBySupplierNameException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIERRECEIPT-SUPPLIERNAME-NOTFOUND-404")
                .validationErrors(null)
                .build());
    }

    @ExceptionHandler(SupplierOrderNotFoundByIdException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundById(SupplierOrderNotFoundByIdException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIERORDER-NOTFOUND-404")
                .validationErrors(null)
                .build());
    }
    @ExceptionHandler(DuplicateSupplierReceiptForOrderException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateReceiptForOrder(DuplicateSupplierReceiptForOrderException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .error(HttpStatus.CONFLICT.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message(ex.getMessage())
                        .errorCode("SUPPLIERRECEIPT-DUPLICATE-409")
                        .validationErrors(null)
                .build());
    }

    @ExceptionHandler(SkuNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSkuNotFoundException(SkuNotFoundException ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIERORDER-SKU-NOTFOUND-404")
                .validationErrors(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }



    @ExceptionHandler(SupplierOrderNotFoundByCodeException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundByCode(SupplierOrderNotFoundByCodeException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIERORDER-CODE-NOTFOUND-404")
                .validationErrors(null)
                .build());
    }


    @ExceptionHandler(DuplicateSupplierOrderCodeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateSupplierOrderCode(DuplicateSupplierOrderCodeException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT.value())
                        .error(HttpStatus.CONFLICT.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message(ex.getMessage())
                        .errorCode("SUPPLIERORDER-DUPLICATE-CODE-409")
                        .validationErrors(null)
                .build());
    }


    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ErrorResponse> handleWebInputException(ServerWebInputException ex, ServerWebExchange exchange) {
        String rawMessage = ex.getMessage();
        String parsedMessage = extractUsefulMessage(rawMessage);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message("Invalid input: " + parsedMessage)
                .errorCode("SUPPLIER-BAD-REQUEST-400")
                .validationErrors(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private String extractUsefulMessage(String rawMessage) {
        if (rawMessage == null) return "Unknown input error";

        rawMessage = rawMessage.toLowerCase();

        if (rawMessage.contains("json parse error")) {
            return "Malformed JSON structure.";
        } else if (rawMessage.contains("uuid")) {
            return "Invalid UUID format (expected xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx).";
        } else if (rawMessage.contains("localdate") || rawMessage.contains("datetimeparseexception")) {
            return "Invalid date format (expected yyyy-MM-dd).";
        } else if (rawMessage.contains("bigdecimal")) {
            return "Invalid number format (expected decimal, e.g. 1234.56).";
        } else if (rawMessage.contains("integer")) {
            return "Invalid number format (expected whole number).";
        } else if (rawMessage.contains("invalidformatexception")) {
            return "Invalid data type in one or more fields.";
        } else if (rawMessage.contains("failed to read http message")) {
            return "One or more fields contain invalid format or missing values.";
        }

        return "Unexpected input error: " + rawMessage;
    }


    @ExceptionHandler(SupplierReceiptFilterNoMatchException.class)
    public ResponseEntity<ErrorResponse> handleSupplierReceiptFilterNoMatch(SupplierReceiptFilterNoMatchException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .path(exchange.getRequest().getPath().value())
                        .message(ex.getMessage())
                        .errorCode("SUPPLIERRECEIPT-FILTER-NOTFOUND-404")
                        .validationErrors(null)
                .build());
    }


    @ExceptionHandler(SupplierOrderNotFoundBySupplierException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundBySupplier(SupplierOrderNotFoundBySupplierException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIERORDER-SUPPLIERID-NOTFOUND-404")
                .validationErrors(null)
                .build());
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplierNotFoundException(SupplierNotFoundException ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIER-NOTFOUND-404")
                .validationErrors(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SupplierNotFoundByNameException.class)
    public ResponseEntity<ErrorResponse> handleSupplierNotFoundByNameException(SupplierNotFoundByNameException ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIER-NAME-NOTFOUND-404")
                .validationErrors(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIER-VALIDATION-001")
                .validationErrors(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message("Validation failed")
                .errorCode("SUPPLIER-VALIDATION-002")
                .validationErrors(ex.getErrors())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, ServerWebExchange exchange) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(exchange.getRequest().getPath().value())
                .message(ex.getMessage())
                .errorCode("SUPPLIER-ERROR-500")
                .validationErrors(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}