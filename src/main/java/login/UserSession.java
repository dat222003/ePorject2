package login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class UserSession {

    private static final DatabaseConnect databaseConnect = new DatabaseConnect();

    public static void createUserSession(String userName, String user_id) {
        String session = user_id + "," + userName;
        DatabaseConnect databaseConnect = new DatabaseConnect();
        String hash_session = databaseConnect.hash(session);
        Path session_path = Paths.get("session.txt");
        try (
                BufferedWriter session_writer = Files.newBufferedWriter(session_path, StandardCharsets.UTF_8);
                Connection con = databaseConnect.getConnect()
        ) {

            session_writer.write(session);
            DatabaseConnect.createSession(hash_session);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createLocalSession(String userName, String user_id) {
        String session = user_id + "," + userName;
        Path session_path = Paths.get("session-local.txt");
        try (
                BufferedWriter session_writer = Files.newBufferedWriter(session_path, StandardCharsets.UTF_8);
        ) {
            session_writer.write(session);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkSession() {
        Path session_path = Paths.get("session.txt");
        try (
                BufferedReader session_reader = Files.newBufferedReader(session_path, StandardCharsets.UTF_8);
                Connection con = databaseConnect.getConnect()
        ) {
            String key = session_reader.readLine();
            if (key != null) {
                if (databaseConnect.checkUserSession()) {
                    return true;
                }
            }
        } catch (IOException | SQLException e) {
            //create session file
            try {
                Files.createFile(session_path);
                return false;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return false;
    }

    public static void deleteUserSession() {

        Path session_path = Paths.get("session.txt");
        Path session_local_path = Paths.get("session-local.txt");
        try (
                BufferedWriter session_local_writer = Files.newBufferedWriter(session_local_path, StandardCharsets.UTF_8);
                BufferedWriter session_writer = Files.newBufferedWriter(session_path, StandardCharsets.UTF_8)
        ) {
            session_local_writer.write("");
            session_writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSession() {
        Path session_path = Paths.get("session.txt");
        try (
                BufferedReader session_reader = Files.newBufferedReader(session_path, StandardCharsets.UTF_8);
        ) {
            String key= session_reader.readLine();
            if (key != null) {
                return key;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String getLocalSession() {
        Path session_path = Paths.get("session-local.txt");
        try (
                BufferedReader session_reader = Files.newBufferedReader(session_path, StandardCharsets.UTF_8);
        ) {
            String key= session_reader.readLine();
            if (key != null) {
                return key;
            }
        } catch (IOException e) {
            // create session file
            try {
                Files.createFile(session_path);
                return null;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;
    }

    public static void deleteLocalSession() {
        Path session_path = Paths.get("session-local.txt");
        try (
                BufferedWriter session_writer = Files.newBufferedWriter(session_path, StandardCharsets.UTF_8)
        ) {
            session_writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
