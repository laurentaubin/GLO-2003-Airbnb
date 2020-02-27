package exceptions;

import java.io.IOException;

public class JsonParserException extends IOException {
  public JsonParserException() {
    super();
  }

  public JsonParserException(String s) {
    super(s);
  }
}
