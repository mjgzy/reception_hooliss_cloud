package com.xfkj.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommUtil {
    public static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SDFTIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static String IMGEXT = "bmp,dib,jfif,gif,jpe,jpeg,jpg,png,tif,tiff,ico";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static int totalFolder;
    static int totalFile;
    //手机
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    //平板
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);


    /**
     * TODO  返回所有返回参数
     *
     */
    public static void saveBackParam(HttpServletRequest request, ModelAndView mv) {
        Map properties = request.getParameterMap();
        Iterator entries = properties.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            String value = "";
            if (CommUtil.isNotNull(valueObj)) {
                if (isNull(valueObj)) {
                    value = "";
                } else if (valueObj instanceof String[]) {
                    String[] values = (String[]) valueObj;
                    for (int i = 0; i < values.length; i++) {
                        value = values[i] + ",";
                    }
                    value = value.substring(0, value.length() - 1);
                } else {
                    value = valueObj.toString();
                }
                if (CommUtil.isNotNull(value) && !"currentPage".equals(name)) {
                    mv.addObject(name, value);
                }
            }
        }
    }
    /**
     * TODO  返回json数据
     *
     */
    public static void printString(HttpServletResponse response, String str) {
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * TODO  判断是否手机访问
     *
     * @作者： zhouchaoxi
     * @日期：2019/2/20
     */
    public static boolean isPhoneRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (null == userAgent) {
            userAgent = "";
        }
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        //移动设备入口
        //pc端入口
        return matcherPhone.find() || matcherTable.find();
    }


    /**
     * TODO  int转String，不足补0
     *
     * @作者： zhouchaoxi
     * @日期：2018/10/25
     */
    public static Long[] stringToLong(String[] str) {
        Long[] in = new Long[str.length];
        for (int i = 0; i < str.length; i++) {
            if (isNotNull(str[i])) {
                in[i] = Long.parseLong(str[i]);
            }
        }
        return in;
    }

    /**
     * TODO  int转String，不足补0
     *
     * @作者： zhouchaoxi
     * @日期：2018/10/25
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);//左补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * TODO 当前时间加上指定天数
     *
     * @作者： zhouchaoxi
     * @日期：2018/8/28
     */
    public static String addDate(long day) {
        long time = new Date().getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return sdfTime.format(new Date(time)); // 将毫秒数转换成日期
    }

    /**
     * TODO 当前时间加上指定天数
     *
     * @作者： zhouchaoxi
     * @日期：2018/8/28
     */
    public static Date addDay(long day) {
        long time = new Date().getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    /**
     * @param o
     * @return
     * @描述: 空值判断
     * @作者: zhouChaoXi
     * @时间: 2018年6月8日
     */
    public static boolean isNull(Object o) {
        if (o == null || "".equals(o) || o == "" || o == "null"
                || "null".equals(o) || "undefined".equals(o)) {
            return true;
        }
        if (o != null) {
            return o.toString().replaceAll("\\s*", "").length() == 0;
        }
        return true;
    }

    public static String first2low(String str) {
        String s = "";
        s = str.substring(0, 1).toLowerCase() + str.substring(1);
        return s;
    }

    public static String first2upper(String str) {
        String s = "";
        s = str.substring(0, 1).toUpperCase() + str.substring(1);
        return s;
    }

    public static List<String> str2list(String s) throws IOException {
        List list = new ArrayList();
        if ((s != null) && (!"".equals(s))) {
            StringReader fr = new StringReader(s);
            BufferedReader br = new BufferedReader(fr);
            String aline = "";
            while ((aline = br.readLine()) != null) {
                list.add(aline);
            }
        }
        return list;
    }

    public static Date parseLongDate(String s) {
        Date d = null;
        try {
            d = sdfTime.parse(s);
        } catch (Exception localException) {
        }
        return d;
    }

    public static Date formatDate(String s) {
        Date d = null;
        try {
            d = dateFormat.parse(s);
        } catch (Exception localException) {
        }
        return d;
    }

    public static Date formatDate(String s, String format) {
        Date d = null;
        try {
            SimpleDateFormat dFormat = new SimpleDateFormat(format);
            d = dFormat.parse(s);
        } catch (Exception localException) {
        }
        return d;
    }

    public static String formatTime(String format, Object v) {
        if (v == null) {
            return null;
        }
        if ("".equals(v)) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(v);
    }

    public static int getNumber() {
        int max = 999999;
        int min = 100000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public static String getOrder() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return df.format(new Date());
    }

    public static String formatLongDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String formatLongDate(Object v) {
        if ((v == null) || ("".equals(v))) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = "";
        try {
            data = df.format(v);
        } catch (Exception e) {

        }
        return data;
    }

    public static String formatShortDate(Object v) {
        if (v == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(v);
    }

    public static String getDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public static String decode(String s) {
        String ret = s;
        try {
            ret = URLDecoder.decode(s.trim(), "UTF-8");
        } catch (Exception localException) {
        }
        return ret;
    }

    public static String encode(String s) {
        String ret = s;
        try {
            ret = URLEncoder.encode(s.trim(), "UTF-8");
        } catch (Exception localException) {
        }
        return ret;
    }

    public static String convert(String str, String coding) {
        String newStr = "";
        if (str != null) {
            try {
                newStr = new String(str.getBytes(StandardCharsets.ISO_8859_1), coding);
            } catch (Exception e) {
                return newStr;
            }
        }
        return newStr;
    }


    /**
     * TODO  获取服务器真实路径
     *
     * @作者： zhouchaoxi
     * @日期：2018/10/22
     */
    public static String getRealPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }
    /**
     * @描述: TODO 保存文件
     * @作者: zhouChaoXi
     * @时间: 2018/8/27 23:50
     */
    public static Map<String, Object> saveFileToServer(HttpServletRequest request, MultipartFile file, String filePath) throws Exception {
        Map<String, Object> map = new HashMap();
        if (file != null && !file.isEmpty()) {
            // 原名
            String oldName = file.getOriginalFilename();
            // 类型
            String extend = oldName.substring(oldName.lastIndexOf(".") + 1).toLowerCase();
            float fileSize = file.getSize();
            // 新名称
            String newFileName = formatLongDate() + "." + extend;
            String relativePath = "upload" + File.separator + filePath + File.separator;
//          保存路径
            filePath = getRealPath(request) + relativePath;
            File tempFile = new File(filePath, newFileName);
//          创建文件夹
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            BufferedImage img = null;
            if (IMGEXT.contains(extend)) {
                img = ImageIO.read(file.getInputStream());
                ImageIO.write(img, "jpg", tempFile);
            } else {
                file.transferTo(tempFile);
            }
            map.put("width", img == null ? null : img.getWidth());
            map.put("height", img == null ? null : img.getHeight());
            map.put("extend", extend);
            map.put("fileName", newFileName);
            map.put("filePath", relativePath);
            map.put("fileSize", fileSize);
            map.put("oldName", oldName);
            map.put("allPath", relativePath + newFileName);
            return map;
        }
        return null;
    }

    /**
     * @描述: TODO 保存文件
     * @作者: zhouChaoXi
     * @时间: 2018/8/27 23:50
     */
    public static Map<String, Object> saveFileToServer(HttpServletRequest request, File file, String filePath) throws Exception {
        Map<String, Object> map = new HashMap();
        if (file != null && file.isFile()) {
            // 原名
            String oldName = file.getName();
            // 类型
            String extend = oldName.substring(oldName.lastIndexOf(".") + 1).toLowerCase();
            float fileSize = file.length();
            // 新名称
            String newFileName = formatLongDate() + "." + extend;
            String relativePath = "upload" + File.separator + filePath + File.separator;
//          保存路径
            filePath = getRealPath(request) + relativePath;
            File tempFile = new File(filePath, newFileName);
//          创建文件夹
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            BufferedImage img = null;
            if (IMGEXT.contains(extend)) {
                img = ImageIO.read(file);
                ImageIO.write(img, "jpg", tempFile);
            } else {
                file.renameTo(tempFile);
            }
            map.put("width", img == null ? null : img.getWidth());
            map.put("height", img == null ? null : img.getHeight());
            map.put("extend", extend);
            map.put("fileName", newFileName);
            map.put("filePath", relativePath);
            map.put("fileSize", fileSize);
            map.put("oldName", oldName);
            map.put("allPath", relativePath + newFileName);
            return map;
        }
        return null;
    }

    public static boolean isImg(String extend) {
        boolean ret = false;
        List<String> list = new ArrayList<String>();
        list.add("jpg");
        list.add("jpeg");
        list.add("bmp");
        list.add("gif");
        list.add("png");
        list.add("tif");
        for (String s : list) {
            if (s.equals(extend)) {
                ret = true;
            }
        }
        return ret;
    }

    public static final void waterMarkWithImage(String pressImg, String targetImg, int pos, float alpha) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, 1);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);

            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            g.setComposite(AlphaComposite.getInstance(10, alpha / 100.0F));
            int width_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            int x = 0;
            int y = 0;

            if (pos == 2) {
                x = (width - width_biao) / 2;
                y = 0;
            }
            if (pos == 3) {
                x = width - width_biao;
                y = 0;
            }
            if (pos == 4) {
                x = width - width_biao;
                y = (height - height_biao) / 2;
            }
            if (pos == 5) {
                x = width - width_biao;
                y = height - height_biao;
            }
            if (pos == 6) {
                x = (width - width_biao) / 2;
                y = height - height_biao;
            }
            if (pos == 7) {
                x = 0;
                y = height - height_biao;
            }
            if (pos == 8) {
                x = 0;
                y = (height - height_biao) / 2;
            }
            if (pos == 9) {
                x = (width - width_biao) / 2;
                y = (height - height_biao) / 2;
            }
            g.drawImage(src_biao, x, y, width_biao, height_biao, null);

            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static boolean waterMarkWithText(String filePath, String outPath, String text, String markContentColor, Font font, int pos, float qualNum) {
        ImageIcon imgIcon = new ImageIcon(filePath);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null);
        int height = theImg.getHeight(null);
        BufferedImage bimage = new BufferedImage(width, height, 1);
        Graphics2D g = bimage.createGraphics();
        if (font == null) {
            font = new Font("黑体", 1, 30);
            g.setFont(font);
        } else {
            g.setFont(font);
        }
        g.setColor(getColor(markContentColor));
        g.setBackground(Color.white);
        g.drawImage(theImg, 0, 0, null);
        FontMetrics metrics = new FontMetrics(font) {
        };
        Rectangle2D bounds = metrics.getStringBounds(text, null);
        int widthInPixels = (int) bounds.getWidth();
        int heightInPixels = (int) bounds.getHeight();
        int left = 0;
        int top = heightInPixels;

        if (pos == 2) {
            left = width / 2;
            top = heightInPixels;
        }
        if (pos == 3) {
            left = width - widthInPixels;
            top = heightInPixels;
        }
        if (pos == 4) {
            left = width - widthInPixels;
            top = height / 2;
        }
        if (pos == 5) {
            left = width - widthInPixels;
            top = height - heightInPixels;
        }
        if (pos == 6) {
            left = width / 2;
            top = height - heightInPixels;
        }
        if (pos == 7) {
            left = 0;
            top = height - heightInPixels;
        }
        if (pos == 8) {
            left = 0;
            top = height / 2;
        }
        if (pos == 9) {
            left = width / 2;
            top = height / 2;
        }
        g.drawString(text, left, top);
        g.dispose();
        try {
            FileOutputStream out = new FileOutputStream(outPath);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(qualNum, true);
            encoder.encode(bimage, param);
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean createFolder(String folderPath) {
        boolean ret = true;
        try {
            File myFilePath = new File(folderPath);
            if ((!myFilePath.exists()) && (!myFilePath.isDirectory())) {
                ret = myFilePath.mkdirs();
                if (!ret) {
                    System.out.println("创建文件夹出错");
                }
            }
        } catch (Exception e) {
            System.out.println("创建文件夹出错");
            ret = false;
        }
        return ret;
    }

    public static List toRowChildList(List list, int perNum) {
        List l = new ArrayList();
        if (list == null) {
            return l;
        }

        for (int i = 0; i < list.size(); i += perNum) {
            List cList = new ArrayList();
            for (int j = 0; j < perNum; j++) {
                if (i + j < list.size()) {
                    cList.add(list.get(i + j));
                }
            }
            l.add(cList);
        }
        return l;
    }

    public static List copyList(List list, int begin, int end) {
        List l = new ArrayList();
        if (list == null) {
            return l;
        }
        if (end > list.size()) {
            end = list.size();
        }
        for (int i = begin; i < end; i++) {
            l.add(list.get(i));
        }
        return l;
    }

    public static boolean isNotNull(Object obj) {
        return (obj != null) && !"".equals(obj.toString()) && !"null".equals(obj);
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];

                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错 ");
            e.printStackTrace();
        }
    }

    public static boolean deleteFolder(String path) {
        boolean flag = false;
        File file = new File(path);

        if (!file.exists()) {
            return flag;
        }

        if (file.isFile()) {
            return deleteFile(path);
        }
        return deleteDirectory(path);
    }

    public static boolean deleteFile(String path) {
        boolean flag = false;
        File file = new File(path);

        if ((file.isFile()) && (file.exists())) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean deleteDirectory(String path) {
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dirFile = new File(path);

        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;

        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }

        return dirFile.delete();
    }

    public static String showPageStaticHtml(String url, int currentPage, int pages) {
        String s = "";
        if (pages > 0) {
            if (currentPage >= 1) {
                s = s + "<a href='" + url + "_1.htm'>首页</a> ";
                if (currentPage > 1) {
                    s = s + "<a href='" + url + "_" + (currentPage - 1) + ".htm'>上一页</a> ";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    if (i == currentPage) {
                        s = s + "<a class='this' href='" + url + "_" + i + ".htm'>" + i + "</a> ";
                    } else {
                        s = s + "<a href='" + url + "_" + i + ".htm'>" + i + "</a> ";
                    }
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s = s + "<a href='" + url + "_" + (currentPage + 1) + ".htm'>下一页</a> ";
                }
                s = s + "<a href='" + url + "_" + pages + ".htm'>末页</a> ";
            }
        }
        return s;
    }

    public static String showPageHtml(String url, String params, int currentPage, int pages) {
        String s = "";
        if (pages > 0) {
            if (currentPage >= 1) {
                s = s + "<a href='" + url + "?currentPage=1" + params + "'>首页</a> ";
                if (currentPage > 1) {
                    s = s + "<a href='" + url + "?currentPage=" + (currentPage - 1) + params + "'>上一页</a> ";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    if (i == currentPage) {
                        s = s + "<a class='this' href='" + url + "?currentPage=" + i + params + "'>" + i + "</a> ";
                    } else {
                        s = s + "<a href='" + url + "?currentPage=" + i + params + "'>" + i + "</a> ";
                    }
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s = s + "<a href='" + url + "?currentPage=" + (currentPage + 1) + params + "'>下一页</a> ";
                }
                s = s + "<a href='" + url + "?currentPage=" + pages + params + "'>末页</a> ";
            }
        }

        return s;
    }

    public static String showPageFormHtml(int currentPage, int pages) {
        String s = "";
        if (pages > 0) {
            if (currentPage >= 1) {
                s = s + "<a href='javascript:void(0);' onclick='gotoPage(1)'>首页</a> ";
                if (currentPage > 1) {
                    s = s + "<a href='javascript:void(0);' onclick='gotoPage(" + (currentPage - 1) + ")'>上一页</a> ";
                }
            }
            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    if (i == currentPage) {
                        s = s + "<a class='this' href='javascript:void(0);' onclick='return gotoPage(" + i + ")'>" + i + "</a> ";
                    } else {
                        s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" + i + ")'>" + i + "</a> ";
                    }
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages) {
                if (currentPage < pages) {
                    s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" + (currentPage + 1) + ")'>下一页</a> ";
                }
                s = s + "<a href='javascript:void(0);' onclick='return gotoPage(" + pages + ")'>末页</a> ";
            }
        }
        return s;
    }

    public static String showPageAjaxHtml(String url, String params, int currentPage, int pages) {
        String s = "";
        if (pages > 0) {
            String address = url + "?1=1" + params;
            if (currentPage >= 1) {
                s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\",1,this)'>首页</a> ";
                s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\"," + (currentPage - 1) + ",this)'>上一页</a> ";
            }

            int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
            if (beginPage <= pages) {
                s = s + "第　";
                int i = beginPage;
                for (int j = 0; (i <= pages) && (j < 6); j++) {
                    if (i == currentPage) {
                        s = s + "<a class='this' href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\"," + i + ",this)'>" + i + "</a> ";
                    } else {
                        s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\"," + i + ",this)'>" + i + "</a> ";
                    }
                    i++;
                }

                s = s + "页　";
            }
            if (currentPage <= pages) {
                s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\"," + (currentPage + 1) + ",this)'>下一页</a> ";
                s = s + "<a href='javascript:void(0);' onclick='return ajaxPage(\"" + address + "\"," + pages + ",this)'>末页</a> ";
            }
        }

        return s;
    }


    public static char randomChar() {
        char[] chars = {'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q', 'r',
                'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z'};
        int index = (int) (Math.random() * 52.0D) - 1;
        if (index < 0) {
            index = 0;
        }
        return chars[index];
    }

    public static String[] splitByChar(String s, String c) {
        String[] list = s.split(c);
        return list;
    }

    public static Object requestByParam(HttpServletRequest request, String param) {
        if (!"".equals(request.getParameter(param))) {
            return request.getParameter(param);
        }
        return null;
    }


    public static String substringfrom(String s, String from) {
        if (s.indexOf(from) < 0) {
            return "";
        }
        return s.substring(s.indexOf(from) + from.length());
    }

    public static int null2Int(Object s) {
        int v = 0;
        if (s != null) {
            try {
                v = Integer.parseInt(s.toString());
            } catch (Exception localException) {
            }
        }
        return v;
    }

    public static float null2Float(Object s) {
        float v = 0.0F;
        if (s != null) {
            try {
                v = Float.parseFloat(s.toString());
            } catch (Exception localException) {
            }
        }
        return v;
    }

    public static Double null2Double(Object s) {
        Double v = 0.0D;
        if (s != null) {
            try {
                v = Double.parseDouble(null2String(s));
            } catch (Exception localException) {
            }
        }
        return v;
    }

    public static boolean null2Boolean(Object s) {
        boolean v = false;
        if (s != null) {
            try {
                v = Boolean.parseBoolean(s.toString());
            } catch (Exception localException) {
            }
        }
        return v;
    }

    public static String null2String(Object s) {
        return s == null ? "" : s.toString().trim();
    }

    public static Long null2Long(Object s) {
        Long v = Long.valueOf(-1L);
        if (isNotNull(s)) {
            try {
                v = Long.valueOf(Long.parseLong(s.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return v;
    }

    public static String getTimeInfo(long time) {
        int hour = (int) time / 3600000;
        long balance = time - hour * 1000 * 60 * 60;
        int minute = (int) balance / 60000;
        balance -= minute * 1000 * 60;
        int seconds = (int) balance / 1000;
        String ret = "";
        if (hour > 0) {
            ret = ret + hour + "小时";
        }
        if (minute > 0) {
            ret = ret + minute + "分";
        } else if ((minute <= 0) && (seconds > 0)) {
            ret = ret + "零";
        }
        if (seconds > 0) {
            ret = ret + seconds + "秒";
        }
        return ret;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getServerAddr(HttpServletRequest request) {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        System.out.println(addr);
        return addr;
    }

    public static int indexOf(String s, String sub) {
        return s.trim().indexOf(sub.trim());
    }

    public static Map cal_time_space(Date begin, Date end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l = end.getTime() - begin.getTime();
        long day = l / 86400000L;
        long hour = l / 3600000L - day * 24L;
        long min = l / 60000L - day * 24L * 60L - hour * 60L;
        long second = l / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        Map map = new HashMap();
        map.put("day", Long.valueOf(day));
        map.put("hour", Long.valueOf(hour));
        map.put("min", Long.valueOf(min));
        map.put("second", Long.valueOf(second));
        return map;
    }

    public static final String randomString(int length) {
        char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        if (length < 1) {
            return "";
        }
        Random randGen = new Random();
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    public static final String randomInt(int length) {
        if (length < 1) {
            return null;
        }
        Random randGen = new Random();
        char[] numbersAndLetters = "0123456789".toCharArray();

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
        }
        return new String(randBuffer);
    }

    public static long getDateDistance(String time1, String time2) {
        long quot = 0L;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = date1.getTime() - date2.getTime();
            quot = quot / 1000L / 60L / 60L / 24L;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return quot;
    }

    public static double div(Object a, Object b) {
        double ret = 0.0D;
        if ((!"".equals(null2String(a))) && (!"".equals(null2String(b)))) {
            BigDecimal e = new BigDecimal(null2String(a));
            BigDecimal f = new BigDecimal(null2String(b));
            if (null2Double(f) > 0.0D) {
                ret = e.divide(f, 3, 1).doubleValue();
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double subtract(Object a, Object b) {
        double ret = 0.0D;
        BigDecimal e = new BigDecimal(null2Double(a));
        BigDecimal f = new BigDecimal(null2Double(b));
        ret = e.subtract(f).doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double add(Object a, Object b) {
        double ret = 0.0D;
        BigDecimal e = new BigDecimal(null2Double(a));
        BigDecimal f = new BigDecimal(null2Double(b));
        ret = e.add(f).doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double mul(Object a, Object b) {
        BigDecimal e = new BigDecimal(null2Double(a));
        BigDecimal f = new BigDecimal(null2Double(b));
        double ret = e.multiply(f).doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(ret)).doubleValue();
    }

    public static double formatMoney(Object money) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.valueOf(df.format(money)).doubleValue();
    }

    public static int M2byte(float m) {
        float a = m * 1024.0F * 1024.0F;
        return (int) a;
    }

    public static boolean convertIntToBoolean(int intValue) {
        return intValue != 0;
    }

    public static String getURL(HttpServletRequest request) {
        String contextPath = "/".equals(request.getContextPath()) ? "" : request.getContextPath();
        String url = "//" + request.getServerName();
        if (null2Int(Integer.valueOf(request.getServerPort())) != 80) {
            url = url + ":" + request.getServerPort() + contextPath;
        } else {
            url = url + contextPath;
        }
        return url;
    }

    /**
     * @param request
     * @return
     * @描述 ：获取服务器访问根路径
     * @作者 zhouchaoxi
     * @时间 2018年6月20日
     */
    public static String getServerUrl(HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
        return url;

    }



    public static int parseDate(String type, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if ("y".equals(type)) {
            return cal.get(1);
        }
        if ("M".equals(type)) {
            return cal.get(2) + 1;
        }
        if ("d".equals(type)) {
            return cal.get(5);
        }
        if ("H".equals(type)) {
            return cal.get(11);
        }
        if ("m".equals(type)) {
            return cal.get(12);
        }
        if ("s".equals(type)) {
            return cal.get(13);
        }
        return 0;
    }

    public static int[] readImgWH(String imgurl) {
        boolean b = false;
        try {
            URL url = new URL(imgurl);

            BufferedInputStream bis = new BufferedInputStream(url.openStream());

            byte[] bytes = new byte[100];

            OutputStream bos = new FileOutputStream(new File("C:\\thetempimg.gif"));
            int len;
            while ((len = bis.read(bytes)) > 0) {
                bos.write(bytes, 0, len);
            }
            bis.close();
            bos.flush();
            bos.close();

            b = true;
        } catch (Exception e) {
            b = false;
        }
        int[] a = new int[2];
        if (b) {
            File file = new File("C:\\thetempimg.gif");
            BufferedImage bi = null;
            boolean imgwrong = false;
            try {
                bi = ImageIO.read(file);
                try {
                    int i = bi.getType();
                    imgwrong = true;
                } catch (Exception e) {
                    imgwrong = false;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (imgwrong) {
                a[0] = bi.getWidth();
                a[1] = bi.getHeight();
            } else {
                a = null;
            }

            file.delete();
        } else {
            a = null;
        }
        return a;
    }


    public static boolean fileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static int splitLength(String s, String c) {
        int v = 0;
        if (!"".equals(s.trim())) {
            v = s.split(c).length;
        }
        return v;
    }

    public static double fileSize(File folder) {
        totalFolder += 1;

        long foldersize = 0L;
        File[] filelist = folder.listFiles();
        for (int i = 0; i < filelist.length; i++) {
            if (filelist[i].isDirectory()) {
                foldersize = (long) (foldersize + fileSize(filelist[i]));
            } else {
                totalFile += 1;
                foldersize += filelist[i].length();
            }
        }
        return div(Long.valueOf(foldersize), Integer.valueOf(1024));
    }

    public static int fileCount(File file) {
        if (file == null) {
            return 0;
        }
        if (!file.isDirectory()) {
            return 1;
        }
        int fileCount = 0;
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                fileCount++;
            } else if (f.isDirectory()) {
                fileCount++;
                fileCount += fileCount(file);
            }
        }
        return fileCount;
    }

    public static String get_all_url(HttpServletRequest request) {
        String query_url = request.getRequestURI();
        if ((request.getQueryString() != null) && (!"".equals(request.getQueryString()))) {
            query_url = query_url + "?" + request.getQueryString();
        }
        return query_url;
    }

    public static Color getColor(String color) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
        }
        return null;
    }

    public static Set<Integer> randomInt(int a, int length) {
        Set list = new TreeSet();
        int size = length;
        if (length > a) {
            size = a;
        }
        while (list.size() < size) {
            Random random = new Random();
            int b = random.nextInt(a);
            list.add(Integer.valueOf(b));
        }
        return list;
    }

    public static Double formatDouble(Object obj, int len) {
        Double ret = Double.valueOf(0.0D);
        String format = "0.0";
        for (int i = 1; i < len; i++) {
            format = format + "0";
        }
        DecimalFormat df = new DecimalFormat(format);
        return Double.valueOf(df.format(obj));
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
                || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
                || (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
    }

    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0.0F;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (Character.isLetterOrDigit(c)) {
                continue;
            }
            if (!isChinese(c)) {
                count += 1.0F;
                System.out.print(c);
            }
        }

        float result = count / chLength;

        return result > 0.4D;
    }

    public static String trimSpaces(String IP) {
        while (IP.startsWith(" ")) {
            IP = IP.substring(1).trim();
        }
        while (IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }
        return IP;
    }

    public static boolean isIp(String IP) {
        boolean b = false;
        IP = trimSpaces(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = IP.split("\\.");
            if ((Integer.parseInt(s[0]) < 255) && (Integer.parseInt(s[1]) < 255) && (Integer.parseInt(s[2]) < 255) && (Integer.parseInt(s[3]) < 255)) {
                b = true;
            }
        }
        return b;
    }

    public static String generic_domain(HttpServletRequest request) {
        String system_domain;
        String serverName = request.getServerName();
        if (isIp(serverName)) {
            system_domain = serverName;
        } else {
            system_domain = serverName.substring(serverName.indexOf(".") + 1);
        }

        return system_domain;
    }

    public static String isSelect(String str, String str1) {
        String b = "";
        String[] s = str1.split(",");
        for (String value : s) {
            if (value.equals(str)) {
                b = "selected";
                break;
            }
        }
        return b;
    }

}
