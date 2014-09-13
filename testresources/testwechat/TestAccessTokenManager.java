package testwechat;

import util.ServerContext;
import wechat.access.AccessTokenManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestAccessTokenManager{

  public static void main(String[] args) throws FileNotFoundException {
    FileInputStream f = new FileInputStream(new File("/Users/wangronghua/workspace/duty/srcresources/server.properties"));
    ServerContext.init(f);
    System.out.println(AccessTokenManager.getToken("form"));
  }

}