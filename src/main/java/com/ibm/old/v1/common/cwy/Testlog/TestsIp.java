package com.ibm.old.v1.common.cwy.Testlog;

import c.a.util.core.test.CommTest;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.junit.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @Description: 测试方案详情修改记录日志
 * @Author: cwy
 * @Date: 2019-08-01 14:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TestsIp extends CommTest {


    @Test
    public void demo() {
        try {
            jdbcTool = this.findJdbcToolLocal();
            this.transactionStart(jdbcTool);
//            List<String> localIpList = getLocalIpList();
//            System.out.println(localIpList.toString());
//            Double aDouble = getDouble(20);
//            System.out.println(aDouble);
//            String s = UUid();
//            System.out.println(s);
            String abcdefg = subLast("abcdefg", 2);
            System.out.println(abcdefg);
            this.transactionEnd(jdbcTool);
        } catch (Exception e) {
            e.printStackTrace();
            this.transactionRoll(jdbcTool);
        } finally {
            this.transactionClose(jdbcTool);
        }

    }
    public static List<String> getLocalIpList() {
        List<String> ipList = new ArrayList<>();
        try {

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address) {
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }

    public static Double getDouble(Object num) {
        if (num == null || StringTool.isEmpty(num)) {
            return 0d;
        } else if (num instanceof Double) {
            return (Double) num;
        } else {
            return Double.parseDouble(num.toString());
        }
    }

    public static int intValueT(Object num) {
        return Double.valueOf((Double.parseDouble(num.toString()) * 1000)).intValue();
    }

    public static Integer[] intValue(Object[] objs) {
        if (ContainerTool.isEmpty(objs)) {
            return null;
        }
        int len = objs.length;
        Integer[] ints = new Integer[len];
        for (int i = 0; i < len; i++) {
            ints[i] = getInteger(objs[i]);
        }
        return ints;
    }
    public static Integer getInteger(Object obj) {
        if (!isInteger(obj)) {
            return 0;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else {
            return Integer.parseInt(obj.toString());
        }
    }
    public static boolean isInteger(Object obj) {
        if (StringTool.isEmpty(obj)) {
            return false;
        }
        Pattern pattern = compile("^[-+]?[\\d]*$");
        return pattern.matcher(obj.toString()).matches();
    }
    public static String UUid(){
        String s = UUID.randomUUID().toString();
        return s;
    }

    public static String subLast(String str, int len) {
        if (isEmpty(str) || str.length() < len) {
            return str;
        }
        return str.substring(str.length() - len);
    }
    public static boolean isEmpty(String inStr) {
        return null == inStr || "".equals(inStr.trim()) || "null".equalsIgnoreCase(inStr.trim()) || "NaN"
                .equalsIgnoreCase(inStr.trim()) || "".equals(inStr.trim().replace("\"", ""));
    }
}
