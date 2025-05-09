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
    public static User currentUser = null;
    public static String AdminString = null;
    public static JFrame returnAfterLogin = null;
    public static Runnable redirectTargetPage = null;

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser.clear();
        currentUser = null;
        returnAfterLogin = null;
        redirectTargetPage = null;
    }
}
