package com.hpetshop.utils;

import java.util.List;

/**
 * 
 * <ul>
 * <li>�ļ����: StringUtils</li>
 * <li>�ļ�����: ���ڶ��ַ���в���</li>
 * <li>��Ȩ����: ��Ȩ����(C) 2016</li>
 * <li>�� ˾: ������������Ƽ����޹�˾</li>
 * <li>����ժҪ:</li>
 * <li>����˵��:</li>
 * <li>��������:2018��3��12��</li>
 * </ul>
 * <ul>
 * <li>�޸ļ�¼:</li>
 * <li>�� �� ��:</li>
 * <li>�޸�����:</li>
 * <li>�� �� ��:</li>
 * <li>�޸�����:</li>
 * </ul>
 * 
 * @author wushaochuan
 * @version 1.0.0
 */
public class StringUtils {
	/**
	 * ��ͷ��ʼ��ȡ
	 * 
	 * @param str
	 *            �ַ�
	 * @param end
	 *            ����λ��
	 * @return
	 */
	public static String subStrStart(String str, int end) {
		return subStr(str, 0, end);
	}

	/**
	 * ��β��ʼ��ȡ
	 * 
	 * @param str
	 *            �ַ�
	 * @param start
	 *            ��ʼλ��
	 * @return
	 */
	public static String subStrEnd(String str, int start) {
		return subStr(str, str.length() - start, str.length());
	}

	/**
	 * ��ȡ�ַ� ��֧�����򡢷����ȡ��<br/>
	 * 
	 * @param str
	 *            ���ȡ���ַ�
	 * @param length
	 *            ���� ��>=0ʱ����ͷ��ʼ����ȡlength���ȵ��ַ�<0ʱ����β��ʼ��ǰ��ȡlength���ȵ��ַ�
	 * @return ���ؽ�ȡ���ַ�
	 * @throws RuntimeException
	 */
	public static String subStr(String str, int length) throws RuntimeException {
		if (str == null) {
			throw new NullPointerException("�ַ�Ϊnull");
		}
		int len = str.length();
		if (len < Math.abs(length)) {
			throw new StringIndexOutOfBoundsException("��󳤶�Ϊ" + len + "���������ΧΪ:" + (len - Math.abs(length)));
		}
		if (length >= 0) {
			return subStr(str, 0, length);
		} else {
			return subStr(str, len - Math.abs(length), len);
		}
	}

	/**
	 * ��ȡ�ַ� ��֧�����򡢷���ѡ��<br/>
	 * 
	 * @param str
	 *            ���ȡ���ַ�
	 * @param start
	 *            ��ʼ���� ��>=0ʱ����start��ʼ��ȡ��<0ʱ����length-|start|��ʼ��ȡ
	 * @param end
	 *            �������� ��>=0ʱ����end�����ȡ��<0ʱ����length-|end|�����ȡ
	 * @return ���ؽ�ȡ���ַ�
	 * @throws RuntimeException
	 */
	public static String subStr(String str, int start, int end) throws RuntimeException {
		if (str == null) {
			throw new NullPointerException("");
		}
		int len = str.length();
		int s = 0;// ��¼��ʼ����
		int e = 0;// ��¼��β����
		if (len < Math.abs(start)) {
			throw new StringIndexOutOfBoundsException("��󳤶�Ϊ" + len + "���������ΧΪ:" + (len - Math.abs(start)));
		} else if (start < 0) {
			s = len - Math.abs(start);
		} else if (start < 0) {
			s = 0;
		} else {// >=0
			s = start;
		}
		if (len < Math.abs(end)) {
			throw new StringIndexOutOfBoundsException("��󳤶�Ϊ" + len + "���������ΧΪ:" + (len - Math.abs(end)));
		} else if (end < 0) {
			e = len - Math.abs(end);
		} else if (end == 0) {
			e = len;
		} else {// >=0
			e = end;
		}
		if (e < s) {
			throw new StringIndexOutOfBoundsException("��������С����ʼ����:" + (e - s));
		}

		return str.substring(s, e);
	}
	/**
	 * �ж�һ���ַ��Ƿ�Ϊ null �� �մ� �� �հ�
	 * 
	 * @param source
	 *            ��Ҫ�жϵ��ַ�
	 * @return ���ַ�Ϊ null �� Ϊ �հס��մ� ʱ���� true
	 */
	public static boolean isEmpty(String source) {
		return source == null || source.trim().isEmpty();
	}

	/**
	 * �ж�һ���ַ��Ƿ���null�Ҳ��ǿմ������ǿհ�
	 * 
	 * @param source
	 *            ��Ҫ�жϵ��ַ�
	 * @return �� �ַ��ǲ���null�Ҳ��ǿմ�Ҳ���ǿհ�ʱ���� true
	 */
	public static boolean isNotEmpty(String source) {
		return source != null && source.trim().length() > 0;
	}
	public static boolean isNotEmpty(List source) {
		if (source.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}
