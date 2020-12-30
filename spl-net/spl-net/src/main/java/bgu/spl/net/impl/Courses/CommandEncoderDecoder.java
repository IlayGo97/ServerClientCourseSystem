package bgu.spl.net.impl.Courses;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;

public class CommandEncoderDecoder implements MessageEncoderDecoder<String> {
    @Override
    public String decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(String message) {
        return new byte[0];
    }
}
