package Server;

import java.io.*;
import java.util.Base64;

public class ObjectSerialization {
	public static Object fromString(String input) throws IOException, ClassNotFoundException {
		byte [] data = Base64.getDecoder().decode(input);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		Object output = ois.readObject();
		ois.close();
		return output;
	}

	public static String toString(Serializable object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		oos.close();
		return Base64.getEncoder().encodeToString(baos.toByteArray());
	}
}
