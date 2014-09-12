package util;
/**
 * @author gudong
 * @since Date: Mar 20, 2014
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gudong
 */
public class StringUtil {
    /**
     * MD5������
     *
     * @param str Ҫ���ܵ��ַ���
     * @return ���ܺ���ַ���
     */
    public static String toMD5(String str) {
        try {
            str = StringUtils.defaultString(str);
            if (str.length() == 0) {
                return str;
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32λ����
            return buf.toString();
            // 16λ�ļ���
            // return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.toMD5("admin"));
    }
}
