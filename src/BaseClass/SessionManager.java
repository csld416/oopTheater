/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BaseClass;

/**
 *
 * @author csld
 */
public class SessionManager {
    public static String currentUserEmail = null;
    public static boolean isLoggedIn() {
        return currentUserEmail != null;
    }
}
