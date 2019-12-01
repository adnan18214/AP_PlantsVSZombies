package allClasses;
import java.io.*;

public class Serializer {
    public static void serialize(User user) throws IOException{

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(user.getName() + ".txt", false));
            out.writeObject(user);
        } finally {
            if(out != null)
                out.close();
        }
    }

    public static User deserialize(String name) throws IOException, ClassNotFoundException{
        User user;

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(name + ".txt"));
            user = (User) in.readObject();
        } finally {
            if(in != null)
                in.close();
        }
        return user;
    }
}
