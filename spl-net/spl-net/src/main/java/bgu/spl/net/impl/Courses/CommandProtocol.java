package bgu.spl.net.impl.Courses;

import bgu.spl.net.Database;
import bgu.spl.net.User;
import bgu.spl.net.api.MessagingProtocol;


public class CommandProtocol implements MessagingProtocol<String> {

    Database db;
    User loggedUser;

    public CommandProtocol()
    {
        this.db = Database.getInstance();
        loggedUser = null;
    }

    @Override
    public String process(String msg) {
        String[] words = msg.split(" ");
        switch (words[0])
        {
            case "ADMINREG" :
            {
                String username = words[1];
                String password = words[2];
                return ADMINREG(username, password);

            }
            case "STUDENTREG" :
            {
                String username = words[1];
                String password = words[2];
                return STUDENTREG(username, password);

            }
            case "LOGIN" :
            {
                String username = words[1];
                String password = words[2];
                return LOGIN(username, password);

            }
            case "LOGOUT" :
            {
                return LOGOUT();

            }
            case "COURSEREG" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return COURSEREG(courseNum);

            }
            case "KDAMCHECK" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return KDAMCHECK(courseNum);

            }
            case "COURSESTAT" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return COURSESTAT(courseNum);

            }
            case "STUDENTSTAT" :
            {
                String username = words[1];
                return STUDENTSTAT(username);

            }
            case "ISREGISTERED" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return ISREGISTERED(courseNum);

            }
            case "UNREGISTER" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return UNREGISTER(courseNum);

            }
            case "MYCOURSES" :
            {
                return MYCOURSES();
            }
            default:
                return null;
        }
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

    private String ADMINREG(String username, String password)
    {
        return null;
    }
    private String STUDENTREG(String username, String password)
    {
        return null;
    }
    private String LOGIN(String username, String password)
    {
        return null;
    }
    private String LOGOUT()
    {
        return null;
    }
    private String COURSEREG(int courseNum)
    {
        return null;
    }
    private String KDAMCHECK(int courseNum)
    {
        return null;
    }
    private String COURSESTAT(int courseNum)
    {
        return  null;
    }
    private String STUDENTSTAT(String Username)
    {
        return null;
    }
    private String ISREGISTERED(int courseNum)
    {
        return null;
    }
    private String UNREGISTER(int courseNum)
    {
        return null;
    }
    private String MYCOURSES()
    {
        return null;
    }

}
