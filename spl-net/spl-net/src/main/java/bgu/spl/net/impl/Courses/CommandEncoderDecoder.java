package bgu.spl.net.impl.Courses;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;

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
        if (nextByte == '\n') {
            return popString();
        }

        pushByte(nextByte);
        return null;
    }

    private String popString(){
        String output="";
        //String trash = new String(bytes, 0, len, StandardCharsets.UTF_8);
        int opCode = bytes[1];
        switch (opCode)
        {
            case 1:
            {
                output = output +"ADMINREG ";
                output = output +byteTostring(bytes,2); //username and password


            }
        }
        return output;
    }

    private String byteTostring(byte[] b, int startIndex)
    {
        String output ="";
        int length=0;
        while(startIndex<b.length) {
            while ((startIndex + length) < b.length && b[startIndex + length] != 0)
                length++;
            output = output +" "+ new String(b, startIndex, length, StandardCharsets.UTF_8);
            startIndex = startIndex+length;
            length = 0;
        }
        return output;
    }

    public static void main(String args[])
    {
        byte[] b = {54,32,72,74,79,00,61,31,32,33,00};
        CommandEncoderDecoder c = new CommandEncoderDecoder();
        System.out.println(c.byteTostring(b,0));
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
