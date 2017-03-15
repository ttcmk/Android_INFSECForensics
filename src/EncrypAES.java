package com.example.infosecforensic;
import java.security.InvalidKeyException;  
import java.security.NoSuchAlgorithmException;  
  
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.KeyGenerator;  
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.SecretKey;  
  
public class EncrypAES {  
      
    //KeyGenerator �ṩ�Գ���Կ�������Ĺ��ܣ�֧�ָ����㷨  
    private KeyGenerator keygen;  
    //SecretKey ���𱣴�Գ���Կ  
    private SecretKey deskey;  
    //Cipher������ɼ��ܻ���ܹ���  
    private Cipher c;  
    //���ֽ����鸺�𱣴���ܵĽ��  
    private byte[] cipherByte;  
      
    public EncrypAES() throws NoSuchAlgorithmException, NoSuchPaddingException{  
        //ʵ����֧��DES�㷨����Կ������(�㷨���������谴�涨�������׳��쳣)  
        keygen = KeyGenerator.getInstance("AES");  
        //������Կ  
        deskey = keygen.generateKey();  
        //����Cipher����,ָ����֧�ֵ�DES�㷨  
        c = Cipher.getInstance("AES");  
    }  
      
    /** 
     * ���ַ������� 
     */  
    public byte[] Encrytor(String str) throws InvalidKeyException,  
            IllegalBlockSizeException, BadPaddingException {  
        // ������Կ����Cipher������г�ʼ����ENCRYPT_MODE��ʾ����ģʽ  
        c.init(Cipher.ENCRYPT_MODE, deskey);  
        byte[] src = str.getBytes();  
        // ���ܣ���������cipherByte  
        cipherByte = c.doFinal(src);  
        return cipherByte;  
    }  
  
    /** 
     * ���ַ������� 
     */  
    public byte[] Decryptor(byte[] buff) throws InvalidKeyException,  
            IllegalBlockSizeException, BadPaddingException {  
        // ������Կ����Cipher������г�ʼ����DECRYPT_MODE��ʾ����ģʽ  
        c.init(Cipher.DECRYPT_MODE, deskey);  
        cipherByte = c.doFinal(buff);  
        return cipherByte;  
    }  
  
//    /** 
//     * ����
//     */  
//    public static void main(String[] args) throws Exception {  
//        EncrypAES de1 = new EncrypAES();  
//        String msg ="��XX-��Ц����ȫ��";  
//        byte[] encontent = de1.Encrytor(msg);  
//        byte[] decontent = de1.Decryptor(encontent);  
//        System.out.println("������:" + msg);  
//        System.out.println("���ܺ�:" + new String(encontent));  
//        System.out.println("���ܺ�:" + new String(decontent));  
//    }  
  
}  

