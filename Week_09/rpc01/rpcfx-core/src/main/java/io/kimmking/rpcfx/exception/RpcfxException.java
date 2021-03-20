package io.kimmking.rpcfx.exception;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/3/15
 */
public class RpcfxException extends RuntimeException {

    public RpcfxException() {
        super();
    }

    public RpcfxException(String message) {
        super(message);
    }

    public RpcfxException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcfxException(Throwable cause) {
        super(cause);
    }
}
