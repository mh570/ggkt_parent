package com.fengling.ggkt.exception;

import com.fengling.ggkt.result.Result;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail().message("错误哦，我也不知道为什么");
    }

    @ExceptionHandler(HiException.class)
    public Result error(HiException e) {
        e.printStackTrace();
        return Result.fail().message("我自己发出的错误哦，问题不知道");
    }


}
