package com.mygdx.game;

import java.io.*;

public class save implements Serializable {
     int health1;
     int health2;
     int posax;
     int posay;
     int posbx;
     int posby;

    public save(int health1, int health2, int posax, int posay, int posbx, int posby) {
        this.health1 = health1;
        this.health2 = health2;
        this.posax = posax;
        this.posay = posay;
        this.posbx = posbx;
        this.posby = posby;
    }

 }
// class Client {
//    public static void serialize() throws IOException {
//    save s1 = new save(100, 100, 100, 100, 100, 100);
//    ObjectOutputStream out = null;
//    try {
//        out = new ObjectOutputStream ( new FileOutputStream("out.txt"));
//        out.writeObject(s1);
//    } finally {
//    out.close();
//    }
// }
//}