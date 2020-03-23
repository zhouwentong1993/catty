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
package pink.catty.config;

import pink.catty.core.ServerAddress;
import pink.catty.core.config.InnerServerConfig;
import pink.catty.core.config.InnerServerConfig.InnerServerConfigBuilder;
import pink.catty.core.utils.NetUtils;

public class ServerConfig {

  public static ServerConfigBuilder builder() {
    return new ServerConfigBuilder();
  }

  public ServerConfig() {
  }

  public ServerConfig(int port, int workerThreadNum, boolean needOrder) {
    this.port = port;
    this.workerThreadNum = workerThreadNum;
    this.needOrder = needOrder;
  }

  private int port;
  private int workerThreadNum;
  private volatile ServerAddress address;

  /**
   * If every request from the same TCP should be executed by order, set this
   * option true.
   *
   * You should very carefully to set this option true. If you do so,
   * all requests from the same rpc-client would be executed one by one in
   * the same thread in rpc-server to guarantee to keep the invoking order,
   * as a consequence of severe performance.
   *
   * Actually, there are rarely conditions you should set this option true.
   * @deprecated bad design. will be removed at 0.2.?
   */
  @Deprecated
  private boolean needOrder = false;

  public int getPort() {
    return port;
  }

  public int getWorkerThreadNum() {
    return workerThreadNum;
  }

  @Deprecated
  public boolean isNeedOrder() {
    return needOrder;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setWorkerThreadNum(int workerThreadNum) {
    this.workerThreadNum = workerThreadNum;
  }

  public ServerAddress getServerAddress() {
    if(address == null) {
      synchronized (this) {
        if(address == null) {
          String serverIp = NetUtils.getLocalAddress().getHostAddress();
          this.address = new ServerAddress(serverIp, port);
        }
      }
    }
    return address;
  }

  public InnerServerConfigBuilder toInnerConfigBuilder() {
    return InnerServerConfig.builder()
        .address(address)
        .needOrder(needOrder)
        .port(port)
        .workerThreadNum(workerThreadNum);
  }

  /**
   * Builder
   */
  public static class ServerConfigBuilder {

    private int port;
    private int workerThreadNum;
    private boolean needOrder;

    public ServerConfigBuilder port(int port) {
      this.port = port;
      return this;
    }

    public ServerConfigBuilder workerThreadNum(int workerThreadNum) {
      this.workerThreadNum = workerThreadNum;
      return this;
    }

    public ServerConfigBuilder needOrder(boolean needOrder) {
      this.needOrder = needOrder;
      return this;
    }

    public ServerConfig build() {
      return new ServerConfig(port, workerThreadNum, needOrder);
    }
  }

}
