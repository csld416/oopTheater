/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import javax.swing.JFrame;

/**
 *
 * @author csld
 */
public class SessionManager {
    public static String AdminString = null;
    public static JFrame returnAfterLogin = null;
    public static Runnable redirectTargetPage = null;

    public static boolean isLoggedIn() {
        return User.currUser != null;
    }

    public static void logout() {
        User.currUser.clear();
        User.currUser = null;
        returnAfterLogin = null;
        redirectTargetPage = null;
    }
}
