package package3;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
    	Saving saving = new Saving(112, 24, 0, "///", false);

        serialize(saving, "Save.txt");

        Saving savingTwo = (Saving)deserialize(Saving.class, "Save.txt");
    }

    private static void serialize(Object o, String file) {
        Class<?> c = o.getClass();

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                try {
                    if (field.isAnnotationPresent(Save.class)) {
                        if (fieldType == int.class || fieldType == Integer.class) {
                            out.writeInt((int) field.get(o));
                        } else if (fieldType == String.class) {
                            out.writeUTF((String) field.get(o));
                        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                            out.writeBoolean((boolean) field.get(o));
                        }
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("Error1");
                }
            }
        } catch (IOException e) {
            System.out.println("Error2");
        }
    }

    private static Object deserialize(Class<?> cl, String file) {
        Object o = null;

        try {
            Constructor c = cl.getDeclaredConstructor();
            c.setAccessible(true);
            o = c.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println("Error3");
        }

        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            Field[] field = cl.getDeclaredFields();
            for (Field f : field) {
                f.setAccessible(true);
                Class<?> type = f.getType();
                try {
                    if (f.isAnnotationPresent(Save.class)) {
                        if (type == int.class || type == Integer.class) {
                            f.set(o, in.readInt());
                        } else if (type == String.class) {
                            f.set(o, in.readUTF());
                        } else if (type == boolean.class || type == Boolean.class) {
                            f.set(o, in.readBoolean());
                        }
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("Error4");
                }
            }
        } catch (IOException e) {
            System.out.println("Error5");
        }

        return o;
    }
}