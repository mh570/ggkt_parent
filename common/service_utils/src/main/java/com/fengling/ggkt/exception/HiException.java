package com.fengling.ggkt.exception;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HiException extends RuntimeException{
    private Integer code;
    private String msg;


}
