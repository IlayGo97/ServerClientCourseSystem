package bgu.spl.net.impl.Courses;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.rmi.server.ExportException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes;
    private int len;


    public CommandEncoderDecoder() {
        bytes = new byte[1 << 10];
        len = 0;
    }

    @Override
    public String decodeNextByte(byte nextByte) {
        if (nextByte == '\n') {
            try {
                return popString();

            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }

        pushByte(nextByte);
        return null;
    }

    private String popString() throws Exception {
        String output = "";
        int opCode = bytes[1];
        switch (opCode) {
            case 1: {
                output = output + "ADMINREG ";
                output = output + byteTostring(bytes, 2); //username and password
                break;
            }
            case 2: {
                output = output + "STUDENTREG ";
                output = output + byteTostring(bytes, 2); //username and password
                break;
            }
            case 3: {
                output = output + "LOGIN ";
                output = output + byteTostring(bytes, 2); //username and password
                break;
            }
            case 4: {
                output = output + "LOGOUT";
                break;
            }
            case 5: {
                output = output + "COURSEREG ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output + num;
                break;
            }
            case 6: {
                output = output + "KDAMCHECK ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output + num;
                break;
            }
            case 7: {
                output = output + "COURSESTAT ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output + num;
                break;
            }
            case 8: {
                output = output + "STUDENTSTAT ";
                output = output + byteTostring(bytes, 2); //username and password
                break;
            }
            case 9: {
                output = output + "ISREGISTERED ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output + num;
                break;
            }
            case 10: {
                output = output + "UNREGISTER ";
                int num = new BigInteger(trimArray(bytes)).intValue();
                output = output + num;
                break;
            }
            case 11: {
                output = output + "MYCOURSES";
                break;
            }
            case 12:
            case 13: {
                throw new Exception();
            }
        }
        return output;
    }

    private byte[] trimArray(byte[] b) {
        byte[] bytes2 = new byte[bytes.length - 2];
        for (int i = 0; i < bytes2.length; i++) {
            bytes2[i] = bytes[i + 2];
        }
        return bytes2;
    }

    private String byteTostring(byte[] b, int startIndex) {
        String output = "";
        int length = 0;
        while (startIndex < b.length) {
            while ((startIndex + length) < b.length && b[startIndex + length] != 0)
                length++;
            output = output + new String(b, startIndex, length, StandardCharsets.UTF_8) + " ";
            startIndex = startIndex + length + 1;
            length = 0;
        }
        output = output.substring(0, output.length() - 1);
        return output;
    }


    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    @Override
    public byte[] encode(String message)
    {
        String[] words = message.split(" ");
        if(words[0].equals("ACK"))
        {
            List <byte[]> l = new LinkedList<>();
            int opCode = Integer.parseInt(words[1]);
            String msg = "";
            for(int i=2; i<words.length; i++)
            {
                msg=msg+ " " +words[i];
            }
            byte[] ackOpCodeInBytes = opCodeToByteArray(12);
            byte[] msgOpCodeInBytes = opCodeToByteArray(Integer.parseInt(words[1]));
            byte[] msgByteArray = msg.getBytes(StandardCharsets.UTF_8);
            byte[] z = new byte[1];
            z[0] = 0;
            l.add(ackOpCodeInBytes);
            l.add(msgOpCodeInBytes);
            l.add(msgByteArray);
            l.add(z);
            return combineByteArrays(l);
        }
        else if(words[0].equals("ERROR"))
        {

            byte[] opCodeInBytes = opCodeToByteArray(13);
            byte[] msgOpCodeInBytes = opCodeToByteArray(Integer.parseInt(words[1]));
            byte[] z = new byte[1];
            z[0] = 0;
            List<byte[]> l = new LinkedList<>();
            l.add(opCodeInBytes);
            l.add(msgOpCodeInBytes);
            l.add(z);
            return combineByteArrays(l);
        }
        else
        {
            return null;
        }

    }
    private byte[] combineByteArrays(List<byte[]> bytes)
    {
        int size=0;
        for(int i=0; i<bytes.size(); i++)
        {
           size=size+bytes.get(i).length;
        }
        byte[] output = new byte[size];
        int pos =0;
        for(int i=0; i<bytes.size(); i++)
        {
            byte[] currArray = bytes.get(i);
            for(int j=0; j<currArray.length; j++)
            {
                output[pos] = currArray[j];
                pos++;
            }
        }
        return output;
    }

    private byte[] opCodeToByteArray(int opCode)
    {
        BigInteger b = new BigInteger(opCode+"");
        byte[] bArray = b.toByteArray();
        byte[] toReturn = new byte[2];
        toReturn[0] = 0x0;
        toReturn[1] = bArray[0];
        return toReturn;
    }


}
