/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BaseClass;

import javax.swing.JFrame;

/**
 *
 * @author csld
 */
public class SessionManager {
    public static String currentUserEmail = null;
    public static JFrame returnAfterLogin = null;
    public static Runnable redirectTargetPage = null; // 👈 next page intent

    public static boolean isLoggedIn() {
        return currentUserEmail != null;
    }

    public static void logout() {
        currentUserEmail = null;
        returnAfterLogin = null;
        redirectTargetPage = null;
    }
}
