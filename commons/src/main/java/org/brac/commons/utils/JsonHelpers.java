package org.brac.commons.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;

public class JsonHelpers {
  private static final ObjectReader objectReader =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .registerModule(new JavaTimeModule()).reader();

  public static <T> T getMessage(byte[] messageBytes, Class<T> messageType) throws IOException {
    return objectReader.readValue(messageBytes, messageType);
  }
}
