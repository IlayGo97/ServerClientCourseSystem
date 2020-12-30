package bgu.spl.net.impl.Courses;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CommandEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes;
    private int len;

    public CommandEncoderDecoder()
    {
        bytes = new byte[1 << 10];
        len=0;
    }

    @Override
    public String decodeNextByte(byte nextByte) {
        if (nextByte == '\n')
        {
            try
            {
                return popString();

            }
            catch(Exception e)
            {
                System.out.println(e.getStackTrace());
            }
        }

        pushByte(nextByte);
        return null;
    }

    private String popString() throws Exception{
        String output="";
        //String trash = new String(bytes, 0, len, StandardCharsets.UTF_8);
        int opCode = bytes[1];
        switch (opCode)
        {
            case 1:
            {
                output = output +"ADMINREG ";
                output = output +byteTostring(bytes,2); //username and password
                break;
            }
            case 2:
            {
                output = output +"STUDENTREG ";
                output = output +byteTostring(bytes,2); //username and password
                break;
            }
            case 3:
            {
                output = output +"LOGIN ";
                output = output +byteTostring(bytes,2); //username and password
                break;
            }
            case 4:
            {
                output = output +"LOGOUT";
                break;
            }
            case 5:
            {
                output = output +"COURSEREG ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output +num;
                break;
            }
            case 6:
            {
                output = output +"KDAMCHECK ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output +num;
                break;
            }
            case 7:
            {
                output = output +"COURSESTAT ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output +num;
                break;
            }
            case 8:
            {
                output = output +"STUDENTSTAT ";
                output = output +byteTostring(bytes,2); //username and password
                break;
            }
            case 9:
            {
                output = output +"ISREGISTERED ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output +num;
                break;
            }
            case 10:
            {
                output = output +"UNREGISTER ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output +num;
                break;
            }
            case 11:
            {
                output = output +"MYCOURSES";
                break;
            }
            case 12:
            case 13: {
                throw new Exception();
                //output = output +"ACK ";
                //output = output +byteTostring(bytes,2); //username and password
            }
        }
        return output;
    }
    private byte[] trimArray(byte[] b)
    {
        byte[] bytes2 = new byte[bytes.length-2];
        for(int i=0; i<bytes2.length; i++)
        {
            bytes2[i] = bytes[i+2];
        }
        return bytes2;
    }

    private String byteTostring(byte[] b, int startIndex)
    {
        String output ="";
        int length=0;
        while(startIndex<b.length) {
            while ((startIndex + length) < b.length && b[startIndex + length] != 0)
                length++;
            output = output + new String(b, startIndex, length, StandardCharsets.UTF_8) +" ";
            startIndex = startIndex+length+1;
            length = 0;
        }
        output = output.substring(0, output.length()-1);
        return output;
    }

    public static void main(String args[])
    {
        byte[] b = {0,5,0x64,0x13};
        CommandEncoderDecoder c = new CommandEncoderDecoder();
        c.bytes = b;
        try
        {
            String a = c.popString();
            System.out.println(a);
        }
        catch(Exception e)
        {
            System.out.println(e.getCause());
        }
    }


    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    @Override
    public byte[] encode(String message) {
        return new byte[0];
    }
}
