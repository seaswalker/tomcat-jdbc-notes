package jmx.listener;

public interface ServerConfigureMBean {

    void setPort(int port);

    int getPort();

    void setHost(String host);

    String getHost();

}
