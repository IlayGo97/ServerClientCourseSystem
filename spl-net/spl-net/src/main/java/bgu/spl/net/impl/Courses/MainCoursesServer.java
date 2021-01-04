package bgu.spl.net.impl.Courses;

import bgu.spl.net.srv.Reactor;
import bgu.spl.net.srv.Server;

import java.io.IOException;

public class MainCoursesServer {
    public static void main(String[] args)
    {
        Reactor cur = new Reactor(3, 7777, CommandProtocol::new, CommandEncoderDecoder::new);
        cur.serve();
    }

}
