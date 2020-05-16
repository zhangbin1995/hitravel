package com.lixiang.hitravel.exception;

import com.lixiang.hitravel.result.CodeMsg;
import lombok.Getter;

/**
 * @author zhang
 * @date 2019-12-09
 */
@Getter
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 5601128317386198961L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public GlobalException(String str) {
        super(str);
    }
}
