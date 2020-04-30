/*
 * Copyright 2019 The Catty Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pink.catty.test;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pink.catty.core.CodecException;
import pink.catty.core.config.InnerClientConfig;
import pink.catty.core.extension.ExtensionFactory;
import pink.catty.core.extension.spi.BrokenDataPackageException;
import pink.catty.core.extension.spi.Codec;

public class EndpointTest {

  private static final String TEST_IP = "127.0.0.1";
  private static final int TEST_PORT = 20560;
  private static final int TEST_PORT_1 = 20561;
  private static final String TEST_ADDRESS = "127.0.0.1:20560";
  private static final String TEST_ADDRESS_1 = "127.0.0.1:20561";
  private static final int TEST_TIMEOUT = 0;
  private static final String TEST_CODEC_TYPE = "TEST";

  @BeforeClass
  public static void registerCodec() {
    ExtensionFactory.getCodec().register(TEST_CODEC_TYPE, MockCodec.class);
  }

  @Test
  public void clientInnerConfigTest() {
    InnerClientConfig clientConfig = new InnerClientConfig(TEST_IP, TEST_PORT, TEST_ADDRESS,
        TEST_TIMEOUT, TEST_CODEC_TYPE);
    Assert.assertEquals(TEST_IP, clientConfig.getServerIp());
    Assert.assertEquals(TEST_PORT, clientConfig.getServerPort());
    Assert.assertEquals(TEST_ADDRESS, clientConfig.getAddress());
    Assert.assertEquals(TEST_TIMEOUT, clientConfig.getTimeout());
    Assert.assertEquals(TEST_ADDRESS, clientConfig.getAddress());
  }


  public static class MockCodec implements Codec {

    @Override
    public void readPackage(Object data, List out) throws BrokenDataPackageException {

    }

    @Override
    public byte[] encode(Object message, DataTypeEnum dataTypeEnum) throws CodecException {
      return new byte[0];
    }

    @Override
    public Object decode(byte[] data, DataTypeEnum dataTypeEnum) throws CodecException {
      return null;
    }
  }

}
