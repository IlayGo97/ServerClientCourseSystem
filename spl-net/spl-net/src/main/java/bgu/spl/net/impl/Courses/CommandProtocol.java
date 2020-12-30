package bgu.spl.net.impl.Courses;

import bgu.spl.net.api.MessagingProtocol;

public class CommandProtocol implements MessagingProtocol<String> {
    @Override
    public String process(String msg) {
        return null;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
