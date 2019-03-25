package com.yueping.testtsc4503;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = findViewById(R.id.button1);
        final List<String> lsitTest = new ArrayList<>();
        lsitTest.add("1");
//        lsitTest.add("1");
//        lsitTest.add("1");
        printOutBoxZsc4503(lsitTest);

        test.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        printOutBoxZsc4503(lsitTest);
                    }
                }, 10);
            }
        });
    }

    public static void testprint(String pStrMsg) {
        Log.v("EAWSD", pStrMsg);
        final String strPringtIp = "172.28.5.67";
        final int iPringtPoint = 9100;
        printTsc4503(pStrMsg, strPringtIp, iPringtPoint);
    }

    private static void realPrint(final String pStrContent, final String pStrPringtIp, final int pIPringtPoint) {
        getTsc4503Sts(pStrPringtIp, pIPringtPoint, new IsCanPrint() {
            @Override
            public void isCanPrint(TscStsResult pTscStsResult) {
                if (pTscStsResult.isCanUse()) {
                    printTsc4503(pStrContent, pStrPringtIp, pIPringtPoint);
                } else {
                    if (pTscStsResult.getCode() == 1) {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                realPrint(pStrContent, pStrPringtIp, pIPringtPoint);
                            }
                        }, 1 * 1000);
                    } else {
                        System.out.print("TSC4503打印机工作中,请稍等");
                    }
                }
            }
        });
    }

    private static String getPrintStr(List<TscPrinterBean> pList) {
        StringBuilder strBld = new StringBuilder();

        for (TscPrinterBean item : pList) {
            strBld.append("SIZE 69.10 mm, 40 mm\n" +
                    "GAP 3 mm, 0 mm\n" +
                    "DIRECTION 1,0\n" +
                    "REFERENCE 0,0\n" +
                    "OFFSET 0 mm\n" +
                    "SET PEEL OFF\n" +
                    "SET CUTTER OFF\n" +
                    "SET PARTIAL_CUTTER OFF\n" +
                    "SET TEAR ON\n" +
                    "CLS\n");

            strBld.append("QRCODE ")
                    .append(item.getStrQRPosX()).append(",")
                    .append(item.getStrQRPosX()).append(",L,6,A,180,M2,S7,\"")
                    .append(item.getStrQRMsg()).append("\"\n");
            for (TscPrinterStrListBean tItem : item.getListStr()) {
                strBld.append("TEXT ").append(tItem.getStrMsgPosX()).append(",")
                        .append(tItem.getStrMsgPosY()).append(",\"Font001\",0,")
                        .append(tItem.getStrMsgX()).append(",").append(tItem.getStrMsgY()).append(",\"")
                        .append(tItem.getStrMsg()).append("\"\n");
            }
            strBld.append("PRINT 1,1\n");
        }

        return strBld.toString();
    }

    public static void printOutBoxZsc4503(List<String> pList) {
        List<TscPrinterBean> listTemp = new ArrayList<>();
        for (String tItem : pList) {
            TscPrinterBean itemBean = new TscPrinterBean();
            itemBean.setStrQRPosX("170");
            itemBean.setStrQRPosY("320");
            itemBean.setStrQRMsg("691001002900910");
            itemBean.setListStr(getListStrMsg());
            listTemp.add(itemBean);
        }
        testprint(getPrintStr(listTemp));
    }

    private static List<TscPrinterStrListBean> getListStrMsg() {
        List<TscPrinterStrListBean> listTemp = new ArrayList<>();
        TscPrinterStrListBean tItem = null;
        tItem = new TscPrinterStrListBean();
        tItem.setStrMsg("客户");
        tItem.setStrMsgPosX("190");
        tItem.setStrMsgPosY("50");
        tItem.setStrMsgX("4");
        tItem.setStrMsgY("4");
        listTemp.add(tItem);
        tItem = new TscPrinterStrListBean();
        tItem.setStrMsg(String.format("%s", "商品名称"));
        tItem.setStrMsgPosX("190");
        tItem.setStrMsgPosY("140");
        tItem.setStrMsgX("4");
        tItem.setStrMsgY("4");
        listTemp.add(tItem);
        tItem = new TscPrinterStrListBean();
        tItem.setStrMsg(String.format("%s", "商品国际码"));
        tItem.setStrMsgPosX("190");
        tItem.setStrMsgPosY("230");
        tItem.setStrMsgX("4");
        tItem.setStrMsgY("4");
        listTemp.add(tItem);
//        tItem = new TscPrinterStrListBean();
//        tItem.setStrMsg(StringUtil.getOutBoxNu(pItem.dbcode));
//        tItem.setStrMsgPosX("150");
//        tItem.setStrMsgPosY("190");
//        tItem.setStrMsgX("35");
//        tItem.setStrMsgY("35");
//        listTemp.add(tItem);
        tItem = new TscPrinterStrListBean();
        tItem.setStrMsg("商品货位");
        tItem.setStrMsgPosX("190");
        tItem.setStrMsgPosY("330");
        tItem.setStrMsgX("5");
        tItem.setStrMsgY("5");
        listTemp.add(tItem);
        tItem = new TscPrinterStrListBean();
        tItem.setStrMsg(String.format("%s%s", "数量", "单位"));
        tItem.setStrMsgPosX("395");
        tItem.setStrMsgPosY("330");
        tItem.setStrMsgX("4");
        tItem.setStrMsgY("4");
        listTemp.add(tItem);
        tItem = new TscPrinterStrListBean();
        tItem.setStrMsg("当前打印序号");
//        tItem.setStrMsg("2/300");
        tItem.setStrMsgPosX("10");
        tItem.setStrMsgPosY("330");
        tItem.setStrMsgX("6");
        tItem.setStrMsgY("6");
        listTemp.add(tItem);
        return listTemp;
    }


    public static void getTsc4503Sts(String ipaddress, int portnumber, IsCanPrint isCanPrint) {
        TscStsResult result = new TscStsResult();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Socket socket = null;
        byte[] readBuf = new byte[1024];
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ipaddress, portnumber), 4000);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMessage("打印机连接失败!");
            result.setCanUse(false);
            isCanPrint.isCanPrint(result);
            return;
        }
        byte[] message = new byte[]{27, 33, 63};
        String query = "";
        try {
            outputStream.write(message);
        } catch (IOException e) {
            result.setCode(0);
            result.setMessage("获取打印机状态失败!");
            result.setCanUse(false);
            isCanPrint.isCanPrint(result);
            return;
        }
        try {
            int i = inputStream.available();
            while (i == 0) {
                i = inputStream.available();
            }
            inputStream.read(readBuf);
        } catch (IOException var7) {
            result.setCode(0);
            result.setMessage("读取打印机状态失败!");
            result.setCanUse(false);
            isCanPrint.isCanPrint(result);
        }
        if (readBuf[0] == 0) {
            colseSocket(socket);
            result.setCode(1);
            result.setMessage("打印机准备就绪");
            result.setCanUse(true);
            isCanPrint.isCanPrint(result);
        } else {
            colseSocket(socket);
            result.setCode(0);
            result.setMessage("打印机发生错误!");
            result.setCanUse(false);
            isCanPrint.isCanPrint(result);
        }
    }

    private static void colseSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printTsc4503(final String content, final String pStrPringtIp, final int pIPringtPoint) {
        byte[] bytes = null;
        try {
            bytes = content.getBytes("gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final byte[] finalBytes = bytes;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Socket socket = null;
                OutputStream os = null;
                try {
                    socket = new Socket(pStrPringtIp, pIPringtPoint);
                    os = socket.getOutputStream();
                    os.write(finalBytes);
                    os.flush();
                    socket.shutdownOutput();
                    os.close();
                    os.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}