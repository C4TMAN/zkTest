package com.example;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;

/**
 * Created by C4TMAN on 2016/10/13.
 */
public class ZkTest {

    @Test
    public void test() {
        readFromFile("D:\\test\\data");
    }


    /**
     *向zk节点写入数据（覆盖掉原来的）
     * @param zookeeperConnectionString    zk连接字符串
     * @param nodePath                      zk节点路径
     * @param data                          写入数据内容
     */
    public void setData(String zookeeperConnectionString, String nodePath, String data){

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient
                (zookeeperConnectionString, retryPolicy);
        client.start();
        try {
            client.setData().forPath(nodePath,data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }

    /**
     * 从文件中读取数据
     * @param filePath
     * @return
     */
    public String readFromFile(String filePath){
        StringBuilder content = new StringBuilder("");
        File file = new File(filePath);
        if(!file.exists()){
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = "";
            int i = 0;
            while((line=reader.readLine())!=null){
                i++;
                content.append(line);
                System.out.println(i+"\n"+line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        catch(IOException e){
            e.printStackTrace();
            return "";
        }
        finally {
            try {
                if(reader!=null){
                    reader.close();
                    return content.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return content.toString();
    }

    /**
     * 读取文件内容写入zk节点
     * @param zookeeperConnectionString
     * @param nodePath
     * @param filePath
     */
    public void setDataFromFile(String zookeeperConnectionString, String nodePath, String filePath ){
        String data  = readFromFile(filePath);
        if(StringUtils.isEmpty(data)||StringUtils.isEmpty(zookeeperConnectionString)||StringUtils.isEmpty(filePath)){
            System.out.printf("data is null");
        }
        else{
            setData(zookeeperConnectionString,nodePath,data);
        }
    }

}
